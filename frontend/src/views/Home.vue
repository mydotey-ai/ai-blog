<template>
  <div class="relative">
    <GridBackground :with-gradient="true" />
    <FloatingShapes class="hidden md:block" />

    <Navbar />

    <main class="container py-24 relative z-10">
      <div class="mb-16 md:mb-32 relative">
        <h1 class="display-title text-5xl md:text-7xl lg:text-9xl mb-6 md:mb-8 bg-gradient-to-r from-klein-blue via-purple-600 to-klein-blue bg-clip-text text-transparent animate-gradient" style="text-shadow: 0 0 40px rgba(0, 34, 255, 0.3);">
          AI Blog
        </h1>
        <p class="text-lg md:text-2xl text-secondary max-w-2xl">探索人工智能、机器学习和技术创新的前沿思想</p>
      </div>

      <div v-if="loading" class="text-center py-12">
        <p class="text-secondary">加载中...</p>
      </div>

      <div v-else-if="error" class="text-center py-12">
        <p class="text-red-500">{{ error }}</p>
      </div>

      <div v-else class="grid gap-12 md:gap-24">
        <div
          v-for="post in posts"
          :key="post.id"
          class="post-card-animated fade-in-up-stagger"
        >
          <PostCard :post="post" />
        </div>
      </div>

      <div v-if="hasMore" class="text-center mt-24">
        <button @click="loadMore" class="btn-primary">加载更多</button>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getPosts, type Post } from '@/services/api'
import Navbar from '@/components/Navbar.vue'
import PostCard from '@/components/PostCard.vue'
import GridBackground from '@/components/decorations/GridBackground.vue'
import FloatingShapes from '@/components/decorations/FloatingShapes.vue'
import { useScrollAnimation } from '@/composables/useScrollAnimation'

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

useScrollAnimation('.post-card-animated')
</script>
