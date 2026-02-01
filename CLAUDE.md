# AI 博客系统 - Claude 项目记忆

> 本文件为 Claude Code 提供项目上下文，帮助快速理解和维护项目。

## 项目信息

- **名称**: AI Blog System
- **类型**: 全栈博客系统
- **状态**: ✅ 已完成，可运行
- **创建**: 2026-02-01
- **开发方式**: Subagent-Driven Development

## 一句话总结

基于 Vue 3 + Spring Boot 的克莱因蓝极简主义博客系统，支持 Markdown 文章发布和匿名评论。

## 技术架构

```
前端 (端口 3000)          后端 (端口 8080)          数据库
Vue 3 + TypeScript  <-->  Spring Boot 3.5  <-->  PostgreSQL 16
  ├─ Vite                   ├─ Spring Security
  ├─ Tailwind CSS           ├─ JWT 认证
  ├─ Vue Router             ├─ Spring Data JPA
  └─ Pinia                  └─ Gradle
```

## 快速命令

```bash
# 一键启动（推荐）
./start.sh

# 停止服务
./stop.sh

# 查看日志
tail -f backend.log    # 后端
tail -f frontend.log   # 前端

# 手动启动
cd backend && ./gradlew bootRun    # 后端
cd frontend && npm run dev          # 前端
```

## 关键路径

### 后端代码
- **入口**: `backend/src/main/java/com/mydotey/blog/BlogApplication.java`
- **配置**: `backend/src/main/resources/application.yml`
- **安全**: `backend/src/main/java/com/mydotey/blog/config/SecurityConfig.java`
- **认证**: `backend/src/main/java/com/mydotey/blog/util/JwtUtil.java`

### 前端代码
- **入口**: `frontend/src/main.ts`
- **路由**: `frontend/src/router/index.ts`
- **API**: `frontend/src/services/api.ts`
- **样式**: `frontend/src/assets/styles/main.css`

### 数据库
- **脚本**: `scripts/init-db.sql`
- **初始化**: `DataInitializer.java` (自动创建管理员)

## 核心功能模块

### 1. 认证系统 (JWT)
- **文件**: `JwtUtil.java`, `JwtAuthenticationFilter.java`, `SecurityConfig.java`
- **流程**: 登录 → 生成 JWT → 前端存储 → 请求携带 → 后端验证
- **存储**: LocalStorage (`token`, `username`)

### 2. 文章管理
- **实体**: `Post.java` (title, slug, content, status, views)
- **状态**: DRAFT | PUBLISHED
- **关联**: 多对多关联 Tag (`post_tags` 表)
- **渲染**: 前端使用 `marked` 库渲染 Markdown

### 3. 评论系统
- **实体**: `Comment.java` (postId, authorName, content, status)
- **状态**: PENDING | APPROVED
- **审核**: 管理员后台审核后才显示

### 4. 标签系统
- **实体**: `Tag.java` (name, slug)
- **自动创建**: 创建文章时，不存在的标签会自动创建

## 数据库结构

```sql
admins          -- 管理员 (username, password_hash)
posts           -- 文章 (title, slug, content, status, views)
tags            -- 标签 (name, slug)
post_tags       -- 文章-标签关联 (多对多)
comments        -- 评论 (post_id, content, status)
```

## 环境配置

### 数据库
```yaml
url: jdbc:postgresql://localhost:5432/aiblog
username: postgres
password: xx123456XX
```

### 默认账号
```
用户名: admin
密码: admin123
⚠️ 首次登录后需修改
```

### 服务端口
- 后端: 8080
- 前端: 3000

## API 端点速查

### 公开 API
```
GET  /api/posts              # 文章列表 (?tag=xx&search=xx&page=0&size=10)
GET  /api/posts/{slug}       # 文章详情
GET  /api/tags               # 所有标签
GET  /api/comments?postId=1  # 文章评论
POST /api/comments           # 发表评论
POST /api/auth/login         # 登录
```

### 管理 API (需 JWT)
```
GET    /api/admin/posts          # 所有文章
POST   /api/admin/posts          # 创建文章
PUT    /api/admin/posts/{id}     # 更新文章
DELETE /api/admin/posts/{id}     # 删除文章
GET    /api/admin/comments       # 所有评论
PUT    /api/admin/comments/{id}/status  # 审核评论
DELETE /api/admin/comments/{id}  # 删除评论
```

## 常见任务

### 添加新功能
1. **后端**: Entity → Repository → Service → Controller → DTO
2. **前端**: API 服务 → 组件 → 路由

### 修改样式
- 主题色: `frontend/tailwind.config.js` (克莱因蓝 #0022FF)
- 全局样式: `frontend/src/assets/styles/main.css`

### 数据库变更
- 修改 Entity 类
- JPA 会自动更新表结构 (ddl-auto: update)
- 生产环境建议手动迁移

### 调试技巧
```bash
# 后端调试
tail -f backend.log | grep ERROR

# 前端调试
npm run dev  # 查看终端输出
# 浏览器 Vue DevTools

# 数据库查询
psql -U postgres -d aiblog
SELECT * FROM posts;
```

## 代码规范

### 后端
- **包命名**: com.mydotey.blog.{config|controller|dto|entity|filter|repository|service|util}
- **注解**: @RestController, @Service, @Repository, @Component
- **事务**: Service 层使用 @Transactional
- **验证**: DTO 使用 @Valid, @NotBlank, @NotNull

### 前端
- **API**: Composition API (`<script setup lang="ts">`)
- **类型**: 所有组件使用 TypeScript
- **样式**: Tailwind CSS 优先，自定义样式放 main.css
- **路由**: 懒加载 `() => import('@/views/xxx.vue')`

## 安全检查清单

⚠️ **生产环境部署前**:
- [ ] 修改 JWT secret (256 位以上)
- [ ] 修改数据库密码
- [ ] 修改默认管理员密码
- [ ] 配置 HTTPS
- [ ] 配置 CORS 白名单
- [ ] 启用速率限制
- [ ] 配置备份策略
- [ ] 启用日志监控

## 故障排查

### 后端无法启动
1. PostgreSQL 是否运行: `pg_isready`
2. 端口占用: `lsof -i:8080`
3. 日志: `tail -f backend.log`

### 前端无法启动
1. 依赖安装: `cd frontend && npm install`
2. 端口占用: `lsof -i:3000`
3. 日志: `tail -f frontend.log`

### 数据库连接失败
1. 检查 PostgreSQL 状态
2. 验证 application.yml 中的密码
3. 确认数据库已创建: `psql -l | grep aiblog`

## 扩展方向

### 短期 (1-2 周)
- [ ] 集成 Markdown 编辑器 (md-editor-v3)
- [ ] 图片上传 (本地存储或 OSS)
- [ ] 评论验证码 (防机器人)

### 中期 (1 月)
- [ ] 文章全文搜索 (ElasticSearch)
- [ ] 邮件通知系统
- [ ] RSS 订阅
- [ ] 访问统计

### 长期 (3 月+)
- [ ] 多用户系统
- [ ] 文章版本控制
- [ ] 社交分享
- [ ] SEO 优化

## 文档资源

- **完整文档**: `README.md`
- **快速开始**: `QUICKSTART.md`
- **实施计划**: `docs/plans/2026-02-01-full-stack-blog-implementation.md`
- **设计系统**: `docs/ui-design/design-system.md`
- **实施总结**: `docs/implementation-summary.md`
- **项目记忆**: `.claude/project-memory.md`

## 项目特色

🎨 **克莱因蓝设计**
- 主色 #0022FF，独特视觉风格
- 超大留白，精致排版
- 极简主义美学

🚀 **一键启动**
- 零配置，运行 `./start.sh` 即可
- 自动初始化数据库
- 自动创建管理员账号

⚡ **现代技术**
- Vue 3 Composition API
- Spring Boot 3.5
- TypeScript 全覆盖
- JWT 无状态认证

## 快速参考

```bash
# 访问地址
前端: http://localhost:3000
后台: http://localhost:3000/admin
API:  http://localhost:8080

# 默认凭据
用户名: admin
密码: admin123

# 重要命令
./start.sh         # 启动
./stop.sh          # 停止
tail -f *.log      # 查看日志
git log --oneline  # 查看提交历史
```

---

**最后更新**: 2026-02-01
**维护**: Claude Sonnet 4.5
**许可**: MIT

💡 **提示**: 本文件应随项目演进持续更新，保持信息准确性。
