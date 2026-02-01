#!/bin/bash

# AI 博客系统一键启动脚本
# Author: Claude Sonnet 4.5
# Date: 2026-02-01

set -e  # 遇到错误立即退出

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 配置
DB_USER="postgres"
DB_PASSWORD="xx123456XX"
DB_NAME="aiblog"
DB_HOST="localhost"
DB_PORT="5432"

# 默认管理员账号
ADMIN_USERNAME="admin"
ADMIN_PASSWORD="admin123"

echo -e "${GREEN}================================${NC}"
echo -e "${GREEN}AI 博客系统一键启动脚本${NC}"
echo -e "${GREEN}================================${NC}"
echo ""

# 检查 PostgreSQL 是否运行
echo -e "${YELLOW}[1/6] 检查 PostgreSQL 服务...${NC}"
if ! pg_isready -h $DB_HOST -p $DB_PORT -U $DB_USER > /dev/null 2>&1; then
    echo -e "${RED}错误: PostgreSQL 服务未运行${NC}"
    echo "请先启动 PostgreSQL 服务"
    exit 1
fi
echo -e "${GREEN}✓ PostgreSQL 服务正常${NC}"
echo ""

# 检查并创建数据库
echo -e "${YELLOW}[2/6] 检查数据库...${NC}"
DB_EXISTS=$(PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME'" 2>/dev/null || echo "0")

if [ "$DB_EXISTS" != "1" ]; then
    echo "数据库不存在，正在创建..."
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -f scripts/init-db.sql > /dev/null 2>&1
    echo -e "${GREEN}✓ 数据库创建成功${NC}"
else
    echo -e "${GREEN}✓ 数据库已存在${NC}"
fi
echo ""

# 更新后端配置
echo -e "${YELLOW}[3/6] 更新后端配置...${NC}"
cat > backend/src/main/resources/application.yml << EOF
spring:
  application:
    name: ai-blog
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
  security:
    jwt:
      secret: your-secret-key-change-in-production-min-256-bits-long-for-security
      expiration: 604800000

server:
  port: 8080

logging:
  level:
    com.mydotey.blog: INFO
EOF
echo -e "${GREEN}✓ 后端配置已更新${NC}"
echo ""

# 创建初始化配置类（自动创建管理员账号）
echo -e "${YELLOW}[4/6] 配置管理员账号初始化...${NC}"
mkdir -p backend/src/main/java/com/mydotey/blog/config
cat > backend/src/main/java/com/mydotey/blog/config/DataInitializer.java << 'EOF'
package com.mydotey.blog.config;

import com.mydotey.blog.entity.Admin;
import com.mydotey.blog.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 检查是否已有管理员账号
            if (adminRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                adminRepository.save(admin);
                System.out.println("✓ 默认管理员账号已创建");
                System.out.println("  用户名: admin");
                System.out.println("  密码: admin123");
                System.out.println("  请登录后立即修改密码！");
            }
        };
    }
}
EOF
echo -e "${GREEN}✓ 管理员初始化配置已创建${NC}"
echo ""

# 编译并启动后端
echo -e "${YELLOW}[5/6] 启动后端服务...${NC}"
cd backend

# 检查是否已有后端进程在运行
BACKEND_PID=$(lsof -ti:8080 2>/dev/null || echo "")
if [ ! -z "$BACKEND_PID" ]; then
    echo "检测到端口 8080 已被占用，正在停止旧进程..."
    kill -9 $BACKEND_PID 2>/dev/null || true
    sleep 2
fi

# 启动后端（后台运行）
echo "正在编译并启动后端服务（这可能需要几分钟）..."
nohup gradle bootRun > ../backend.log 2>&1 &
BACKEND_PID=$!

# 等待后端启动
echo "等待后端服务启动..."
for i in {1..60}; do
    if curl -s http://localhost:8080/api/tags > /dev/null 2>&1; then
        echo -e "${GREEN}✓ 后端服务启动成功 (PID: $BACKEND_PID)${NC}"
        break
    fi
    if [ $i -eq 60 ]; then
        echo -e "${RED}错误: 后端服务启动超时${NC}"
        echo "请查看日志: tail -f backend.log"
        exit 1
    fi
    sleep 1
    echo -n "."
done
echo ""
cd ..
echo ""

# 启动前端
echo -e "${YELLOW}[6/6] 启动前端服务...${NC}"
cd frontend

# 检查是否已有前端进程在运行
FRONTEND_PID=$(lsof -ti:3000 2>/dev/null || echo "")
if [ ! -z "$FRONTEND_PID" ]; then
    echo "检测到端口 3000 已被占用，正在停止旧进程..."
    kill -9 $FRONTEND_PID 2>/dev/null || true
    sleep 2
fi

# 检查依赖
if [ ! -d "node_modules" ]; then
    echo "首次运行，正在安装前端依赖..."
    npm install > /dev/null 2>&1
fi

# 启动前端开发服务器（后台运行）
nohup npm run dev > ../frontend.log 2>&1 &
FRONTEND_PID=$!

# 等待前端启动
echo "等待前端服务启动..."
for i in {1..30}; do
    if curl -s http://localhost:3000 > /dev/null 2>&1; then
        echo -e "${GREEN}✓ 前端服务启动成功 (PID: $FRONTEND_PID)${NC}"
        break
    fi
    if [ $i -eq 30 ]; then
        echo -e "${RED}错误: 前端服务启动超时${NC}"
        echo "请查看日志: tail -f frontend.log"
        exit 1
    fi
    sleep 1
    echo -n "."
done
echo ""
cd ..

# 保存 PID 到文件
echo $BACKEND_PID > .backend.pid
echo $FRONTEND_PID > .frontend.pid

echo ""
echo -e "${GREEN}================================${NC}"
echo -e "${GREEN}✓ 所有服务启动成功！${NC}"
echo -e "${GREEN}================================${NC}"
echo ""
echo -e "前端地址: ${GREEN}http://localhost:3000${NC}"
echo -e "后端地址: ${GREEN}http://localhost:8080${NC}"
echo ""
echo -e "默认管理员账号:"
echo -e "  用户名: ${GREEN}admin${NC}"
echo -e "  密码: ${GREEN}admin123${NC}"
echo ""
echo -e "管理后台: ${GREEN}http://localhost:3000/admin${NC}"
echo ""
echo -e "日志文件:"
echo -e "  后端: ${YELLOW}backend.log${NC}"
echo -e "  前端: ${YELLOW}frontend.log${NC}"
echo ""
echo -e "停止服务: ${YELLOW}./stop.sh${NC}"
echo ""
echo -e "${YELLOW}提示: 首次登录后请立即修改管理员密码！${NC}"
echo ""
