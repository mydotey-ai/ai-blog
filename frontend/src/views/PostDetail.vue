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
  return marked.parse(post.value.content, { async: false }) as string
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
