<template>
  <div class="article-detail">
    <!-- Navigation -->
    <nav class="navigation">
      <div class="container navigation-inner">
        <router-link to="/" class="logo">AI.BLOG</router-link>
        <div class="nav-links">
          <router-link to="/tags" class="nav-link">Tags</router-link>
          <router-link to="/about" class="nav-link">About</router-link>
        </div>
      </div>
    </nav>

    <!-- Article Header -->
    <header class="article-header pt-64 pb-32">
      <div class="container container-md">
        <div class="article-meta-header">
          <span class="article-tag">Machine Learning</span>
          <span class="article-meta">Feb 01, 2026 · 12 min read</span>
        </div>
        <h1 class="article-title-large">
          Building Transformers from Scratch
        </h1>
        <p class="article-lead">
          A deep dive into the architecture that revolutionized NLP.
          Implement attention mechanisms and understand why they work.
        </p>
      </div>
    </header>

    <!-- Article Content -->
    <article class="article-content">
      <div class="container container-md">
        <div class="prose">
          <p>
            The Transformer architecture, introduced in the seminal paper
            "Attention Is All You Need," has fundamentally changed how we
            approach sequence modeling tasks. Unlike RNNs and LSTMs,
            Transformers process entire sequences in parallel through
            self-attention mechanisms.
          </p>

          <h2>The Self-Attention Mechanism</h2>

          <p>
            At the heart of the Transformer lies the self-attention mechanism.
            Given a sequence of tokens, each token attends to all other tokens,
            computing weighted representations that capture contextual relationships.
          </p>

          <p>
            Mathematically, self-attention is computed as:
          </p>

          <pre class="code-block">Attention(Q, K, V) = softmax(QK^T / √d_k)V</pre>

          <p>
            Where Q, K, and V are the Query, Key, and Value matrices respectively,
            and d_k is the dimensionality of the keys. The scaling factor √d_k
            prevents the dot products from growing too large, which would push
            the softmax into regions with extremely small gradients.
          </p>

          <h2>Multi-Head Attention</h2>

          <p>
            Rather than performing a single attention function, multi-head
            attention runs multiple attention operations in parallel, allowing
            the model to capture different types of relationships at different
            positions.
          </p>

          <pre class="code-block">MultiHead(Q, K, V) = Concat(head_1, ..., head_h)W^O
where head_i = Attention(QW_i^Q, KW_i^K, VW_i^V)</pre>

          <p>
            Each head learns different aspects of the input. Some heads might
            focus on syntactic relationships, while others capture semantic
            connections or long-range dependencies.
          </p>

          <h2>Position Encoding</h2>

          <p>
            Since self-attention doesn't inherently capture sequence order,
            Transformers inject positional information using sinusoidal
            position encodings:
          </p>

          <pre class="code-block">PE_(pos, 2i)   = sin(pos / 10000^(2i/d_model))
PE_(pos, 2i+1) = cos(pos / 10000^(2i/d_model))</pre>

          <p>
            These encodings allow the model to learn relative positions and
            generalize to sequence lengths not seen during training.
          </p>

          <h2>Implementation</h2>

          <p>
            Let's implement a simplified version of the self-attention mechanism
            in PyTorch:
          </p>

          <pre class="code-block">class SelfAttention(nn.Module):
    def __init__(self, embed_dim, num_heads):
        super().__init__()
        self.embed_dim = embed_dim
        self.num_heads = num_heads
        self.head_dim = embed_dim // num_heads

        self.qkv = nn.Linear(embed_dim, 3 * embed_dim)
        self.proj = nn.Linear(embed_dim, embed_dim)

    def forward(self, x):
        B, N, C = x.shape
        qkv = self.qkv(x).reshape(B, N, 3, self.num_heads, self.head_dim)
        qkv = qkv.permute(2, 0, 3, 1, 4)
        q, k, v = qkv[0], qkv[1], qkv[2]

        attn = (q @ k.transpose(-2, -1)) * (self.head_dim ** -0.5)
        attn = attn.softmax(dim=-1)

        x = (attn @ v).transpose(1, 2).reshape(B, N, C)
        return self.proj(x)</pre>

          <h2>Conclusion</h2>

          <p>
            The Transformer architecture has proven remarkably effective across
            a wide range of tasks. Its parallelizable nature and ability to
            capture long-range dependencies have made it the foundation for
            modern large language models.
          </p>

          <p>
            Understanding these building blocks is essential for anyone working
            with contemporary NLP systems. Whether you're fine-tuning existing
            models or architecting new ones, these principles remain the same.
          </p>
        </div>
      </div>
    </article>

    <!-- Article Footer -->
    <footer class="article-footer pt-48 pb-32">
      <div class="container container-md">
        <div class="article-tags">
          <span class="article-tag">Machine Learning</span>
          <span class="article-tag">Deep Learning</span>
          <span class="article-tag">NLP</span>
        </div>
      </div>
    </footer>

    <!-- Comments Section -->
    <section class="comments-section pt-32 pb-64" style="border-top: 1px solid var(--color-border-light);">
      <div class="container container-md">
        <h3 class="comments-title">Comments</h3>

        <!-- Comment Form -->
        <div class="comment-form">
          <input
            type="text"
            placeholder="Name (optional)"
            class="comment-input"
          />
          <input
            type="email"
            placeholder="Email (optional)"
            class="comment-input"
          />
          <textarea
            placeholder="Write a comment..."
            class="comment-textarea"
            rows="4"
          ></textarea>
          <button class="btn btn-primary">Submit</button>
        </div>

        <!-- Comments List -->
        <div class="comments-list">
          <div class="comment">
            <div class="comment-header">
              <span class="comment-author">Alex Chen</span>
              <span class="comment-date">2 hours ago</span>
            </div>
            <p class="comment-content">
              Great explanation of the self-attention mechanism! The code
              example really helped clarify how multi-head attention works
              in practice.
            </p>
            <button class="comment-reply">Reply</button>
          </div>

          <div class="comment">
            <div class="comment-header">
              <span class="comment-author">Sarah Kim</span>
              <span class="comment-date">5 hours ago</span>
            </div>
            <p class="comment-content">
              Would love to see a follow-up article covering efficient
              attention implementations like Flash Attention!
            </p>
            <button class="comment-reply">Reply</button>
          </div>
        </div>
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
.article-header {
  border-bottom: 1px solid var(--color-border-light);
}

.article-meta-header {
  display: flex;
  align-items: center;
  gap: var(--space-4);
  margin-bottom: var(--space-8);
}

.article-title-large {
  font-family: var(--font-display);
  font-size: var(--text-6xl);
  font-weight: var(--font-bold);
  line-height: 1.05;
  letter-spacing: -0.04em;
  color: var(--color-text);
  margin-bottom: var(--space-8);
}

.article-lead {
  font-family: var(--font-body);
  font-size: var(--text-2xl);
  line-height: 1.6;
  color: var(--color-text-secondary);
}

.article-content {
  padding-top: var(--space-32);
}

.prose {
  font-family: var(--font-body);
  font-size: var(--text-lg);
  line-height: 1.8;
  color: var(--color-text-secondary);
}

.prose h2 {
  font-family: var(--font-display);
  font-size: var(--text-3xl);
  font-weight: var(--font-bold);
  letter-spacing: -0.02em;
  color: var(--color-text);
  margin-top: var(--space-16);
  margin-bottom: var(--space-6);
}

.prose p {
  margin-bottom: var(--space-6);
}

.prose pre {
  font-family: var(--font-mono);
  font-size: var(--text-sm);
  line-height: 1.6;
  background: var(--color-bg-secondary);
  padding: var(--space-6);
  border-radius: var(--radius-md);
  margin: var(--space-8) 0;
  overflow-x: auto;
}

.code-block {
  font-family: var(--font-mono);
  font-size: var(--text-sm);
  line-height: 1.6;
  background: var(--color-bg-secondary);
  padding: var(--space-6);
  border-radius: var(--radius-md);
  margin: var(--space-8) 0;
  overflow-x: auto;
}

.article-footer .article-tags {
  display: flex;
  gap: var(--space-3);
}

.comments-title {
  font-family: var(--font-display);
  font-size: var(--text-3xl);
  font-weight: var(--font-bold);
  letter-spacing: -0.02em;
  color: var(--color-text);
  margin-bottom: var(--space-12);
}

.comment-form {
  display: flex;
  flex-direction: column;
  gap: var(--space-4);
  margin-bottom: var(--space-16);
}

.comment-input,
.comment-textarea {
  font-family: var(--font-body);
  font-size: var(--text-base);
  padding: var(--space-4);
  border: 1px solid var(--color-border);
  border-radius: var(--radius-md);
  background: var(--color-bg);
  transition: border-color var(--transition-fast);
}

.comment-input:focus,
.comment-textarea:focus {
  border-color: var(--color-accent);
}

.comment-textarea {
  resize: vertical;
  min-height: 120px;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-12);
}

.comment {
  padding-bottom: var(--space-8);
  border-bottom: 1px solid var(--color-border-light);
}

.comment-header {
  display: flex;
  align-items: baseline;
  gap: var(--space-4);
  margin-bottom: var(--space-3);
}

.comment-author {
  font-family: var(--font-body);
  font-size: var(--text-sm);
  font-weight: var(--font-semibold);
  color: var(--color-text);
}

.comment-date {
  font-family: var(--font-mono);
  font-size: var(--text-xs);
  color: var(--color-text-tertiary);
}

.comment-content {
  font-family: var(--font-body);
  font-size: var(--text-base);
  line-height: 1.7;
  color: var(--color-text-secondary);
  margin-bottom: var(--space-3);
}

.comment-reply {
  font-family: var(--font-body);
  font-size: var(--text-xs);
  font-weight: var(--font-semibold);
  letter-spacing: 0.05em;
  text-transform: uppercase;
  color: var(--color-text-tertiary);
  transition: color var(--transition-fast);
}

.comment-reply:hover {
  color: var(--color-accent);
}
</style>
