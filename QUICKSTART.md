# 🚀 快速开始指南

## 5 分钟启动你的博客系统

### 第 1 步：确认环境

确保已安装以下软件：

- ✅ Java 21
- ✅ Node.js 18+
- ✅ PostgreSQL 16（用户名：postgres，密码：xx123456XX）

### 第 2 步：一键启动

```bash
./start.sh
```

就这么简单！脚本会自动完成所有配置。

### 第 3 步：访问系统

**前端首页**: http://localhost:3000

**管理后台**: http://localhost:3000/admin
- 用户名: `admin`
- 密码: `admin123`

### 常用命令

```bash
# 停止服务
./stop.sh

# 查看日志
tail -f backend.log   # 后端日志
tail -f frontend.log  # 前端日志
```

### 下一步做什么？

1. 登录管理后台
2. 创建第一篇文章
3. 发布并查看效果
4. 尝试评论功能

### 遇到问题？

查看完整文档：[README.md](README.md)

---

**提示**: 首次登录后记得修改默认密码！
