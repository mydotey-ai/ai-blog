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
import { ref, onMounted, computed } from 'vue'
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
