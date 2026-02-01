#!/bin/bash

# AI 博客系统停止脚本
# Author: Claude Sonnet 4.5

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}正在停止 AI 博客系统...${NC}"
echo ""

# 停止后端
if [ -f .backend.pid ]; then
    BACKEND_PID=$(cat .backend.pid)
    if ps -p $BACKEND_PID > /dev/null 2>&1; then
        echo -e "停止后端服务 (PID: $BACKEND_PID)..."
        kill $BACKEND_PID 2>/dev/null
        sleep 2
        # 如果进程还在，强制杀死
        if ps -p $BACKEND_PID > /dev/null 2>&1; then
            kill -9 $BACKEND_PID 2>/dev/null
        fi
        echo -e "${GREEN}✓ 后端服务已停止${NC}"
    else
        echo -e "${YELLOW}后端服务未运行${NC}"
    fi
    rm -f .backend.pid
else
    # 尝试通过端口查找并停止
    BACKEND_PID=$(lsof -ti:8080 2>/dev/null || echo "")
    if [ ! -z "$BACKEND_PID" ]; then
        echo -e "发现后端进程 (PID: $BACKEND_PID)，正在停止..."
        kill -9 $BACKEND_PID 2>/dev/null
        echo -e "${GREEN}✓ 后端服务已停止${NC}"
    else
        echo -e "${YELLOW}后端服务未运行${NC}"
    fi
fi

# 停止前端
if [ -f .frontend.pid ]; then
    FRONTEND_PID=$(cat .frontend.pid)
    if ps -p $FRONTEND_PID > /dev/null 2>&1; then
        echo -e "停止前端服务 (PID: $FRONTEND_PID)..."
        kill $FRONTEND_PID 2>/dev/null
        sleep 2
        # 如果进程还在，强制杀死
        if ps -p $FRONTEND_PID > /dev/null 2>&1; then
            kill -9 $FRONTEND_PID 2>/dev/null
        fi
        echo -e "${GREEN}✓ 前端服务已停止${NC}"
    else
        echo -e "${YELLOW}前端服务未运行${NC}"
    fi
    rm -f .frontend.pid
else
    # 尝试通过端口查找并停止
    FRONTEND_PID=$(lsof -ti:3000 2>/dev/null || echo "")
    if [ ! -z "$FRONTEND_PID" ]; then
        echo -e "发现前端进程 (PID: $FRONTEND_PID)，正在停止..."
        kill -9 $FRONTEND_PID 2>/dev/null
        echo -e "${GREEN}✓ 前端服务已停止${NC}"
    else
        echo -e "${YELLOW}前端服务未运行${NC}"
    fi
fi

echo ""
echo -e "${GREEN}所有服务已停止${NC}"
echo ""
