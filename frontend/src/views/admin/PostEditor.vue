<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <router-link to="/admin/posts" class="font-bold text-xl">返回文章列表</router-link>
      </div>
    </nav>

    <main class="container py-12">
      <h2 class="text-4xl font-bold mb-8">{{ isEdit ? '编辑文章' : '新建文章' }}</h2>

      <form @submit.prevent="handleSubmit" class="max-w-4xl">
        <input
          v-model="form.title"
          type="text"
          placeholder="文章标题"
          class="input mb-4"
          required
        />

        <input
          v-model="form.slug"
          type="text"
          placeholder="URL Slug"
          class="input mb-4"
          required
        />

        <input
          v-model="form.summary"
          type="text"
          placeholder="文章摘要"
          class="input mb-4"
        />

        <input
          v-model="form.coverImage"
          type="text"
          placeholder="封面图 URL"
          class="input mb-4"
        />

        <input
          v-model="tagsInput"
          type="text"
          placeholder="标签（逗号分隔）"
          class="input mb-4"
        />

        <select v-model="form.status" class="input mb-4">
          <option value="DRAFT">草稿</option>
          <option value="PUBLISHED">已发布</option>
        </select>

        <textarea
          v-model="form.content"
          placeholder="文章内容（Markdown）"
          class="input textarea mb-4"
          style="min-height: 400px"
          required
        ></textarea>

        <div class="flex gap-4">
          <button type="submit" class="btn-primary" :disabled="submitting">
            {{ submitting ? '保存中...' : '保存' }}
          </button>
          <router-link to="/admin/posts" class="btn-secondary">取消</router-link>
        </div>
      </form>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { adminCreatePost, adminUpdatePost, adminGetPost } from '@/services/api'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)

const form = ref({
  title: '',
  slug: '',
  summary: '',
  coverImage: '',
  content: '',
  status: 'DRAFT'
})

const tagsInput = ref('')
const submitting = ref(false)

const handleSubmit = async () => {
  try {
    submitting.value = true
    const data = {
      ...form.value,
      tagNames: tagsInput.value.split(',').map(t => t.trim()).filter(Boolean)
    }

    if (isEdit.value) {
      await adminUpdatePost(Number(route.params.id), data)
    } else {
      await adminCreatePost(data)
    }

    router.push('/admin/posts')
  } catch (err: any) {
    alert(err.message || '保存失败')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  if (isEdit.value) {
    try {
      const response = await adminGetPost(Number(route.params.id))
      const post = response.data
      form.value = {
        title: post.title,
        slug: post.slug,
        summary: post.summary || '',
        coverImage: post.coverImage || '',
        content: post.content,
        status: post.status
      }
      tagsInput.value = post.tags.join(', ')
    } catch (err) {
      alert('加载失败')
    }
  }
})
</script>
