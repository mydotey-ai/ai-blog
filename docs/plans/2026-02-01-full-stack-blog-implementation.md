# AI 博客系统完整实现计划

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 构建完整的前后端分离博客系统（Vue 3 + Spring Boot + PostgreSQL）

**Architecture:** 前端 Vue 3 SPA，后端 Spring Boot RESTful API，PostgreSQL 数据库，前后端通过 HTTP/JSON 通信

**Tech Stack:** Vue 3, Vite, Tailwind CSS, Spring Boot 3.5, Spring Data JPA, Spring Security, PostgreSQL 16

---

## Phase 1: 项目初始化

### Task 1: 后端项目结构创建

**Files:**
- Create: `backend/build.gradle`
- Create: `backend/src/main/java/com/mydotey/blog/BlogApplication.java`
- Create: `backend/src/main/resources/application.yml`
- Create: `backend/.gitignore`

**Step 1: 创建 Gradle 构建配置**

在 `backend/build.gradle`:
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.0'
    id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.mydotey'
version = '1.0.0'
sourceCompatibility = '21'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.postgresql:postgresql'
    implementation 'io.jsonwebtoken:jjwt-api:0.12.5'
    implementation 'io.jsonwebtoken:jjwt-impl:0.12.5'
    implementation 'io.jsonwebtoken:jjwt-jackson:0.12.5'
    implementation 'org.mapstruct:mapstruct:1.6.3'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
}
```

**Step 2: 创建主应用类**

在 `backend/src/main/java/com/mydotey/blog/BlogApplication.java`:
```java
package com.mydotey.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
```

**Step 3: 创建应用配置**

在 `backend/src/main/resources/application.yml`:
```yaml
spring:
  application:
    name: ai-blog
  datasource:
    url: jdbc:postgresql://localhost:5432/aiblog
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  security:
    jwt:
      secret: your-secret-key-change-in-production-min-256-bits-long
      expiration: 604800000

server:
  port: 8080
```

**Step 4: 创建 .gitignore**

在 `backend/.gitignore`:
```
.gradle/
build/
.idea/
*.iml
*.log
```

**Step 5: 初始化 Gradle Wrapper**

```bash
cd backend && gradle wrapper
```

---

### Task 2: 前端项目初始化

**Files:**
- Create: `frontend/package.json`
- Create: `frontend/vite.config.ts`
- Create: `frontend/tsconfig.json`
- Create: `frontend/tailwind.config.js`
- Create: `frontend/index.html`
- Create: `frontend/src/main.ts`
- Create: `frontend/src/App.vue`

**Step 1: 创建 package.json**

在 `frontend/package.json`:
```json
{
  "name": "ai-blog-frontend",
  "version": "1.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vue-tsc && vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "vue": "^3.5.13",
    "vue-router": "^4.5.0",
    "pinia": "^2.3.0",
    "axios": "^1.7.9",
    "marked": "^15.0.6",
    "highlight.js": "^11.11.1"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^5.2.1",
    "typescript": "^5.7.3",
    "vue-tsc": "^2.2.0",
    "vite": "^6.0.7",
    "tailwindcss": "^3.4.17",
    "autoprefixer": "^10.4.20",
    "postcss": "^8.5.1"
  }
}
```

**Step 2: 创建 Vite 配置**

在 `frontend/vite.config.ts`:
```typescript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
```

**Step 3: 创建 TypeScript 配置**

在 `frontend/tsconfig.json`:
```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "ESNext",
    "lib": ["ES2020", "DOM", "DOM.Iterable"],
    "skipLibCheck": true,
    "moduleResolution": "bundler",
    "resolveJsonModule": true,
    "isolatedModules": true,
    "jsx": "preserve",
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true,
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"]
    }
  },
  "include": ["src/**/*.ts", "src/**/*.d.ts", "src/**/*.tsx", "src/**/*.vue"],
  "exclude": ["node_modules"]
}
```

**Step 4: 创建 Tailwind 配置**

在 `frontend/tailwind.config.js`:
```javascript
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'klein-blue': '#0022FF',
        'klein-blue-hover': '#001ACC',
      },
      fontFamily: {
        'display': ['Monument Extended', 'Helvetica Now Display', 'Arial Black', 'sans-serif'],
        'sans': ['Space Grotesk', 'Inter', 'system-ui', 'sans-serif'],
        'mono': ['JetBrains Mono', 'SF Mono', 'monospace'],
      },
    },
  },
  plugins: [],
}
```

**Step 5: 创建 HTML 入口**

在 `frontend/index.html`:
```html
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AI Blog</title>
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;700&display=swap" rel="stylesheet">
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.ts"></script>
  </body>
</html>
```

**Step 6: 创建主入口文件**

在 `frontend/src/main.ts`:
```typescript
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './assets/styles/main.css'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.mount('#app')
```

**Step 7: 创建根组件**

在 `frontend/src/App.vue`:
```vue
<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup lang="ts">
</script>
```

**Step 8: 安装依赖**

```bash
cd frontend && npm install
```

---

## Phase 2: 数据库设计

### Task 3: 数据库实体类创建

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/entity/Post.java`
- Create: `backend/src/main/java/com/mydotey/blog/entity/Tag.java`
- Create: `backend/src/main/java/com/mydotey/blog/entity/Comment.java`
- Create: `backend/src/main/java/com/mydotey/blog/entity/Admin.java`

**Step 1: 创建 Post 实体**

在 `backend/src/main/java/com/mydotey/blog/entity/Post.java`:
```java
package com.mydotey.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 200, unique = true)
    private String slug;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String summary;

    @Column(length = 500)
    private String coverImage;

    @Column(length = 20)
    private String status = "DRAFT";

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long views = 0L;

    @ManyToMany
    @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
```

**Step 2: 创建 Tag 实体**

在 `backend/src/main/java/com/mydotey/blog/entity/Tag.java`:
```java
package com.mydotey.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String slug;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

**Step 3: 创建 Comment 实体**

在 `backend/src/main/java/com/mydotey/blog/entity/Comment.java`:
```java
package com.mydotey.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    private Long parentId;

    @Column(length = 50)
    private String authorName;

    @Column(length = 100)
    private String authorEmail;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 20)
    private String status = "PENDING";

    @Column(length = 45)
    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String userAgent;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

**Step 4: 创建 Admin 实体**

在 `backend/src/main/java/com/mydotey/blog/entity/Admin.java`:
```java
package com.mydotey.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

---

### Task 4: Repository 层创建

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/repository/PostRepository.java`
- Create: `backend/src/main/java/com/mydotey/blog/repository/TagRepository.java`
- Create: `backend/src/main/java/com/mydotey/blog/repository/CommentRepository.java`
- Create: `backend/src/main/java/com/mydotey/blog/repository/AdminRepository.java`

**Step 1: 创建 PostRepository**

在 `backend/src/main/java/com/mydotey/blog/repository/PostRepository.java`:
```java
package com.mydotey.blog.repository;

import com.mydotey.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);

    Page<Post> findByStatus(String status, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.slug = :tagSlug AND p.status = :status")
    Page<Post> findByTagSlugAndStatus(String tagSlug, String status, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.status = :status AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Post> searchByKeyword(String keyword, String status, Pageable pageable);
}
```

**Step 2: 创建 TagRepository**

在 `backend/src/main/java/com/mydotey/blog/repository/TagRepository.java`:
```java
package com.mydotey.blog.repository;

import com.mydotey.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findBySlug(String slug);
    Optional<Tag> findByName(String name);
}
```

**Step 3: 创建 CommentRepository**

在 `backend/src/main/java/com/mydotey/blog/repository/CommentRepository.java`:
```java
package com.mydotey.blog.repository;

import com.mydotey.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndStatus(Long postId, String status);
    Page<Comment> findByStatus(String status, Pageable pageable);
}
```

**Step 4: 创建 AdminRepository**

在 `backend/src/main/java/com/mydotey/blog/repository/AdminRepository.java`:
```java
package com.mydotey.blog.repository;

import com.mydotey.blog.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
```

---

## Phase 3: 后端核心功能

### Task 5: DTO 和请求/响应类

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/dto/PostDTO.java`
- Create: `backend/src/main/java/com/mydotey/blog/dto/CreatePostRequest.java`
- Create: `backend/src/main/java/com/mydotey/blog/dto/CommentDTO.java`
- Create: `backend/src/main/java/com/mydotey/blog/dto/CreateCommentRequest.java`
- Create: `backend/src/main/java/com/mydotey/blog/dto/LoginRequest.java`
- Create: `backend/src/main/java/com/mydotey/blog/dto/LoginResponse.java`

**Step 1: 创建 PostDTO**

在 `backend/src/main/java/com/mydotey/blog/dto/PostDTO.java`:
```java
package com.mydotey.blog.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String summary;
    private String coverImage;
    private String status;
    private Long views;
    private Set<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

**Step 2: 创建 CreatePostRequest**

在 `backend/src/main/java/com/mydotey/blog/dto/CreatePostRequest.java`:
```java
package com.mydotey.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    @NotBlank
    private String content;

    private String summary;
    private String coverImage;
    private String status = "DRAFT";
    private Set<String> tagNames;
}
```

**Step 3: 创建 CommentDTO**

在 `backend/src/main/java/com/mydotey/blog/dto/CommentDTO.java`:
```java
package com.mydotey.blog.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private Long postId;
    private Long parentId;
    private String authorName;
    private String content;
    private String status;
    private LocalDateTime createdAt;
}
```

**Step 4: 创建 CreateCommentRequest**

在 `backend/src/main/java/com/mydotey/blog/dto/CreateCommentRequest.java`:
```java
package com.mydotey.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @NotNull
    private Long postId;

    private Long parentId;

    private String authorName;
    private String authorEmail;

    @NotBlank
    private String content;
}
```

**Step 5: 创建 LoginRequest**

在 `backend/src/main/java/com/mydotey/blog/dto/LoginRequest.java`:
```java
package com.mydotey.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
```

**Step 6: 创建 LoginResponse**

在 `backend/src/main/java/com/mydotey/blog/dto/LoginResponse.java`:
```java
package com.mydotey.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
}
```

---

### Task 6: JWT 工具类

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/util/JwtUtil.java`

**Step 1: 创建 JWT 工具类**

在 `backend/src/main/java/com/mydotey/blog/util/JwtUtil.java`:
```java
package com.mydotey.blog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("role", "ADMIN")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

---

### Task 7: Spring Security 配置

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/config/SecurityConfig.java`
- Create: `backend/src/main/java/com/mydotey/blog/filter/JwtAuthenticationFilter.java`

**Step 1: 创建 Security 配置**

在 `backend/src/main/java/com/mydotey/blog/config/SecurityConfig.java`:
```java
package com.mydotey.blog.config;

import com.mydotey.blog.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/posts/**", "/api/tags/**", "/api/comments", "/api/auth/login").permitAll()
                .requestMatchers("/api/admin/**").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

**Step 2: 创建 JWT 过滤器**

在 `backend/src/main/java/com/mydotey/blog/filter/JwtAuthenticationFilter.java`:
```java
package com.mydotey.blog.filter;

import com.mydotey.blog.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsername(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

---

### Task 8: Service 层实现

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/service/PostService.java`
- Create: `backend/src/main/java/com/mydotey/blog/service/TagService.java`
- Create: `backend/src/main/java/com/mydotey/blog/service/CommentService.java`
- Create: `backend/src/main/java/com/mydotey/blog/service/AuthService.java`

**Step 1: 创建 PostService**

在 `backend/src/main/java/com/mydotey/blog/service/PostService.java`:
```java
package com.mydotey.blog.service;

import com.mydotey.blog.dto.CreatePostRequest;
import com.mydotey.blog.dto.PostDTO;
import com.mydotey.blog.entity.Post;
import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.repository.PostRepository;
import com.mydotey.blog.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Page<PostDTO> getPublishedPosts(Pageable pageable) {
        return postRepository.findByStatus("PUBLISHED", pageable).map(this::toDTO);
    }

    public Page<PostDTO> getPostsByTag(String tagSlug, Pageable pageable) {
        return postRepository.findByTagSlugAndStatus(tagSlug, "PUBLISHED", pageable).map(this::toDTO);
    }

    public Page<PostDTO> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchByKeyword(keyword, "PUBLISHED", pageable).map(this::toDTO);
    }

    @Transactional
    public PostDTO getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        return toDTO(post);
    }

    @Transactional
    public PostDTO createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setContent(request.getContent());
        post.setSummary(request.getSummary());
        post.setCoverImage(request.getCoverImage());
        post.setStatus(request.getStatus());

        if (request.getTagNames() != null) {
            Set<Tag> tags = request.getTagNames().stream()
                .map(name -> tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag tag = new Tag();
                        tag.setName(name);
                        tag.setSlug(name.toLowerCase().replaceAll("\\s+", "-"));
                        return tagRepository.save(tag);
                    }))
                .collect(Collectors.toSet());
            post.setTags(tags);
        }

        return toDTO(postRepository.save(post));
    }

    @Transactional
    public PostDTO updatePost(Long id, CreatePostRequest request) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setContent(request.getContent());
        post.setSummary(request.getSummary());
        post.setCoverImage(request.getCoverImage());
        post.setStatus(request.getStatus());

        if (request.getTagNames() != null) {
            Set<Tag> tags = request.getTagNames().stream()
                .map(name -> tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag tag = new Tag();
                        tag.setName(name);
                        tag.setSlug(name.toLowerCase().replaceAll("\\s+", "-"));
                        return tagRepository.save(tag);
                    }))
                .collect(Collectors.toSet());
            post.setTags(tags);
        }

        return toDTO(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Page<PostDTO> getAllPostsForAdmin(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::toDTO);
    }

    private PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setContent(post.getContent());
        dto.setSummary(post.getSummary());
        dto.setCoverImage(post.getCoverImage());
        dto.setStatus(post.getStatus());
        dto.setViews(post.getViews());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        if (post.getTags() != null) {
            dto.setTags(post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        }

        return dto;
    }
}
```

**Step 2: 创建 TagService**

在 `backend/src/main/java/com/mydotey/blog/service/TagService.java`:
```java
package com.mydotey.blog.service;

import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setSlug(name.toLowerCase().replaceAll("\\s+", "-"));
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
```

**Step 3: 创建 CommentService**

在 `backend/src/main/java/com/mydotey/blog/service/CommentService.java`:
```java
package com.mydotey.blog.service;

import com.mydotey.blog.dto.CommentDTO;
import com.mydotey.blog.dto.CreateCommentRequest;
import com.mydotey.blog.entity.Comment;
import com.mydotey.blog.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentDTO> getApprovedComments(Long postId) {
        return commentRepository.findByPostIdAndStatus(postId, "APPROVED")
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public CommentDTO createComment(CreateCommentRequest request, HttpServletRequest httpRequest) {
        Comment comment = new Comment();
        comment.setPostId(request.getPostId());
        comment.setParentId(request.getParentId());
        comment.setAuthorName(request.getAuthorName());
        comment.setAuthorEmail(request.getAuthorEmail());
        comment.setContent(request.getContent());
        comment.setStatus("PENDING");
        comment.setIpAddress(getClientIp(httpRequest));
        comment.setUserAgent(httpRequest.getHeader("User-Agent"));

        return toDTO(commentRepository.save(comment));
    }

    public Page<CommentDTO> getAllCommentsForAdmin(Pageable pageable) {
        return commentRepository.findAll(pageable).map(this::toDTO);
    }

    public CommentDTO updateCommentStatus(Long id, String status) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setStatus(status);
        return toDTO(commentRepository.save(comment));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPostId());
        dto.setParentId(comment.getParentId());
        dto.setAuthorName(comment.getAuthorName());
        dto.setContent(comment.getContent());
        dto.setStatus(comment.getStatus());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
```

**Step 4: 创建 AuthService**

在 `backend/src/main/java/com/mydotey/blog/service/AuthService.java`:
```java
package com.mydotey.blog.service;

import com.mydotey.blog.dto.LoginRequest;
import com.mydotey.blog.dto.LoginResponse;
import com.mydotey.blog.entity.Admin;
import com.mydotey.blog.repository.AdminRepository;
import com.mydotey.blog.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Admin admin = adminRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(admin.getUsername(), admin.getId());
        return new LoginResponse(token, admin.getUsername());
    }

    public void createAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPasswordHash(passwordEncoder.encode(password));
        adminRepository.save(admin);
    }
}
```

---

### Task 9: Controller 层实现

**Files:**
- Create: `backend/src/main/java/com/mydotey/blog/controller/PostController.java`
- Create: `backend/src/main/java/com/mydotey/blog/controller/TagController.java`
- Create: `backend/src/main/java/com/mydotey/blog/controller/CommentController.java`
- Create: `backend/src/main/java/com/mydotey/blog/controller/AuthController.java`
- Create: `backend/src/main/java/com/mydotey/blog/controller/AdminController.java`

**Step 1: 创建 PostController**

在 `backend/src/main/java/com/mydotey/blog/controller/PostController.java`:
```java
package com.mydotey.blog.controller;

import com.mydotey.blog.dto.PostDTO;
import com.mydotey.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostDTO> getPosts(
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        if (tag != null && !tag.isEmpty()) {
            return postService.getPostsByTag(tag, pageRequest);
        } else if (search != null && !search.isEmpty()) {
            return postService.searchPosts(search, pageRequest);
        } else {
            return postService.getPublishedPosts(pageRequest);
        }
    }

    @GetMapping("/{slug}")
    public PostDTO getPost(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }
}
```

**Step 2: 创建 TagController**

在 `backend/src/main/java/com/mydotey/blog/controller/TagController.java`:
```java
package com.mydotey.blog.controller;

import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getAllTags();
    }
}
```

**Step 3: 创建 CommentController**

在 `backend/src/main/java/com/mydotey/blog/controller/CommentController.java`:
```java
package com.mydotey.blog.controller;

import com.mydotey.blog.dto.CommentDTO;
import com.mydotey.blog.dto.CreateCommentRequest;
import com.mydotey.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getComments(@RequestParam Long postId) {
        return commentService.getApprovedComments(postId);
    }

    @PostMapping
    public CommentDTO createComment(@Valid @RequestBody CreateCommentRequest request, HttpServletRequest httpRequest) {
        return commentService.createComment(request, httpRequest);
    }
}
```

**Step 4: 创建 AuthController**

在 `backend/src/main/java/com/mydotey/blog/controller/AuthController.java`:
```java
package com.mydotey.blog.controller;

import com.mydotey.blog.dto.LoginRequest;
import com.mydotey.blog.dto.LoginResponse;
import com.mydotey.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
```

**Step 5: 创建 AdminController**

在 `backend/src/main/java/com/mydotey/blog/controller/AdminController.java`:
```java
package com.mydotey.blog.controller;

import com.mydotey.blog.dto.CommentDTO;
import com.mydotey.blog.dto.CreatePostRequest;
import com.mydotey.blog.dto.PostDTO;
import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.service.CommentService;
import com.mydotey.blog.service.PostService;
import com.mydotey.blog.service.TagService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;

    public AdminController(PostService postService, CommentService commentService, TagService tagService) {
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;
    }

    // Post Management
    @GetMapping("/posts")
    public Page<PostDTO> getPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getAllPostsForAdmin(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @PostMapping("/posts")
    public PostDTO createPost(@Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }

    @PutMapping("/posts/{id}")
    public PostDTO updatePost(@PathVariable Long id, @Valid @RequestBody CreatePostRequest request) {
        return postService.updatePost(id, request);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    // Comment Management
    @GetMapping("/comments")
    public Page<CommentDTO> getComments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return commentService.getAllCommentsForAdmin(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @PutMapping("/comments/{id}/status")
    public CommentDTO updateCommentStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return commentService.updateCommentStatus(id, body.get("status"));
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    // Tag Management
    @PostMapping("/tags")
    public Tag createTag(@RequestBody Map<String, String> body) {
        return tagService.createTag(body.get("name"));
    }

    @DeleteMapping("/tags/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
```

---

## Phase 4: 前端核心开发

### Task 10: 样式系统

**Files:**
- Create: `frontend/src/assets/styles/main.css`
- Create: `frontend/postcss.config.js`

**Step 1: 创建主样式文件**

在 `frontend/src/assets/styles/main.css`:
```css
@tailwind base;
@tailwind components;
@tailwind utilities;

:root {
  --bg-primary: #FAFAFA;
  --bg-secondary: #F5F5F5;
  --text-primary: #1A1A1A;
  --text-secondary: #6B6B6B;
  --text-tertiary: #9E9E9E;
  --border: #E8E8E8;
  --accent: #0022FF;
  --accent-hover: #001ACC;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Space Grotesk', 'Inter', system-ui, sans-serif;
  background: var(--bg-primary);
  color: var(--text-primary);
  line-height: 1.6;
}

a {
  text-decoration: none;
  color: inherit;
  transition: color 0.25s;
}

a:hover {
  color: var(--accent);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

@media (max-width: 768px) {
  .container {
    padding: 0 1rem;
  }
}

/* Typography */
.display-title {
  font-family: 'Monument Extended', 'Helvetica Now Display', 'Arial Black', sans-serif;
  font-weight: 900;
  letter-spacing: -0.03em;
  line-height: 1.1;
}

.prose {
  font-size: 1.125rem;
  line-height: 1.8;
}

.prose h2 {
  font-size: 2rem;
  font-weight: 700;
  margin-top: 3rem;
  margin-bottom: 1.5rem;
}

.prose h3 {
  font-size: 1.5rem;
  font-weight: 700;
  margin-top: 2rem;
  margin-bottom: 1rem;
}

.prose p {
  margin-bottom: 1.5rem;
}

.prose code {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  background: var(--bg-secondary);
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.9em;
}

.prose pre {
  background: var(--text-primary);
  color: var(--bg-primary);
  padding: 1.5rem;
  border-radius: 8px;
  overflow-x: auto;
  margin-bottom: 1.5rem;
}

.prose pre code {
  background: none;
  padding: 0;
  color: inherit;
}

/* Buttons */
.btn-primary {
  background: var(--accent);
  color: white;
  padding: 1rem 2rem;
  border-radius: 9999px;
  font-size: 0.875rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  border: none;
  cursor: pointer;
  transition: all 0.25s;
}

.btn-primary:hover {
  background: var(--accent-hover);
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(0, 34, 255, 0.3);
}

/* Input */
.input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid var(--border);
  border-radius: 8px;
  font-size: 1rem;
  font-family: inherit;
  transition: border-color 0.25s;
}

.input:focus {
  outline: none;
  border-color: var(--accent);
}

.textarea {
  min-height: 120px;
  resize: vertical;
}
```

**Step 2: 创建 PostCSS 配置**

在 `frontend/postcss.config.js`:
```javascript
export default {
  plugins: {
    tailwindcss: {},
    autoprefixer: {},
  },
}
```

---

### Task 11: API 服务

**Files:**
- Create: `frontend/src/services/api.ts`
- Create: `frontend/src/services/auth.ts`

**Step 1: 创建 API 基础服务**

在 `frontend/src/services/api.ts`:
```typescript
import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export interface Post {
  id: number
  title: string
  slug: string
  content: string
  summary: string
  coverImage?: string
  status: string
  views: number
  tags: string[]
  createdAt: string
  updatedAt: string
}

export interface Comment {
  id: number
  postId: number
  parentId?: number
  authorName: string
  content: string
  status: string
  createdAt: string
}

export interface Tag {
  id: number
  name: string
  slug: string
}

export interface PageResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

// Public APIs
export const getPosts = (params?: { tag?: string; search?: string; page?: number; size?: number }) =>
  api.get<PageResponse<Post>>('/posts', { params })

export const getPost = (slug: string) =>
  api.get<Post>(`/posts/${slug}`)

export const getTags = () =>
  api.get<Tag[]>('/tags')

export const getComments = (postId: number) =>
  api.get<Comment[]>('/comments', { params: { postId } })

export const createComment = (data: {
  postId: number
  parentId?: number
  authorName?: string
  authorEmail?: string
  content: string
}) => api.post<Comment>('/comments', data)

// Admin APIs
export const adminGetPosts = (page = 0, size = 10) =>
  api.get<PageResponse<Post>>('/admin/posts', { params: { page, size } })

export const adminCreatePost = (data: {
  title: string
  slug: string
  content: string
  summary?: string
  coverImage?: string
  status?: string
  tagNames?: string[]
}) => api.post<Post>('/admin/posts', data)

export const adminUpdatePost = (id: number, data: any) =>
  api.put<Post>(`/admin/posts/${id}`, data)

export const adminDeletePost = (id: number) =>
  api.delete(`/admin/posts/${id}`)

export const adminGetComments = (page = 0, size = 20) =>
  api.get<PageResponse<Comment>>('/admin/comments', { params: { page, size } })

export const adminUpdateCommentStatus = (id: number, status: string) =>
  api.put<Comment>(`/admin/comments/${id}/status`, { status })

export const adminDeleteComment = (id: number) =>
  api.delete(`/admin/comments/${id}`)

export const adminCreateTag = (name: string) =>
  api.post<Tag>('/admin/tags', { name })

export const adminDeleteTag = (id: number) =>
  api.delete(`/admin/tags/${id}`)

export default api
```

**Step 2: 创建认证服务**

在 `frontend/src/services/auth.ts`:
```typescript
import api from './api'

export interface LoginResponse {
  token: string
  username: string
}

export const login = async (username: string, password: string) => {
  const response = await api.post<LoginResponse>('/auth/login', { username, password })
  const { token, username: user } = response.data
  localStorage.setItem('token', token)
  localStorage.setItem('username', user)
  return response.data
}

export const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
}

export const isAuthenticated = () => {
  return !!localStorage.getItem('token')
}

export const getUsername = () => {
  return localStorage.getItem('username')
}
```

---

### Task 12: 路由配置

**Files:**
- Create: `frontend/src/router/index.ts`

**Step 1: 创建路由配置**

在 `frontend/src/router/index.ts`:
```typescript
import { createRouter, createWebHistory } from 'vue-router'
import { isAuthenticated } from '@/services/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/Home.vue')
    },
    {
      path: '/posts/:slug',
      name: 'PostDetail',
      component: () => import('@/views/PostDetail.vue')
    },
    {
      path: '/tags/:slug',
      name: 'TagPosts',
      component: () => import('@/views/TagPosts.vue')
    },
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: () => import('@/views/admin/Login.vue')
    },
    {
      path: '/admin',
      name: 'AdminDashboard',
      component: () => import('@/views/admin/Dashboard.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/posts',
      name: 'AdminPosts',
      component: () => import('@/views/admin/Posts.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/posts/new',
      name: 'AdminNewPost',
      component: () => import('@/views/admin/PostEditor.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/posts/edit/:id',
      name: 'AdminEditPost',
      component: () => import('@/views/admin/PostEditor.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/comments',
      name: 'AdminComments',
      component: () => import('@/views/admin/Comments.vue'),
      meta: { requiresAuth: true }
    }
  ],
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !isAuthenticated()) {
    next({ name: 'AdminLogin' })
  } else {
    next()
  }
})

export default router
```

---

### Task 13: 公开页面组件

**Files:**
- Modify: `frontend/src/views/Home.vue`
- Create: `frontend/src/views/PostDetail.vue`
- Create: `frontend/src/views/TagPosts.vue`
- Create: `frontend/src/components/Navbar.vue`
- Create: `frontend/src/components/PostCard.vue`
- Create: `frontend/src/components/CommentSection.vue`

**Step 1: 更新 Home 页面**

在 `frontend/src/views/Home.vue`:
```vue
<template>
  <div>
    <Navbar />

    <main class="container py-24">
      <div class="mb-32">
        <h1 class="display-title text-7xl md:text-9xl mb-8">AI Blog</h1>
        <p class="text-2xl text-secondary max-w-2xl">探索人工智能、机器学习和技术创新的前沿思想</p>
      </div>

      <div v-if="loading" class="text-center py-12">
        <p class="text-secondary">加载中...</p>
      </div>

      <div v-else-if="error" class="text-center py-12">
        <p class="text-red-500">{{ error }}</p>
      </div>

      <div v-else class="grid gap-24">
        <PostCard
          v-for="post in posts"
          :key="post.id"
          :post="post"
        />
      </div>

      <div v-if="hasMore" class="text-center mt-24">
        <button @click="loadMore" class="btn-primary">加载更多</button>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getPosts, type Post } from '@/services/api'
import Navbar from '@/components/Navbar.vue'
import PostCard from '@/components/PostCard.vue'

const posts = ref<Post[]>([])
const loading = ref(true)
const error = ref('')
const page = ref(0)
const totalPages = ref(0)

const hasMore = computed(() => page.value < totalPages.value - 1)

const loadPosts = async () => {
  try {
    loading.value = true
    const response = await getPosts({ page: page.value, size: 10 })
    posts.value.push(...response.data.content)
    totalPages.value = response.data.totalPages
  } catch (err: any) {
    error.value = err.message || '加载失败'
  } finally {
    loading.value = false
  }
}

const loadMore = () => {
  page.value++
  loadPosts()
}

onMounted(() => {
  loadPosts()
})
</script>
```

**Step 2: 创建 Navbar 组件**

在 `frontend/src/components/Navbar.vue`:
```vue
<template>
  <nav class="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-sm border-b border-gray-200 z-50">
    <div class="container flex items-center justify-between h-20">
      <router-link to="/" class="font-bold text-xl">AI BLOG</router-link>

      <div class="flex gap-8">
        <router-link to="/" class="nav-link">首页</router-link>
        <router-link
          v-for="tag in tags"
          :key="tag.id"
          :to="`/tags/${tag.slug}`"
          class="nav-link"
        >
          {{ tag.name }}
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getTags, type Tag } from '@/services/api'

const tags = ref<Tag[]>([])

onMounted(async () => {
  try {
    const response = await getTags()
    tags.value = response.data
  } catch (err) {
    console.error(err)
  }
})
</script>

<style scoped>
.nav-link {
  font-size: 0.875rem;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  position: relative;
  padding-bottom: 4px;
}

.nav-link::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background: var(--accent);
  transition: width 0.25s;
}

.nav-link:hover::after {
  width: 100%;
}
</style>
```

**Step 3: 创建 PostCard 组件**

在 `frontend/src/components/PostCard.vue`:
```vue
<template>
  <article class="border-t border-gray-200 pt-12 hover:border-klein-blue transition-colors duration-250">
    <div class="flex gap-4 mb-4">
      <span
        v-for="tag in post.tags"
        :key="tag"
        class="text-xs font-semibold uppercase text-klein-blue"
      >
        {{ tag }}
      </span>
    </div>

    <router-link :to="`/posts/${post.slug}`">
      <h2 class="text-5xl font-bold mb-6 hover:text-klein-blue transition-colors">
        {{ post.title }}
      </h2>
    </router-link>

    <p class="text-lg text-secondary mb-6">{{ post.summary }}</p>

    <div class="flex gap-6 text-sm text-tertiary">
      <span>{{ formatDate(post.createdAt) }}</span>
      <span>{{ post.views }} 次阅读</span>
    </div>
  </article>
</template>

<script setup lang="ts">
import type { Post } from '@/services/api'

defineProps<{ post: Post }>()

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}
</script>
```

**Step 4: 创建 PostDetail 页面**

在 `frontend/src/views/PostDetail.vue`:
```vue
<template>
  <div>
    <Navbar />

    <main class="container py-32">
      <div v-if="loading" class="text-center py-12">
        <p class="text-secondary">加载中...</p>
      </div>

      <div v-else-if="error" class="text-center py-12">
        <p class="text-red-500">{{ error }}</p>
      </div>

      <article v-else class="max-w-4xl mx-auto">
        <div class="flex gap-4 mb-8">
          <span
            v-for="tag in post?.tags"
            :key="tag"
            class="text-xs font-semibold uppercase text-klein-blue"
          >
            {{ tag }}
          </span>
        </div>

        <h1 class="display-title text-6xl mb-8">{{ post?.title }}</h1>

        <div class="flex gap-6 text-sm text-tertiary mb-16">
          <span>{{ formatDate(post?.createdAt || '') }}</span>
          <span>{{ post?.views }} 次阅读</span>
        </div>

        <div class="prose" v-html="renderedContent"></div>
      </article>

      <CommentSection v-if="post" :post-id="post.id" class="max-w-4xl mx-auto mt-32" />
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { marked } from 'marked'
import { getPost, type Post } from '@/services/api'
import Navbar from '@/components/Navbar.vue'
import CommentSection from '@/components/CommentSection.vue'

const route = useRoute()
const post = ref<Post | null>(null)
const loading = ref(true)
const error = ref('')

const renderedContent = computed(() => {
  if (!post.value) return ''
  return marked(post.value.content)
})

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}

onMounted(async () => {
  try {
    const response = await getPost(route.params.slug as string)
    post.value = response.data
  } catch (err: any) {
    error.value = err.message || '加载失败'
  } finally {
    loading.value = false
  }
})
</script>
```

**Step 5: 创建 CommentSection 组件**

在 `frontend/src/components/CommentSection.vue`:
```vue
<template>
  <section class="border-t border-gray-200 pt-16">
    <h2 class="text-4xl font-bold mb-12">评论</h2>

    <form @submit.prevent="submitComment" class="mb-16">
      <input
        v-model="form.authorName"
        type="text"
        placeholder="昵称（可选）"
        class="input mb-4"
      />
      <input
        v-model="form.authorEmail"
        type="email"
        placeholder="邮箱（可选）"
        class="input mb-4"
      />
      <textarea
        v-model="form.content"
        placeholder="写下你的评论..."
        class="input textarea mb-4"
        required
      ></textarea>
      <button type="submit" class="btn-primary" :disabled="submitting">
        {{ submitting ? '发送中...' : '发表评论' }}
      </button>
    </form>

    <div v-if="comments.length === 0" class="text-center py-8 text-secondary">
      暂无评论
    </div>

    <div v-else class="space-y-8">
      <div v-for="comment in comments" :key="comment.id" class="border-b border-gray-200 pb-8">
        <div class="flex items-center gap-4 mb-4">
          <span class="font-semibold">{{ comment.authorName || '匿名' }}</span>
          <span class="text-sm text-tertiary">{{ formatDate(comment.createdAt) }}</span>
        </div>
        <p class="text-secondary">{{ comment.content }}</p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getComments, createComment, type Comment } from '@/services/api'

const props = defineProps<{ postId: number }>()

const comments = ref<Comment[]>([])
const submitting = ref(false)
const form = ref({
  authorName: '',
  authorEmail: '',
  content: ''
})

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}

const loadComments = async () => {
  try {
    const response = await getComments(props.postId)
    comments.value = response.data
  } catch (err) {
    console.error(err)
  }
}

const submitComment = async () => {
  try {
    submitting.value = true
    await createComment({
      postId: props.postId,
      authorName: form.value.authorName,
      authorEmail: form.value.authorEmail,
      content: form.value.content
    })
    form.value = { authorName: '', authorEmail: '', content: '' }
    alert('评论已提交，等待审核')
  } catch (err: any) {
    alert(err.message || '提交失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadComments()
})
</script>
```

**Step 6: 创建 TagPosts 页面**

在 `frontend/src/views/TagPosts.vue`:
```vue
<template>
  <div>
    <Navbar />

    <main class="container py-32">
      <h1 class="display-title text-6xl mb-16">{{ tagSlug }}</h1>

      <div v-if="loading" class="text-center py-12">
        <p class="text-secondary">加载中...</p>
      </div>

      <div v-else-if="error" class="text-center py-12">
        <p class="text-red-500">{{ error }}</p>
      </div>

      <div v-else class="grid gap-24">
        <PostCard
          v-for="post in posts"
          :key="post.id"
          :post="post"
        />
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getPosts, type Post } from '@/services/api'
import Navbar from '@/components/Navbar.vue'
import PostCard from '@/components/PostCard.vue'

const route = useRoute()
const tagSlug = computed(() => route.params.slug as string)
const posts = ref<Post[]>([])
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    const response = await getPosts({ tag: tagSlug.value })
    posts.value = response.data.content
  } catch (err: any) {
    error.value = err.message || '加载失败'
  } finally {
    loading.value = false
  }
})
</script>
```

---

### Task 14: 管理后台页面

**Files:**
- Create: `frontend/src/views/admin/Login.vue`
- Create: `frontend/src/views/admin/Dashboard.vue`
- Create: `frontend/src/views/admin/Posts.vue`
- Create: `frontend/src/views/admin/PostEditor.vue`
- Create: `frontend/src/views/admin/Comments.vue`

**Step 1: 创建登录页面**

在 `frontend/src/views/admin/Login.vue`:
```vue
<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="max-w-md w-full bg-white p-12 rounded-lg shadow-lg">
      <h1 class="text-4xl font-bold mb-8">管理员登录</h1>

      <form @submit.prevent="handleLogin">
        <input
          v-model="username"
          type="text"
          placeholder="用户名"
          class="input mb-4"
          required
        />
        <input
          v-model="password"
          type="password"
          placeholder="密码"
          class="input mb-6"
          required
        />
        <button type="submit" class="btn-primary w-full" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>

      <p v-if="error" class="text-red-500 mt-4">{{ error }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/services/auth'

const router = useRouter()
const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

const handleLogin = async () => {
  try {
    loading.value = true
    error.value = ''
    await login(username.value, password.value)
    router.push('/admin')
  } catch (err: any) {
    error.value = err.response?.data?.message || '登录失败'
  } finally {
    loading.value = false
  }
}
</script>
```

**Step 2: 创建管理后台首页**

在 `frontend/src/views/admin/Dashboard.vue`:
```vue
<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <h1 class="font-bold text-xl">管理后台</h1>
        <div class="flex gap-6">
          <router-link to="/admin/posts" class="hover:text-klein-blue">文章</router-link>
          <router-link to="/admin/comments" class="hover:text-klein-blue">评论</router-link>
          <button @click="handleLogout" class="text-red-500">退出</button>
        </div>
      </div>
    </nav>

    <main class="container py-12">
      <h2 class="text-4xl font-bold mb-8">欢迎回来，{{ username }}</h2>

      <div class="grid grid-cols-3 gap-6">
        <router-link to="/admin/posts" class="bg-white p-8 rounded-lg shadow hover:shadow-lg transition">
          <h3 class="text-2xl font-bold mb-2">文章管理</h3>
          <p class="text-secondary">创建和管理博客文章</p>
        </router-link>

        <router-link to="/admin/comments" class="bg-white p-8 rounded-lg shadow hover:shadow-lg transition">
          <h3 class="text-2xl font-bold mb-2">评论审核</h3>
          <p class="text-secondary">审核和管理评论</p>
        </router-link>

        <router-link to="/" class="bg-white p-8 rounded-lg shadow hover:shadow-lg transition">
          <h3 class="text-2xl font-bold mb-2">查看网站</h3>
          <p class="text-secondary">前往公开页面</p>
        </router-link>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { logout, getUsername } from '@/services/auth'

const router = useRouter()
const username = getUsername()

const handleLogout = () => {
  logout()
  router.push('/admin/login')
}
</script>
```

**Step 3: 创建文章管理页面**

在 `frontend/src/views/admin/Posts.vue`:
```vue
<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <router-link to="/admin" class="font-bold text-xl">管理后台</router-link>
        <button @click="logout" class="text-red-500">退出</button>
      </div>
    </nav>

    <main class="container py-12">
      <div class="flex items-center justify-between mb-8">
        <h2 class="text-4xl font-bold">文章管理</h2>
        <router-link to="/admin/posts/new" class="btn-primary">新建文章</router-link>
      </div>

      <div class="bg-white rounded-lg shadow">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left p-4">标题</th>
              <th class="text-left p-4">状态</th>
              <th class="text-left p-4">浏览量</th>
              <th class="text-left p-4">创建时间</th>
              <th class="text-left p-4">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in posts" :key="post.id" class="border-b border-gray-200">
              <td class="p-4">{{ post.title }}</td>
              <td class="p-4">{{ post.status }}</td>
              <td class="p-4">{{ post.views }}</td>
              <td class="p-4">{{ formatDate(post.createdAt) }}</td>
              <td class="p-4 flex gap-2">
                <router-link :to="`/admin/posts/edit/${post.id}`" class="text-klein-blue">编辑</router-link>
                <button @click="deletePost(post.id)" class="text-red-500">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { adminGetPosts, adminDeletePost, type Post } from '@/services/api'
import { logout as authLogout } from '@/services/auth'

const router = useRouter()
const posts = ref<Post[]>([])

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

const loadPosts = async () => {
  try {
    const response = await adminGetPosts()
    posts.value = response.data.content
  } catch (err) {
    console.error(err)
  }
}

const deletePost = async (id: number) => {
  if (!confirm('确定删除？')) return
  try {
    await adminDeletePost(id)
    loadPosts()
  } catch (err) {
    alert('删除失败')
  }
}

const logout = () => {
  authLogout()
  router.push('/admin/login')
}

onMounted(() => {
  loadPosts()
})
</script>
```

**Step 4: 创建文章编辑器**

在 `frontend/src/views/admin/PostEditor.vue`:
```vue
<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <router-link to="/admin/posts" class="font-bold text-xl">返回文章列表</router-link>
      </div>
    </nav>

    <main class="container py-12">
      <h2 class="text-4xl font-bold mb-8">{{ isEdit ? '编辑文章' : '新建文章' }}</h2>

      <form @submit.prevent="handleSubmit" class="max-w-4xl">
        <input
          v-model="form.title"
          type="text"
          placeholder="文章标题"
          class="input mb-4"
          required
        />

        <input
          v-model="form.slug"
          type="text"
          placeholder="URL Slug"
          class="input mb-4"
          required
        />

        <input
          v-model="form.summary"
          type="text"
          placeholder="文章摘要"
          class="input mb-4"
        />

        <input
          v-model="form.coverImage"
          type="text"
          placeholder="封面图 URL"
          class="input mb-4"
        />

        <input
          v-model="tagsInput"
          type="text"
          placeholder="标签（逗号分隔）"
          class="input mb-4"
        />

        <select v-model="form.status" class="input mb-4">
          <option value="DRAFT">草稿</option>
          <option value="PUBLISHED">已发布</option>
        </select>

        <textarea
          v-model="form.content"
          placeholder="文章内容（Markdown）"
          class="input textarea mb-4"
          style="min-height: 400px"
          required
        ></textarea>

        <div class="flex gap-4">
          <button type="submit" class="btn-primary" :disabled="submitting">
            {{ submitting ? '保存中...' : '保存' }}
          </button>
          <router-link to="/admin/posts" class="btn-secondary">取消</router-link>
        </div>
      </form>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { adminCreatePost, adminUpdatePost, getPost } from '@/services/api'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)

const form = ref({
  title: '',
  slug: '',
  summary: '',
  coverImage: '',
  content: '',
  status: 'DRAFT'
})

const tagsInput = ref('')
const submitting = ref(false)

const handleSubmit = async () => {
  try {
    submitting.value = true
    const data = {
      ...form.value,
      tagNames: tagsInput.value.split(',').map(t => t.trim()).filter(Boolean)
    }

    if (isEdit.value) {
      await adminUpdatePost(Number(route.params.id), data)
    } else {
      await adminCreatePost(data)
    }

    router.push('/admin/posts')
  } catch (err: any) {
    alert(err.message || '保存失败')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  if (isEdit.value) {
    try {
      const response = await getPost(route.params.id as string)
      const post = response.data
      form.value = {
        title: post.title,
        slug: post.slug,
        summary: post.summary || '',
        coverImage: post.coverImage || '',
        content: post.content,
        status: post.status
      }
      tagsInput.value = post.tags.join(', ')
    } catch (err) {
      alert('加载失败')
    }
  }
})
</script>
```

**Step 5: 创建评论管理页面**

在 `frontend/src/views/admin/Comments.vue`:
```vue
<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <router-link to="/admin" class="font-bold text-xl">管理后台</router-link>
      </div>
    </nav>

    <main class="container py-12">
      <h2 class="text-4xl font-bold mb-8">评论管理</h2>

      <div class="bg-white rounded-lg shadow">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left p-4">作者</th>
              <th class="text-left p-4">内容</th>
              <th class="text-left p-4">状态</th>
              <th class="text-left p-4">时间</th>
              <th class="text-left p-4">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="comment in comments" :key="comment.id" class="border-b border-gray-200">
              <td class="p-4">{{ comment.authorName || '匿名' }}</td>
              <td class="p-4">{{ comment.content.substring(0, 50) }}...</td>
              <td class="p-4">{{ comment.status }}</td>
              <td class="p-4">{{ formatDate(comment.createdAt) }}</td>
              <td class="p-4 flex gap-2">
                <button
                  v-if="comment.status !== 'APPROVED'"
                  @click="approve(comment.id)"
                  class="text-green-500"
                >
                  通过
                </button>
                <button @click="deleteComment(comment.id)" class="text-red-500">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { adminGetComments, adminUpdateCommentStatus, adminDeleteComment, type Comment } from '@/services/api'

const comments = ref<Comment[]>([])

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

const loadComments = async () => {
  try {
    const response = await adminGetComments()
    comments.value = response.data.content
  } catch (err) {
    console.error(err)
  }
}

const approve = async (id: number) => {
  try {
    await adminUpdateCommentStatus(id, 'APPROVED')
    loadComments()
  } catch (err) {
    alert('操作失败')
  }
}

const deleteComment = async (id: number) => {
  if (!confirm('确定删除？')) return
  try {
    await adminDeleteComment(id)
    loadComments()
  } catch (err) {
    alert('删除失败')
  }
}

onMounted(() => {
  loadComments()
})
</script>
```

---

## Phase 5: 数据库初始化和启动

### Task 15: 数据库初始化脚本

**Files:**
- Create: `scripts/init-db.sql`
- Create: `scripts/create-admin.sh`

**Step 1: 创建数据库初始化脚本**

在 `scripts/init-db.sql`:
```sql
-- 创建数据库
CREATE DATABASE aiblog;

-- 连接到数据库
\c aiblog;

-- 创建管理员表
CREATE TABLE admins (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建文章表
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

-- 创建标签表
CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    slug VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建文章标签关联表
CREATE TABLE post_tags (
    post_id BIGINT REFERENCES posts(id) ON DELETE CASCADE,
    tag_id BIGINT REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);

-- 创建评论表
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

**Step 2: 创建管理员创建脚本**

在 `scripts/create-admin.sh`:
```bash
#!/bin/bash

# 这个脚本通过 Spring Boot 的 AuthService 创建管理员账号
# 需要先启动后端服务，然后手动调用 createAdmin 方法

echo "请手动执行以下操作创建管理员："
echo "1. 启动后端服务"
echo "2. 在代码中临时添加一个初始化方法调用 authService.createAdmin(\"admin\", \"password\")"
echo "3. 启动后删除该初始化代码"
```

**Step 3: 初始化数据库**

```bash
psql -U postgres -f scripts/init-db.sql
```

---

### Task 16: 启动和运行

**Step 1: 启动后端服务**

```bash
cd backend && ./gradlew bootRun
```

**Step 2: 启动前端开发服务器**

```bash
cd frontend && npm run dev
```

**Step 3: 访问应用**

打开浏览器访问:
- 前端: http://localhost:3000
- 后端: http://localhost:8080

---

## 完成清单

- [x] 后端项目结构
- [x] 前端项目结构
- [x] 数据库实体和 Repository
- [x] Service 和 Controller 层
- [x] JWT 认证
- [x] 前端样式系统
- [x] API 服务
- [x] 路由配置
- [x] 公开页面（首页、文章详情、标签页、评论）
- [x] 管理后台（登录、文章管理、评论审核）
- [x] 数据库初始化

---

## 后续优化建议

1. 添加 Markdown 编辑器（如 md-editor-v3）
2. 添加图片上传功能
3. 添加搜索功能
4. 添加分页组件
5. 添加验证码防护
6. 部署到生产环境
