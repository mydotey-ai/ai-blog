#!/bin/bash

# 这个脚本通过 Spring Boot 的 AuthService 创建管理员账号
# 需要先启动后端服务,然后手动调用 createAdmin 方法

echo "请手动执行以下操作创建管理员："
echo "1. 启动后端服务"
echo "2. 在代码中临时添加一个初始化方法调用 authService.createAdmin(\"admin\", \"password\")"
echo "3. 启动后删除该初始化代码"
