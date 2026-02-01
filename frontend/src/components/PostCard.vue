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
