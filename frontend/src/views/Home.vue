<template>
  <div class="home">
    <!-- Navigation -->
    <nav class="navigation">
      <div class="container navigation-inner">
        <router-link to="/" class="logo">AI.BLOG</router-link>
        <div class="nav-links">
          <router-link to="/tags" class="nav-link">Tags</router-link>
          <router-link to="/about" class="nav-link">About</router-link>
          <a href="/admin" class="nav-link">Admin</a>
        </div>
      </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero">
      <div class="container">
        <h1 class="hero-title">
          <span class="line"><span style="--delay: 0s">Exploring</span></span>
          <span class="line"><span style="--delay: 0.1s">Artificial</span></span>
          <span class="line"><span style="--delay: 0.2s">Intelligence</span></span>
        </h1>
        <p class="hero-subtitle">
          Thoughts, tutorials, and explorations in machine learning,
          deep learning, and the future of AI.
        </p>
      </div>
    </section>

    <!-- Featured Articles -->
    <section class="featured pt-64 pb-48">
      <div class="container">
        <div class="section-header" style="margin-bottom: var(--space-32);">
          <p class="article-tag">Featured</p>
        </div>

        <article class="article-card">
          <div class="article-card-header">
            <span class="article-tag">Machine Learning</span>
            <span class="article-meta">Feb 01, 2026</span>
          </div>
          <h2 class="article-title">Building Transformers from Scratch</h2>
          <p class="article-excerpt">
            A deep dive into the architecture that revolutionized NLP.
            Implement attention mechanisms and understand why they work.
          </p>
        </article>

        <article class="article-card">
          <div class="article-card-header">
            <span class="article-tag">Deep Learning</span>
            <span class="article-meta">Jan 28, 2026</span>
          </div>
          <h2 class="article-title">Understanding Diffusion Models</h2>
          <p class="article-excerpt">
            From DDPM to Stable Diffusion: learn the mathematics behind
            the generative models powering the AI art revolution.
          </p>
        </article>

        <article class="article-card" style="border-bottom: none;">
          <div class="article-card-header">
            <span class="article-tag">LLM</span>
            <span class="article-meta">Jan 25, 2026</span>
          </div>
          <h2 class="article-title">Fine-tuning Large Language Models</h2>
          <p class="article-excerpt">
            Practical guide to adapting pre-trained models for your
            specific use case with LoRA and QLoRA techniques.
          </p>
        </article>
      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <div class="container footer-content">
        <span class="footer-copy">© 2026 AI.BLOG</span>
        <span class="footer-copy">Built with Vue 3 + Spring Boot</span>
      </div>
    </footer>
  </div>
</template>

<script setup>
// Component logic
</script>

<style scoped>
.home {
  min-height: 100vh;
}

.section-header {
  display: flex;
  align-items: center;
  gap: var(--space-6);
}
</style>
