# AI Blog - UI Design System

**设计方向**: 白色极简主义 + 克莱因蓝
**创建日期**: 2026-02-01

---

## 视觉风格定位

### 核心特征
- **极简主义**: 超大留白，精致排版，去除一切不必要的装饰
- **粗体几何**: 使用 Monument Extended 等粗体几何字体创造视觉冲击
- **单色重点**: 纯白背景 + 深灰文字 + 克莱因蓝 (#0022FF) 强调色

### 设计原则
1. **留白即设计**: 大量使用 padding 和 margin 创造呼吸感
2. **排版即结构**: 通过字体大小和粗细构建层次，而非边框和分割线
3. **克制使用颜色**: 90% 灰度，10% 克莱因蓝作为交互状态
4. **动微交互**: 精致的 hover 效果和页面过渡动画

---

## 色彩系统

### 主色调
```
Background:     #FAFAFA  (接近纯白，但更柔和)
Background Alt: #F5F5F5  (次级背景)
Text Primary:   #1A1A1A  (接近纯黑)
Text Secondary: #6B6B6B  (中灰)
Text Tertiary:  #9E9E9E  (浅灰)
Border:         #E8E8E8  (极淡灰)
```

### 强调色
```
Accent:         #0022FF  (克莱因蓝)
Accent Hover:   #001ACC  (克莱因蓝深色)
Accent Light:   rgba(0, 34, 255, 0.08)  (淡蓝背景)
```

### 语义色（仅在必要时使用）
```
Success:        #00CC66
Warning:        #FF9900
Error:          #FF0033
```

---

## 字体系统

### 字体家族

**Display Font (标题字体)**
```
Primary: Monument Extended (商用需授权)
Fallback: Helvetica Now Display
Fallback: Arial Black
```
- 用于: 页面标题、文章标题、Logo
- 特点: 超粗字重、紧凑字间距、强烈视觉冲击

**Body Font (正文字体)**
```
Primary: Space Grotesk (Google Fonts 免费)
Fallback: Inter
Fallback: system-ui
```
- 用于: 正文、导航、按钮、表单
- 特点: 几何无衬线、现代科技感、易读性强

**Mono Font (代码字体)**
```
Primary: JetBrains Mono
Fallback: SF Mono
Fallback: monospace
```
- 用于: 代码块、日期、元数据

### 字体层级

```css
/* Display - Hero Titles */
--text-7xl: 7rem       /* 主页标题 */
--text-6xl: 5rem       /* 文章详情标题 */
--text-5xl: 3.5rem     /* 区块标题 */

/* Heading */
--text-4xl: 2.5rem     /* H2 */
--text-3xl: 2rem       /* H3 */
--text-2xl: 1.5rem     /* H4 */
--text-xl:  1.25rem    /* H5 */

/* Body */
--text-lg:  1.125rem   /* 引言、摘要 */
--text-base: 1rem      /* 正文 */
--text-sm:  0.875rem   /* 辅助文本 */
--text-xs:  0.75rem    /* 标签、元数据 */
```

### 字重
```
Light:    300  (正文引言)
Regular:  400  (正文)
Medium:   500  (强调)
Bold:     700  (小标题)
Black:    900  (Display 标题)
```

### 字间距 (Letter Spacing)
```
Display 标题:    -0.05em 到 -0.02em  (紧密，创造冲击力)
正文:            0em                  (默认)
小字/标签:       0.05em 到 0.1em     (宽松，提升可读性)
```

---

## 间距系统

### 极简主义留白原则
普通设计的 1.5-2 倍间距，创造"呼吸感"

```css
--space-1:  0.25rem   (4px)
--space-2:  0.5rem    (8px)
--space-3:  0.75rem   (12px)
--space-4:  1rem      (16px)
--space-5:  1.25rem   (20px)
--space-6:  1.5rem    (24px)
--space-8:  2rem      (32px)
--space-12: 3rem      (48px)
--space-16: 4rem      (64px)
--space-24: 6rem      (96px)
--space-32: 8rem      (128px)
--space-48: 12rem     (192px)
--space-64: 16rem     (256px)
```

### 应用场景
- 组件内间距: `space-4` 到 `space-8`
- 区块间距: `space-24` 到 `space-32`
- 页面级留白: `space-48` 到 `space-64`
- Hero 上下留白: `space-64`

---

## 组件设计

### 导航栏 (Navigation)
- 固定顶部，白色背景，1px 浅灰底边
- Logo: Monument Extended Bold，20px
- 链接: 14px，Medium，全大写，0.05em 字间距
- Hover: 文字变蓝 + 底部 2px 蓝线动画

### 文章卡片 (Article Card)
- 垂直布局，无背景色
- 分隔线: 1px 浅灰，hover 时变克莱因蓝
- 标签: 12px，SemiBold，全大写，克莱因蓝
- 标题: 40px，Bold，深灰，hover 变蓝
- 摘要: 18px，Regular，中灰

### 按钮 (Button)
- 圆角全圆 (9999px)
- Padding: 16px 32px
- 字体: 14px，SemiBold，全大写
- Primary: 克莱因蓝背景，白色文字
- Hover: 上移 2px + 阴影

### 评论区 (Comments)
- 标题: 32px，Bold，深灰
- 表单: 无边框输入框，1px 浅灰边，focus 变蓝
- 评论: 14px 正文，浅灰元数据，Reply 小字链接

---

## 动画系统

### 过渡时长
```css
Fast:   150ms  (hover 态)
Base:   250ms  (常规过渡)
Slow:   350ms  (页面元素)
Slower: 500ms  (页面切换)
```

### 页面加载动画
Hero 标题逐行上滑显示，每行延迟 0.1s

### Hover 效果
- 链接: 颜色渐变 + 底线展开
- 按钮: 上移 2px + 阴影
- 卡片: 边框颜色变化

---

## 响应式设计

### 断点
```
sm:  640px   (平板竖屏)
md:  768px   (平板横屏)
lg:  1024px  (笔记本)
xl:  1280px  (桌面)
2xl: 1536px  (大屏)
```

### 移动端适配
- Display 标题缩小 40%
- 内边距减少 50%
- 导航栏简化为汉堡菜单

---

## 设计文件位置

### 源文件
```
frontend/src/assets/styles/
├── variables.css    # 设计令牌
├── reset.css        # 重置样式
└── main.css         # 主样式 + 组件
```

### 页面组件
```
frontend/src/views/
├── Home.vue           # 首页
└── ArticleDetail.vue  # 文章详情
```

---

## 实现优先级

1. ✅ Design System CSS (variables, reset, main)
2. ✅ Home Page with Hero animation
3. ✅ Article Detail Page
4. ⏳ Tags Page
5. ⏳ About Page
6. ⏳ Admin Dashboard
