<template>
  <article class="group relative bg-white rounded-2xl p-8 shadow-sm hover:shadow-2xl transition-all duration-500 hover:-translate-y-2 border border-gray-100 hover:border-klein-blue/20 overflow-hidden">
    <!-- Left glow bar -->
    <div class="absolute left-0 top-0 bottom-0 w-1 bg-gradient-to-b from-klein-blue to-purple-600 opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>

    <!-- Decorative pattern -->
    <svg class="absolute top-4 right-4 w-24 h-24 opacity-0 group-hover:opacity-10 transition-opacity duration-500" viewBox="0 0 100 100">
      <circle cx="20" cy="20" r="15" fill="currentColor" class="text-klein-blue" />
      <rect x="60" y="10" width="30" height="30" fill="currentColor" class="text-purple-500" transform="rotate(45 75 25)" />
      <circle cx="70" cy="70" r="20" fill="currentColor" class="text-blue-400" />
    </svg>

    <div class="flex gap-3 mb-4 flex-wrap">
      <span
        v-for="tag in post.tags"
        :key="tag"
        class="px-3 py-1 text-xs font-semibold uppercase bg-gradient-to-r from-klein-blue/10 to-purple-500/10 text-klein-blue rounded-full"
      >
        {{ tag }}
      </span>
    </div>

    <router-link :to="`/posts/${post.slug}`" class="block">
      <h2 class="text-4xl md:text-5xl font-bold mb-6 transition-all duration-300 group-hover:bg-gradient-to-r group-hover:from-klein-blue group-hover:via-purple-600 group-hover:to-klein-blue group-hover:bg-clip-text group-hover:text-transparent">
        {{ post.title }}
      </h2>
    </router-link>

    <p class="text-lg text-secondary mb-6 line-clamp-3">{{ post.summary }}</p>

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
