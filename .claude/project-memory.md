# AI 博客系统 - 项目记忆

## 项目概述

**项目名称**: AI Blog System (AI 博客系统)
**创建日期**: 2026-02-01
**开发方式**: Subagent-Driven Development
**状态**: ✅ 完整实现，可直接运行

## 技术栈

### 后端
- Java 21
- Spring Boot 3.5.0
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL 16
- Gradle

### 前端
- Vue 3.5 (Composition API)
- TypeScript 5.7
- Vite 6.0
- Tailwind CSS 3.4
- Vue Router 4.5
- Pinia 2.3

## 设计理念

**克莱因蓝极简主义**
- 主色: #0022FF (克莱因蓝)
- 超大留白
- 精致排版
- 极简设计

## 项目结构

```
ai-blog/
├── backend/                 # Spring Boot 后端
│   ├── src/main/java/com/mydotey/blog/
│   │   ├── config/         # 配置 (Security, DataInitializer)
│   │   ├── controller/     # 5 个 REST 控制器
│   │   ├── dto/            # 6 个 DTO
│   │   ├── entity/         # 4 个实体 (Post, Tag, Comment, Admin)
│   │   ├── filter/         # JWT 过滤器
│   │   ├── repository/     # 4 个 Repository
│   │   ├── service/        # 4 个 Service
│   │   └── util/           # JWT 工具类
│   └── src/main/resources/application.yml
├── frontend/               # Vue 3 前端
│   ├── src/
│   │   ├── components/     # Navbar, PostCard, CommentSection
│   │   ├── views/          # 公开页面 + 管理后台
│   │   ├── services/       # api.ts, auth.ts
│   │   └── router/         # 路由配置
├── docs/
│   ├── plans/              # 实施计划
│   ├── ui-design/          # UI 设计文档
│   └── implementation-summary.md
├── scripts/
│   └── init-db.sql         # 数据库初始化
├── start.sh                # ⭐ 一键启动脚本
├── stop.sh                 # 停止脚本
└── README.md               # 完整文档
```

## 核心功能

### 公开功能
- 博客首页和文章列表
- 文章详情（Markdown 渲染）
- 标签分类浏览
- 匿名评论系统

### 管理功能
- 管理员登录 (JWT 认证)
- 文章 CRUD (草稿/发布)
- 评论审核
- 标签管理

## 数据库设计

5 个主要表：
1. `admins` - 管理员 (BCrypt 密码)
2. `posts` - 文章 (title, slug, content, status, views)
3. `tags` - 标签
4. `post_tags` - 文章标签关联 (多对多)
5. `comments` - 评论 (PENDING/APPROVED)

## 环境配置

### 数据库
- 用户名: `postgres`
- 密码: `xx123456XX`
- 数据库名: `aiblog`
- 端口: 5432

### 服务端口
- 后端: 8080
- 前端: 3000

### 默认管理员
- 用户名: `admin`
- 密码: `admin123`
- ⚠️ 首次登录后需修改

## 快速启动

```bash
# 一键启动所有服务
./start.sh

# 停止所有服务
./stop.sh

# 查看日志
tail -f backend.log
tail -f frontend.log
```

## API 端点

### 公开 API
- `GET /api/posts` - 文章列表 (支持分页、标签、搜索)
- `GET /api/posts/{slug}` - 文章详情
- `GET /api/tags` - 所有标签
- `GET /api/comments?postId={id}` - 文章评论
- `POST /api/comments` - 发表评论
- `POST /api/auth/login` - 管理员登录

### 管理员 API (需 JWT)
- `GET /api/admin/posts` - 所有文章
- `POST /api/admin/posts` - 创建文章
- `PUT /api/admin/posts/{id}` - 更新文章
- `DELETE /api/admin/posts/{id}` - 删除文章
- `GET /api/admin/comments` - 所有评论
- `PUT /api/admin/comments/{id}/status` - 审核评论
- `DELETE /api/admin/comments/{id}` - 删除评论

## 重要文件说明

### 后端核心文件
- `SecurityConfig.java` - Spring Security 配置，定义 URL 权限
- `JwtAuthenticationFilter.java` - JWT 认证过滤器
- `JwtUtil.java` - JWT 生成和验证
- `DataInitializer.java` - 自动创建默认管理员账号
- `application.yml` - 数据库连接、JWT 配置

### 前端核心文件
- `router/index.ts` - 路由配置，包含认证守卫
- `services/api.ts` - 所有 API 调用封装
- `services/auth.ts` - 认证逻辑 (localStorage)
- `assets/styles/main.css` - 克莱因蓝设计系统

## 开发历程

**完成的 16 个任务：**
1. 后端项目结构创建
2. 前端项目初始化
3. 数据库实体类创建
4. Repository 层创建
5. DTO 和请求/响应类创建
6. JWT 工具类创建
7. Spring Security 配置
8. Service 层实现
9. Controller 层实现
10. 前端样式系统
11. API 服务层创建
12. 路由配置
13. 公开页面组件实现
14. 管理后台页面实现
15. 数据库初始化脚本
16. 启动和运行验证

## 待办功能

- [ ] Markdown 编辑器 (md-editor-v3)
- [ ] 图片上传功能
- [ ] 文章搜索优化
- [ ] 评论验证码
- [ ] 邮件通知
- [ ] RSS 订阅
- [ ] 访问统计

## 开发技巧

### 常用命令
```bash
# 后端
cd backend && ./gradlew bootRun
cd backend && ./gradlew build

# 前端
cd frontend && npm run dev
cd frontend && npm run build

# 数据库
PGPASSWORD=xx123456XX psql -h localhost -U postgres -f scripts/init-db.sql
```

### 调试技巧
1. 后端日志级别在 `application.yml` 中配置
2. 前端使用 Vue DevTools
3. API 测试可用 Postman 或 curl
4. 数据库查看: `psql -U postgres -d aiblog`

## 安全注意事项

⚠️ **生产环境部署前必做：**
1. 修改 JWT secret（256 位以上）
2. 修改默认管理员密码
3. 配置 HTTPS
4. 启用速率限制
5. 配置 CORS 白名单
6. 修改数据库密码
7. 启用日志监控

## 文档位置

- **实施计划**: `docs/plans/2026-02-01-full-stack-blog-implementation.md`
- **实施总结**: `docs/implementation-summary.md`
- **设计系统**: `docs/ui-design/design-system.md`
- **完整文档**: `README.md`
- **快速开始**: `QUICKSTART.md`

## 关键决策记录

1. **为什么选择 JWT?** - 无状态认证，适合前后端分离
2. **为什么使用克莱因蓝?** - 独特的视觉识别，避免千篇一律
3. **为什么不用测试?** - 用户要求，快速开发优先
4. **为什么用 Spring Boot 3.5?** - 最新稳定版，Java 21 支持
5. **为什么用 Composition API?** - Vue 3 推荐，更好的 TypeScript 支持

## 项目特色

✨ **亮点:**
- 完整的前后端分离架构
- 克莱因蓝极简主义设计
- 一键启动脚本，零配置
- 完善的文档和代码注释
- 使用最新技术栈
- 代码结构清晰，易于扩展

---

**最后更新**: 2026-02-01
**维护者**: Claude Sonnet 4.5
