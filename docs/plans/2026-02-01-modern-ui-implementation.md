# 现代化 UI 实施计划

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 将博客前端从极简风格升级为现代科技感设计，增加渐变、光晕、动画和装饰元素

**Architecture:** 保留现有 Vue 3 + Tailwind 架构，通过扩展 Tailwind 配置、新增全局样式和创建装饰组件来实现视觉升级

**Tech Stack:** Vue 3, TypeScript, Tailwind CSS, CSS Animations, IntersectionObserver API

---

## 前置条件

- 当前工作目录: `/home/koqizhao/Projects/mydotey-ai/ai-blog/.worktrees/feature/modern-ui`
- 前端依赖已安装 (`npm install` 完成)
- 后端构建成功

---

## Phase 1: 基础设施 - 色彩系统和样式基础

### Task 1: 扩展 Tailwind 配置

**Files:**
- Modify: `frontend/tailwind.config.js`

**Step 1: 扩展 Tailwind 配置添加新的颜色、阴影和动画**

```javascript
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'klein-blue': {
          DEFAULT: '#0022FF',
          light: '#0066FF',
          dark: '#001ACC',
        },
      },
      fontFamily: {
        'display': ['Monument Extended', 'Helvetica Now Display', 'Arial Black', 'sans-serif'],
        'sans': ['Space Grotesk', 'Inter', 'system-ui', 'sans-serif'],
        'mono': ['JetBrains Mono', 'SF Mono', 'monospace'],
      },
      boxShadow: {
        'glow-sm': '0 0 20px rgba(0,34,255,0.3)',
        'glow-md': '0 0 40px rgba(0,34,255,0.15)',
        'glow-lg': '0 0 60px rgba(0,34,255,0.2)',
        'card': '0 2px 8px rgba(0,34,255,0.08)',
        'card-hover': '0 20px 60px rgba(0,34,255,0.25)',
        'elevation-1': '0 2px 8px rgba(0,34,255,0.08)',
        'elevation-2': '0 8px 24px rgba(0,34,255,0.12)',
        'elevation-3': '0 16px 48px rgba(0,34,255,0.16)',
      },
      animation: {
        'float': 'float 20s ease-in-out infinite',
        'float-delayed': 'float 25s ease-in-out 5s infinite',
        'fade-in': 'fadeIn 0.6s ease-out',
        'fade-in-up': 'fadeInUp 0.6s ease-out',
        'fade-in-down': 'fadeInDown 0.6s ease-out',
      },
      keyframes: {
        float: {
          '0%, 100%': { transform: 'translate(0, 0) rotate(0deg)' },
          '33%': { transform: 'translate(30px, -30px) rotate(120deg)' },
          '66%': { transform: 'translate(-20px, 20px) rotate(240deg)' },
        },
        fadeIn: {
          from: { opacity: '0' },
          to: { opacity: '1' },
        },
        fadeInUp: {
          from: { opacity: '0', transform: 'translateY(30px)' },
          to: { opacity: '1', transform: 'translateY(0)' },
        },
        fadeInDown: {
          from: { opacity: '0', transform: 'translateY(-30px)' },
          to: { opacity: '1', transform: 'translateY(0)' },
        },
      },
      backdropBlur: {
        xs: '2px',
      },
    },
  },
  plugins: [],
}
```

**Step 2: 验证配置**

Run: `cd frontend && npm run dev`
Expected: 开发服务器启动，无配置错误

**Step 3: 停止开发服务器并提交**

```bash
# Ctrl+C 停止服务器
git add frontend/tailwind.config.js
git commit -m "feat: extend Tailwind config with gradients, shadows, and animations

- Add klein-blue color variants
- Add glow and elevation shadow utilities
- Add float and fade animation keyframes

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 2: 创建动画样式文件

**Files:**
- Create: `frontend/src/assets/styles/animations.css`

**Step 1: 创建动画样式文件**

```css
/* animations.css - 动画和过渡效果 */

/* 滚动触发的淡入动画 */
.fade-in-up {
  animation: fadeInUp 0.6s ease-out forwards;
}

.fade-in-up-stagger {
  opacity: 0;
  transform: translateY(30px);
}

.fade-in-up-stagger.visible {
  animation: fadeInUp 0.6s ease-out forwards;
}

/* 悬浮效果 */
.hover-lift {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.hover-lift:hover {
  transform: translateY(-8px);
}

/* 悬浮光晕 */
.hover-glow {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
}

.hover-glow::before {
  content: '';
  position: absolute;
  inset: 0;
  border-radius: inherit;
  opacity: 0;
  transition: opacity 0.3s ease;
  pointer-events: none;
  box-shadow: 0 0 40px rgba(0, 34, 255, 0.4);
}

.hover-glow:hover::before {
  opacity: 1;
}

/* 按钮按下效果 */
.btn-press {
  transition: transform 0.1s ease;
}

.btn-press:active {
  transform: scale(0.98);
}

/* 减少动画 (可访问性) */
@media (prefers-reduced-motion: reduce) {
  *,
  *::before,
  *::after {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}
```

**Step 2: 在主样式文件中导入**

Modify: `frontend/src/assets/styles/main.css`

在文件开头添加导入:
```css
@import './animations.css';
```

**Step 3: 验证样式加载**

Run: `cd frontend && npm run dev`
Expected: 无样式错误，开发服务器正常启动

**Step 4: 提交**

```bash
git add frontend/src/assets/styles/animations.css frontend/src/assets/styles/main.css
git commit -m "feat: add animations.css with hover and scroll effects

- Fade-in-up animations with stagger support
- Hover lift and glow effects
- Respects prefers-reduced-motion

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 3: 更新全局样式 - 渐变背景

**Files:**
- Modify: `frontend/src/assets/styles/main.css`

**Step 1: 更新 CSS 变量和背景渐变**

在 `:root` 部分更新:
```css
:root {
  --bg-primary: #FAFAFA;
  --bg-gradient: linear-gradient(180deg, #FAFAFA 0%, #F5F7FF 100%);
  --bg-secondary: #F5F5F5;
  --text-primary: #1A1A1A;
  --text-secondary: #6B6B6B;
  --text-tertiary: #9E9E9E;
  --border: #E8E8E8;
  --accent: #0022FF;
  --accent-hover: #001ACC;
  --gradient-blue: linear-gradient(135deg, #0022FF 0%, #0066FF 100%);
  --gradient-purple: linear-gradient(135deg, #0022FF 0%, #6600FF 100%);
}
```

更新 body 样式:
```css
body {
  font-family: 'Space Grotesk', 'Inter', system-ui, sans-serif;
  background: var(--bg-gradient);
  color: var(--text-primary);
  line-height: 1.6;
  min-height: 100vh;
}
```

**Step 2: 验证渐变效果**

Run: `cd frontend && npm run dev`
Expected: 页面背景显示微妙的灰到淡蓝渐变

**Step 3: 提交**

```bash
git add frontend/src/assets/styles/main.css
git commit -m "feat: add gradient background to body

- Update CSS variables with gradient definitions
- Apply subtle gray-to-blue gradient to body

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 2: 装饰组件 - Hero 区域视觉增强

### Task 4: 创建浮动装饰组件

**Files:**
- Create: `frontend/src/components/decorations/FloatingShapes.vue`

**Step 1: 创建装饰组件目录和文件**

```bash
mkdir -p frontend/src/components/decorations
```

**Step 2: 编写浮动图形组件**

```vue
<template>
  <div class="floating-shapes">
    <div
      v-for="(shape, index) in shapes"
      :key="index"
      class="shape"
      :class="shape.type"
      :style="{
        left: shape.left,
        top: shape.top,
        width: shape.size,
        height: shape.size,
        animationDelay: shape.delay,
        animationDuration: shape.duration,
      }"
    />
  </div>
</template>

<script setup lang="ts">
interface Shape {
  type: 'circle' | 'square'
  left: string
  top: string
  size: string
  delay: string
  duration: string
}

const shapes: Shape[] = [
  { type: 'circle', left: '10%', top: '20%', size: '120px', delay: '0s', duration: '20s' },
  { type: 'square', left: '80%', top: '30%', size: '80px', delay: '5s', duration: '25s' },
  { type: 'circle', left: '70%', top: '70%', size: '100px', delay: '10s', duration: '22s' },
  { type: 'square', left: '15%', top: '80%', size: '60px', delay: '3s', duration: '18s' },
]
</script>

<style scoped>
.floating-shapes {
  position: absolute;
  inset: 0;
  overflow: hidden;
  pointer-events: none;
  z-index: 0;
}

.shape {
  position: absolute;
  opacity: 0.08;
  animation: float ease-in-out infinite;
}

.circle {
  border-radius: 50%;
  background: radial-gradient(circle, rgba(0, 34, 255, 0.3) 0%, rgba(0, 102, 255, 0.1) 100%);
}

.square {
  border-radius: 20%;
  background: linear-gradient(135deg, rgba(0, 34, 255, 0.2) 0%, rgba(102, 0, 255, 0.1) 100%);
  transform: rotate(45deg);
}

@keyframes float {
  0%, 100% {
    transform: translate(0, 0) rotate(0deg);
  }
  33% {
    transform: translate(30px, -30px) rotate(120deg);
  }
  66% {
    transform: translate(-20px, 20px) rotate(240deg);
  }
}
</style>
```

**Step 3: 验证组件可导入**

Run: `cd frontend && npm run dev`
Expected: 无编译错误

**Step 4: 提交**

```bash
git add frontend/src/components/decorations/FloatingShapes.vue
git commit -m "feat: add FloatingShapes decoration component

- 4 abstract shapes with float animation
- Circle and square variants with gradients
- Configurable position and timing

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 5: 创建网格背景组件

**Files:**
- Create: `frontend/src/components/decorations/GridBackground.vue`

**Step 1: 编写网格背景组件**

```vue
<template>
  <div
    class="grid-background"
    :class="{ 'with-gradient': withGradient }"
  />
</template>

<script setup lang="ts">
interface Props {
  withGradient?: boolean
}

withDefaults(defineProps<Props>(), {
  withGradient: true
})
</script>

<style scoped>
.grid-background {
  position: absolute;
  inset: 0;
  pointer-events: none;
  z-index: 0;
  background-image:
    linear-gradient(rgba(255, 255, 255, 0.05) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 50px 50px;
}

.grid-background.with-gradient {
  background-color: transparent;
  background-image:
    linear-gradient(135deg, #0022FF 0%, #0066FF 100%),
    linear-gradient(rgba(255, 255, 255, 0.08) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px);
  background-size:
    100% 100%,
    50px 50px,
    50px 50px;
  background-position:
    0 0,
    0 0,
    0 0;
}
</style>
```

**Step 2: 验证编译**

Run: `cd frontend && npm run dev`
Expected: 无错误

**Step 3: 提交**

```bash
git add frontend/src/components/decorations/GridBackground.vue
git commit -m "feat: add GridBackground decoration component

- Tech-style grid pattern
- Optional gradient background
- Configurable via props

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 6: 升级 Home 页面 Hero 区域

**Files:**
- Modify: `frontend/src/views/Home.vue`

**Step 1: 导入装饰组件**

在 `<script setup>` 部分添加导入:
```typescript
import GridBackground from '@/components/decorations/GridBackground.vue'
import FloatingShapes from '@/components/decorations/FloatingShapes.vue'
```

**Step 2: 重构 Hero 区域 HTML**

替换原有的 Hero 部分 (第 5-9 行):
```vue
<div class="relative mb-32 py-24 -mx-8 px-8 overflow-hidden">
  <GridBackground />
  <FloatingShapes />

  <div class="relative z-10">
    <h1 class="display-title text-7xl md:text-9xl mb-8 text-white animate-fade-in-up"
        style="text-shadow: 0 0 40px rgba(255,255,255,0.3)">
      AI Blog
    </h1>
    <p class="text-2xl text-white/90 max-w-2xl animate-fade-in-up"
       style="animation-delay: 0.2s; animation-fill-mode: backwards">
      探索人工智能、机器学习和技术创新的前沿思想
    </p>
  </div>
</div>
```

**Step 3: 验证视觉效果**

Run: `cd frontend && npm run dev`
Expected:
- Hero 区域显示蓝色渐变背景
- 网格线条可见
- 浮动图形在背景缓慢移动
- 标题和描述有淡入动画

**Step 4: 提交**

```bash
git add frontend/src/views/Home.vue
git commit -m "feat: upgrade Home hero section with decorations

- Add GridBackground and FloatingShapes
- Apply gradient background and animations
- Add text shadow glow effect

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 3: 组件升级 - 文章卡片和导航栏

### Task 7: 升级 PostCard 组件

**Files:**
- Modify: `frontend/src/components/PostCard.vue`

**Step 1: 替换模板结构**

完整替换 `<template>` 部分:
```vue
<template>
  <article
    class="group relative bg-white rounded-2xl p-8 transition-all duration-300
           shadow-card hover:shadow-card-hover hover:-translate-y-2
           border border-gray-100 hover:border-klein-blue/20
           overflow-hidden"
  >
    <!-- 左侧光晕条 -->
    <div
      class="absolute left-0 top-0 bottom-0 w-1 bg-gradient-to-b from-klein-blue to-klein-blue-light
             opacity-0 group-hover:opacity-100 transition-opacity duration-300"
    />

    <!-- 装饰性 SVG 图案 -->
    <svg
      class="absolute top-4 right-4 w-16 h-16 opacity-[0.03] group-hover:opacity-[0.08]
             transition-opacity duration-300"
      viewBox="0 0 100 100"
    >
      <circle cx="20" cy="20" r="15" fill="currentColor" class="text-klein-blue" />
      <rect x="50" y="10" width="30" height="30" fill="currentColor" class="text-klein-blue-light" transform="rotate(45 65 25)" />
      <path d="M 10 60 Q 30 80 50 60 T 90 60" stroke="currentColor" class="text-klein-blue" stroke-width="3" fill="none" />
    </svg>

    <!-- 标签 -->
    <div class="flex gap-3 mb-4 relative z-10">
      <span
        v-for="tag in post.tags"
        :key="tag"
        class="px-3 py-1 text-xs font-semibold uppercase rounded-full
               bg-gradient-to-r from-klein-blue/10 to-klein-blue-light/10
               border border-klein-blue/20 text-klein-blue
               hover:scale-105 transition-transform duration-200"
      >
        {{ tag }}
      </span>
    </div>

    <!-- 标题 -->
    <router-link :to="`/posts/${post.slug}`">
      <h2
        class="text-4xl md:text-5xl font-bold mb-6
               transition-all duration-300
               group-hover:bg-gradient-to-r group-hover:from-klein-blue group-hover:to-klein-blue-light
               group-hover:bg-clip-text group-hover:text-transparent"
      >
        {{ post.title }}
      </h2>
    </router-link>

    <!-- 摘要 -->
    <p class="text-lg text-secondary mb-6 leading-relaxed">{{ post.summary }}</p>

    <!-- 元信息 -->
    <div class="flex gap-6 text-sm text-tertiary">
      <span>{{ formatDate(post.createdAt) }}</span>
      <span>{{ post.views }} 次阅读</span>
    </div>
  </article>
</template>
```

**Step 2: 保持 script 部分不变**

Script 部分保持原样，无需修改。

**Step 3: 验证视觉效果**

Run: `cd frontend && npm run dev`
Expected:
- 卡片有白色背景和圆角
- 悬浮时上移并显示左侧蓝色光晕条
- 标题悬浮时显示渐变色
- 标签有渐变背景和边框
- 右上角显示 SVG 装饰图案

**Step 4: 提交**

```bash
git add frontend/src/components/PostCard.vue
git commit -m "feat: redesign PostCard with modern styling

- Add card shadow and hover lift effect
- Add left glow bar on hover
- Add SVG decorative pattern
- Style tags with gradient background
- Add title gradient on hover

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 8: 升级 Navbar 组件

**Files:**
- Modify: `frontend/src/components/Navbar.vue`

**Step 1: 更新导航栏样式**

替换 `<template>` 部分:
```vue
<template>
  <nav class="fixed top-0 left-0 right-0 bg-white/90 backdrop-blur-md border-b border-klein-blue/10 z-50
              shadow-sm">
    <div class="container flex items-center justify-between h-20">
      <!-- Logo with gradient -->
      <router-link
        to="/"
        class="font-bold text-xl bg-gradient-to-r from-klein-blue to-klein-blue-light
               bg-clip-text text-transparent hover:opacity-80 transition-opacity"
      >
        AI BLOG
      </router-link>

      <!-- Navigation Links -->
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
```

**Step 2: 更新 scoped 样式**

替换 `<style scoped>` 部分:
```css
.nav-link {
  font-size: 0.875rem;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  position: relative;
  padding-bottom: 4px;
  transition: color 0.3s ease;
}

.nav-link::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background: linear-gradient(90deg, #0022FF 0%, #0066FF 100%);
  transition: width 0.3s ease;
}

.nav-link:hover {
  color: #0022FF;
}

.nav-link:hover::after {
  width: 100%;
}
```

**Step 3: 验证效果**

Run: `cd frontend && npm run dev`
Expected:
- 导航栏背景有毛玻璃效果
- Logo 显示蓝色渐变
- 导航链接下划线从左到右滑入
- 边框颜色为淡蓝色

**Step 4: 提交**

```bash
git add frontend/src/components/Navbar.vue
git commit -m "feat: enhance Navbar with glassmorphism and gradient

- Add backdrop-blur for glassmorphism effect
- Apply gradient to logo text
- Update link underline to gradient
- Adjust border color to klein-blue

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 4: 交互增强 - 滚动动画

### Task 9: 创建滚动动画 Composable

**Files:**
- Create: `frontend/src/composables/useScrollAnimation.ts`

**Step 1: 创建 composables 目录和文件**

```bash
mkdir -p frontend/src/composables
```

**Step 2: 编写滚动动画 hook**

```typescript
import { onMounted, onUnmounted } from 'vue'

export function useScrollAnimation(selector: string, animationClass = 'fade-in-up-stagger') {
  let observer: IntersectionObserver | null = null

  const setupObserver = () => {
    observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry, index) => {
          if (entry.isIntersecting) {
            setTimeout(() => {
              entry.target.classList.add('visible')
              entry.target.classList.add(animationClass)
            }, index * 100) // stagger effect

            observer?.unobserve(entry.target)
          }
        })
      },
      {
        threshold: 0.1,
        rootMargin: '0px 0px -100px 0px',
      }
    )

    const elements = document.querySelectorAll(selector)
    elements.forEach((el) => observer?.observe(el))
  }

  onMounted(() => {
    setupObserver()
  })

  onUnmounted(() => {
    observer?.disconnect()
  })
}
```

**Step 3: 验证 TypeScript 编译**

Run: `cd frontend && npm run dev`
Expected: 无 TypeScript 错误

**Step 4: 提交**

```bash
git add frontend/src/composables/useScrollAnimation.ts
git commit -m "feat: add useScrollAnimation composable

- IntersectionObserver-based scroll animation
- Stagger effect for multiple elements
- Configurable animation class

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 10: 应用滚动动画到 Home 页面

**Files:**
- Modify: `frontend/src/views/Home.vue`

**Step 1: 导入 composable**

在 `<script setup>` 部分添加:
```typescript
import { useScrollAnimation } from '@/composables/useScrollAnimation'
```

**Step 2: 在 onMounted 后调用**

在 `onMounted` 之后添加:
```typescript
useScrollAnimation('.post-card-animated')
```

**Step 3: 为文章卡片添加动画类**

更新 PostCard 组件调用 (第 20-24 行):
```vue
<div class="grid gap-24">
  <div
    v-for="post in posts"
    :key="post.id"
    class="post-card-animated fade-in-up-stagger"
  >
    <PostCard :post="post" />
  </div>
</div>
```

**Step 4: 验证滚动动画**

Run: `cd frontend && npm run dev`
Expected:
- 页面加载时文章卡片隐藏
- 滚动到卡片时依次淡入
- 每个卡片间隔 100ms 出现

**Step 5: 提交**

```bash
git add frontend/src/views/Home.vue
git commit -m "feat: add scroll animation to Home page post cards

- Apply fade-in-up animation on scroll
- Stagger effect for sequential appearance
- Use IntersectionObserver for performance

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 5: 文章详情页增强

### Task 11: 升级 PostDetail 页面样式

**Files:**
- Modify: `frontend/src/views/PostDetail.vue`
- Modify: `frontend/src/assets/styles/main.css`

**Step 1: 更新 PostDetail 模板**

替换 `<template>` 部分:
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
        <!-- 标签 -->
        <div class="flex gap-3 mb-8 animate-fade-in-up">
          <span
            v-for="tag in post?.tags"
            :key="tag"
            class="px-3 py-1 text-xs font-semibold uppercase rounded-full
                   bg-gradient-to-r from-klein-blue/10 to-klein-blue-light/10
                   border border-klein-blue/20 text-klein-blue"
          >
            {{ tag }}
          </span>
        </div>

        <!-- 标题 -->
        <h1
          class="display-title text-5xl md:text-6xl mb-8 animate-fade-in-up"
          style="animation-delay: 0.1s; animation-fill-mode: backwards"
        >
          {{ post?.title }}
        </h1>

        <!-- 元信息 -->
        <div
          class="flex gap-6 text-sm text-tertiary mb-16 animate-fade-in-up"
          style="animation-delay: 0.2s; animation-fill-mode: backwards"
        >
          <span>{{ formatDate(post?.createdAt || '') }}</span>
          <span>{{ post?.views }} 次阅读</span>
        </div>

        <!-- 正文 -->
        <div
          class="prose animate-fade-in-up"
          v-html="renderedContent"
          style="animation-delay: 0.3s; animation-fill-mode: backwards"
        />
      </article>

      <!-- 评论区 -->
      <CommentSection
        v-if="post"
        :post-id="post.id"
        class="max-w-4xl mx-auto mt-32 animate-fade-in-up"
        style="animation-delay: 0.4s; animation-fill-mode: backwards"
      />
    </main>
  </div>
</template>
```

**Step 2: 增强 prose 样式**

在 `frontend/src/assets/styles/main.css` 的 `.prose` 部分更新:
```css
.prose {
  font-size: 1.125rem;
  line-height: 1.8;
  max-width: 720px;
}

.prose h2 {
  font-size: 2rem;
  font-weight: 700;
  margin-top: 3rem;
  margin-bottom: 1.5rem;
  background: linear-gradient(135deg, #0022FF 0%, #0066FF 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.prose h3 {
  font-size: 1.5rem;
  font-weight: 700;
  margin-top: 2rem;
  margin-bottom: 1rem;
  color: var(--text-primary);
}

.prose p {
  margin-bottom: 1.5rem;
}

.prose a {
  color: var(--accent);
  text-decoration: underline;
  text-underline-offset: 3px;
  transition: color 0.2s ease;
}

.prose a:hover {
  color: var(--accent-hover);
}

.prose code {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  background: var(--bg-secondary);
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.9em;
  color: var(--accent);
  font-weight: 500;
}

.prose pre {
  background: var(--text-primary);
  color: var(--bg-primary);
  padding: 1.5rem;
  border-radius: 12px;
  overflow-x: auto;
  margin-bottom: 1.5rem;
  box-shadow: 0 8px 24px rgba(0, 34, 255, 0.1);
}

.prose pre code {
  background: none;
  padding: 0;
  color: inherit;
  font-weight: 400;
}

.prose blockquote {
  border-left: 4px solid;
  border-image: linear-gradient(180deg, #0022FF, #0066FF) 1;
  background: rgba(0, 34, 255, 0.02);
  padding: 1rem 1.5rem;
  margin: 2rem 0;
  border-radius: 0 8px 8px 0;
  font-style: italic;
}

.prose img {
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 34, 255, 0.08);
  margin: 2rem 0;
}

.prose ul, .prose ol {
  padding-left: 1.5rem;
  margin-bottom: 1.5rem;
}

.prose li {
  margin-bottom: 0.5rem;
}
```

**Step 3: 验证样式效果**

Run: `cd frontend && npm run dev`
访问任意文章详情页
Expected:
- 标题、标签依次淡入
- H2 标题显示蓝色渐变
- 代码块有圆角和阴影
- 引用块有渐变左边框
- 整体阅读体验更精致

**Step 4: 提交**

```bash
git add frontend/src/views/PostDetail.vue frontend/src/assets/styles/main.css
git commit -m "feat: enhance PostDetail with animations and improved prose styles

- Add staggered fade-in animations to sections
- Apply gradient to H2 headings
- Enhance code block and blockquote styling
- Add box shadow to images and code blocks

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 6: 响应式优化

### Task 12: 移动端适配

**Files:**
- Modify: `frontend/src/views/Home.vue`
- Modify: `frontend/src/components/PostCard.vue`
- Modify: `frontend/src/components/Navbar.vue`

**Step 1: 优化 Home 页面移动端**

更新 Hero 区域样式 (在 Home.vue):
```vue
<div class="relative mb-16 md:mb-32 py-16 md:py-24 -mx-4 md:-mx-8 px-4 md:px-8 overflow-hidden">
  <GridBackground />
  <FloatingShapes class="hidden md:block" />

  <div class="relative z-10">
    <h1 class="display-title text-5xl md:text-7xl lg:text-9xl mb-6 md:mb-8 text-white animate-fade-in-up"
        style="text-shadow: 0 0 40px rgba(255,255,255,0.3)">
      AI Blog
    </h1>
    <p class="text-lg md:text-2xl text-white/90 max-w-2xl animate-fade-in-up"
       style="animation-delay: 0.2s; animation-fill-mode: backwards">
      探索人工智能、机器学习和技术创新的前沿思想
    </p>
  </div>
</div>
```

更新文章列表间距:
```vue
<div class="grid gap-12 md:gap-24">
```

**Step 2: 优化 PostCard 移动端**

更新卡片 padding 和字体大小:
```vue
<article
  class="group relative bg-white rounded-2xl p-6 md:p-8 transition-all duration-300
         shadow-card hover:shadow-card-hover hover:-translate-y-2
         border border-gray-100 hover:border-klein-blue/20
         overflow-hidden"
>
  <!-- SVG 图案在移动端缩小 -->
  <svg
    class="absolute top-4 right-4 w-12 h-12 md:w-16 md:h-16 opacity-[0.03] group-hover:opacity-[0.08]
           transition-opacity duration-300"
    viewBox="0 0 100 100"
  >
    <!-- ... -->
  </svg>

  <!-- 标题字号响应式 -->
  <h2
    class="text-3xl md:text-4xl lg:text-5xl font-bold mb-4 md:mb-6
           transition-all duration-300
           group-hover:bg-gradient-to-r group-hover:from-klein-blue group-hover:to-klein-blue-light
           group-hover:bg-clip-text group-hover:text-transparent"
  >
    {{ post.title }}
  </h2>

  <!-- 摘要 -->
  <p class="text-base md:text-lg text-secondary mb-4 md:mb-6 leading-relaxed">{{ post.summary }}</p>
```

**Step 3: 验证移动端效果**

Run: `cd frontend && npm run dev`
打开浏览器开发者工具,切换到移动端视图
Expected:
- Hero 标题在移动端缩小
- 浮动图形在移动端隐藏
- 卡片间距和 padding 在移动端缩小
- 所有文字大小适配移动端

**Step 4: 提交**

```bash
git add frontend/src/views/Home.vue frontend/src/components/PostCard.vue
git commit -m "feat: add responsive design for mobile devices

- Adjust Hero section spacing and font sizes
- Hide FloatingShapes on mobile for performance
- Optimize PostCard padding and typography
- Reduce grid gap on smaller screens

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 7: 性能优化与可访问性

### Task 13: 添加性能优化 CSS

**Files:**
- Modify: `frontend/src/assets/styles/main.css`

**Step 1: 添加性能优化规则**

在 `main.css` 末尾添加:
```css
/* 性能优化 */
.hover-lift,
.shape,
.floating-shapes {
  will-change: transform;
}

/* 减少不必要的重绘 */
.post-card,
.nav-link {
  contain: layout style paint;
}

/* GPU 加速 */
.animate-fade-in-up,
.animate-float {
  transform: translateZ(0);
  backface-visibility: hidden;
}
```

**Step 2: 验证无性能回退**

Run: `cd frontend && npm run dev`
打开 Chrome DevTools > Performance
录制页面加载和交互
Expected: 无明显掉帧,动画保持 60fps

**Step 3: 提交**

```bash
git add frontend/src/assets/styles/main.css
git commit -m "feat: add CSS performance optimizations

- Add will-change for animated elements
- Use contain for layout isolation
- Enable GPU acceleration for animations

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

### Task 14: 添加可访问性增强

**Files:**
- Modify: `frontend/src/components/decorations/FloatingShapes.vue`
- Modify: `frontend/src/components/PostCard.vue`

**Step 1: 添加 ARIA 属性到装饰元素**

在 FloatingShapes.vue 中:
```vue
<div class="floating-shapes" aria-hidden="true" role="presentation">
```

**Step 2: 为 PostCard 添加更好的语义**

在 PostCard.vue 的 article 标签添加:
```vue
<article
  class="..."
  role="article"
  :aria-labelledby="`post-title-${post.id}`"
>
  <!-- ... -->

  <h2
    :id="`post-title-${post.id}`"
    class="..."
  >
    {{ post.title }}
  </h2>
```

**Step 3: 验证可访问性**

Run: `cd frontend && npm run dev`
使用键盘 Tab 键导航
Expected: 所有链接和按钮可键盘访问

**Step 4: 提交**

```bash
git add frontend/src/components/decorations/FloatingShapes.vue frontend/src/components/PostCard.vue
git commit -m "feat: improve accessibility with ARIA attributes

- Add aria-hidden to decorative elements
- Add proper article labeling
- Improve semantic HTML structure

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 8: 测试与验证

### Task 15: 手动测试清单

**Step 1: 功能测试**

执行以下测试:
- [ ] 首页加载正常,Hero 区域显示渐变和装饰
- [ ] 文章卡片悬浮效果正常
- [ ] 滚动时文章卡片依次淡入
- [ ] 导航栏链接下划线动画正常
- [ ] 文章详情页样式正常,代码块和引用块正确渲染
- [ ] 移动端适配良好

**Step 2: 性能测试**

在 Chrome DevTools 中:
```bash
# 打开 Lighthouse
# 运行 Performance 测试
```

Expected:
- Performance Score > 90
- First Contentful Paint < 1.5s
- Cumulative Layout Shift < 0.1

**Step 3: 可访问性测试**

```bash
# 使用键盘 Tab 导航所有链接
# 检查颜色对比度
# 测试 prefers-reduced-motion
```

Expected:
- 所有交互元素可键盘访问
- 对比度符合 WCAG AA
- 动画在 reduced-motion 模式下禁用

**Step 4: 创建测试报告**

创建文件 `docs/testing/2026-02-01-ui-testing-report.md`:
```markdown
# UI 升级测试报告

## 测试日期
2026-02-01

## 功能测试
- [x] Hero 区域渐变和装饰
- [x] 文章卡片悬浮效果
- [x] 滚动动画
- [x] 导航栏交互
- [x] 文章详情页样式
- [x] 移动端适配

## 性能测试
- Performance Score: [填写分数]
- FCP: [填写时间]
- CLS: [填写数值]

## 可访问性测试
- [x] 键盘导航
- [x] 颜色对比度
- [x] Reduced motion 支持

## 问题
[列出发现的问题]

## 结论
[通过/需要修复]
```

**Step 5: 提交测试报告**

```bash
mkdir -p docs/testing
git add docs/testing/2026-02-01-ui-testing-report.md
git commit -m "docs: add UI testing report

- Document manual testing results
- Record performance metrics
- List accessibility checks

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 9: 文档更新

### Task 16: 更新 README 和 CLAUDE.md

**Files:**
- Modify: `CLAUDE.md`

**Step 1: 在 CLAUDE.md 中记录 UI 升级**

在 "## 项目特色" 部分更新:
```markdown
## 项目特色

🎨 **现代科技感设计**
- 渐变背景和光晕效果
- 动态装饰元素 (浮动图形、网格背景)
- 精致的悬浮和滚动动画
- 克莱因蓝主题色贯穿设计

🚀 **一键启动**
- 零配置，运行 `./start.sh` 即可
- 自动初始化数据库
- 自动创建管理员账号

⚡ **现代技术**
- Vue 3 Composition API
- Spring Boot 3.5
- TypeScript 全覆盖
- JWT 无状态认证
- Tailwind CSS + 自定义动画
```

**Step 2: 添加 UI 组件文档**

在 "## 关键路径" 后添加新部分:
```markdown
### UI 组件
- **装饰组件**: `frontend/src/components/decorations/`
  - `FloatingShapes.vue`: 浮动抽象图形
  - `GridBackground.vue`: 网格背景
- **动画系统**: `frontend/src/assets/styles/animations.css`
- **Composables**: `frontend/src/composables/useScrollAnimation.ts`
```

**Step 3: 验证文档可读性**

阅读更新后的文档,确保清晰准确。

**Step 4: 提交**

```bash
git add CLAUDE.md
git commit -m "docs: update CLAUDE.md with UI redesign information

- Document new design features
- Add UI component locations
- Update project highlights

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## Phase 10: 最终验收

### Task 17: 完整验收测试

**Step 1: 启动完整服务**

```bash
cd /home/koqizhao/Projects/mydotey-ai/ai-blog/.worktrees/feature/modern-ui
./start.sh
```

Expected: 前端和后端都成功启动

**Step 2: 验收清单**

访问 http://localhost:3000 并检查:

**视觉效果**
- [ ] 页面整体具有科技感和层次感
- [ ] 克莱因蓝应用优雅自然
- [ ] 光晕和渐变效果不突兀
- [ ] 装饰元素增强视觉但不喧宾夺主

**交互体验**
- [ ] 所有动画流畅 (60fps)
- [ ] 悬浮效果响应迅速
- [ ] 滚动体验顺滑
- [ ] 移动端触摸友好

**技术指标**
- [ ] Lighthouse Performance > 85
- [ ] 首屏渲染 < 2s
- [ ] 无控制台错误
- [ ] 无布局抖动

**可访问性**
- [ ] 键盘导航完整
- [ ] 对比度符合标准
- [ ] 支持 reduced-motion

**Step 3: 停止服务**

```bash
./stop.sh
```

**Step 4: 创建验收报告**

如所有检查通过,创建验收报告并提交:

```bash
git add -A
git commit -m "chore: complete UI redesign implementation

All acceptance criteria met:
- Modern tech aesthetic with gradients and glows
- Smooth animations and interactions
- Mobile responsive
- Accessible and performant

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

---

## 实施后步骤

完成所有任务后,需要:

1. **代码审查**: 使用 @superpowers:requesting-code-review
2. **合并到主分支**: 使用 @superpowers:finishing-a-development-branch
3. **部署**: 根据部署流程推送到生产环境

---

## 故障排查

### 常见问题

**动画不显示**
- 检查 Tailwind 配置是否正确加载
- 验证 animations.css 是否导入
- 检查浏览器控制台是否有 CSS 错误

**卡片悬浮效果失效**
- 检查 PostCard.vue 的 class 是否正确
- 验证 Tailwind 的 group-hover 是否生效
- 确认没有 CSS 冲突

**移动端布局错乱**
- 检查响应式 class (md:, lg:) 是否正确
- 验证容器宽度和 padding
- 使用开发者工具调试

**性能问题**
- 减少同时运行的动画数量
- 检查是否有过多的 box-shadow
- 使用 Chrome Performance 工具定位瓶颈

---

**计划版本**: 1.0
**创建日期**: 2026-02-01
**预计工时**: 4-6 小时
**难度**: 中等
**依赖**: Vue 3, Tailwind CSS, TypeScript
