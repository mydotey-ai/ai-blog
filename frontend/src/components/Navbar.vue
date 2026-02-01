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
