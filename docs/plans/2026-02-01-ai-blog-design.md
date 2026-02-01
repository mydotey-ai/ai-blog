# AI 博客系统设计文档

**日期**: 2026-02-01
**类型**: 个人技术博客
**技术栈**: Vue 3 + Spring Boot 3.5 + PostgreSQL 16

---

## 1. 整体架构

博客采用前后端分离架构：

**前端部分**：Vue 3 构建单页应用（SPA），使用 Vue Router 处理路由，Pinia 进行状态管理。UI 方面使用 Element Plus 或 Naive UI 组件库，配合 Tailwind CSS 进行样式定制。

**后端部分**：Spring Boot 3.5 提供 RESTful API，使用 Spring Security 处理认证授权（JWT），Spring Data JPA 进行数据访问。前后端通过 HTTP/JSON 通信。

**数据存储**：PostgreSQL 16 存储所有数据，包括文章、标签、评论、管理员信息等。

**目录结构规划**：
```
ai-blog/
├── frontend/          # Vue 3 前端
├── backend/           # Spring Boot 后端
└── docs/              # 文档
```

---

## 2. 数据库设计

### 2.1 文章表 (posts)

```sql
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(200) UNIQUE NOT NULL,
    content TEXT NOT NULL,
    summary VARCHAR(500),
    cover_image VARCHAR(500),
    status VARCHAR(20) DEFAULT 'DRAFT',
    views BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_posts_slug ON posts(slug);
CREATE INDEX idx_posts_status ON posts(status);
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);
```

### 2.2 标签表 (tags)

```sql
CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    slug VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2.3 文章标签关联表 (post_tags)

```sql
CREATE TABLE post_tags (
    post_id BIGINT REFERENCES posts(id) ON DELETE CASCADE,
    tag_id BIGINT REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);
```

### 2.4 评论表 (comments)

```sql
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    parent_id BIGINT REFERENCES comments(id) ON DELETE CASCADE,
    author_name VARCHAR(50),
    author_email VARCHAR(100),
    content TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_status ON comments(status);
```

### 2.5 管理员表 (admins)

```sql
CREATE TABLE admins (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 3. 后端 API 设计

### 3.1 公开接口（无需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/posts` | 获取文章列表（分页、标签筛选、搜索） |
| GET | `/api/posts/{slug}` | 获取文章详情 |
| GET | `/api/posts/{slug}/comments` | 获取文章评论 |
| GET | `/api/tags` | 获取所有标签 |
| POST | `/api/comments` | 发表匿名评论 |
| POST | `/api/auth/login` | 管理员登录 |

### 3.2 管理员接口（需要认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/posts` | 文章管理列表 |
| POST | `/api/admin/posts` | 创建文章 |
| PUT | `/api/admin/posts/{id}` | 更新文章 |
| DELETE | `/api/admin/posts/{id}` | 删除文章 |
| GET | `/api/admin/comments` | 评论管理列表 |
| PUT | `/api/admin/comments/{id}/status` | 审核评论 |
| DELETE | `/api/admin/comments/{id}` | 删除评论 |
| POST | `/api/admin/tags` | 创建标签 |
| PUT | `/api/admin/tags/{id}` | 更新标签 |
| DELETE | `/api/admin/tags/{id}` | 删除标签 |

### 3.3 评论请求体

```json
POST /api/comments
{
  "postId": 123,
  "parentId": null,
  "authorName": "张三",
  "authorEmail": "xx@example.com",
  "content": "很好的文章！",
  "captcha": "验证码"
}
```

### 3.4 技术要点

- 使用 JWT 存储 session
- Spring Security + BCrypt 处理认证
- Spring Data JPA 简化 CRUD
- MapStruct 做 DTO 转换
- 图形验证码防垃圾评论
- IP 限流（bucket4j）

---

## 4. 前端页面设计

### 4.1 页面结构

**首页 (`/`)**
- 展示最新文章列表（卡片式布局）
- 每篇文章显示：标题、摘要、封面图、发布日期、标签、阅读量
- 顶部导航栏：Logo、标签列表、搜索框
- 右侧边栏：热门文章、标签云

**文章详情页 (`/posts/{slug}`)**
- 文章标题、元信息（日期、浏览量、标签）
- Markdown 渲染的正文
- 评论区：楼中楼结构显示，支持回复
- 评论输入框（可选填昵称/邮箱）

**标签页 (`/tags/{slug}`)**
- 显示该标签下的文章列表
- 面包屑导航

**管理后台 (`/admin`)**
- 文章管理：列表、新建、编辑、删除
- 评论管理：审核、删除
- 标签管理
- Markdown 编辑器（`md-editor-v3`）

### 4.2 路由配置

```
/ → 首页
/posts/:slug → 文章详情
/tags/:slug → 标签文章列表
/admin → 管理后台（需要认证）
/admin/posts → 文章管理
/admin/comments → 评论管理
```

---

## 5. 认证流程

### 5.1 管理员登录

```
管理员访问 /admin/login
→ 输入用户名密码
→ POST /api/auth/login {username, password}
→ 验证 BCrypt 密码哈希
→ 返回 JWT
→ 存储 token，跳转管理后台
```

### 5.2 匿名评论

```
访客阅读文章
→ 输入昵称（可选）、邮箱（可选）、内容、验证码
→ POST /api/comments
→ 验证验证码、敏感词过滤
→ 状态设为 PENDING 或 APPROVED
→ 返回成功
```

### 5.3 JWT Token 设计

```json
{
  "sub": "管理员ID",
  "username": "admin",
  "role": "ADMIN",
  "exp": "过期时间（7天）"
}
```

---

## 6. 部署架构

### 6.1 架构图

```
                   Nginx (反向代理)
                    /        \
                   /          \
         Vue 3静态前端    Spring Boot后端(:8080)
                               |
                         PostgreSQL 16 (:5432)
```

### 6.2 Nginx 配置要点

- 前端静态文件服务（`/` 指向 `frontend/dist`）
- API 代理（`/api/*` 转发到后端 `:8080`）
- SSL/TLS 配置（Let's Encrypt）
- Gzip 压缩

### 6.3 部署流程

1. **前端**：`npm run build` → 生成 `dist/` → 上传到服务器
2. **后端**：`gradle bootJar` → 生成可执行 JAR → 上传并运行
3. **数据库**：安装 PostgreSQL 16，执行建表脚本
4. **环境变量**：配置数据库连接、JWT 密钥

### 6.4 服务管理

- 使用 Systemd 管理 Spring Boot 服务
- 使用 Nginx 托管前端静态文件

---

## 7. 安全考虑

- 管理员密码使用 BCrypt 哈希存储
- 评论需要图形验证码防止机器人
- IP 限流：同一 IP 每分钟最多 5 条评论
- 敏感词过滤（关键词黑名单）
- HTTPS 部署（生产环境）
- JWT 过期时间 7 天

---

## 8. 核心功能总结

| 功能 | 描述 |
|------|------|
| 文章展示 | 首页列表、详情页、标签分类 |
| 内容管理 | 管理员后台 CRUD 操作 |
| 匿名评论 | 可选昵称/邮箱，楼中楼回复 |
| 审核机制 | 评论需管理员审核后显示 |
