<template>
  <section class="border-t border-gray-200 pt-16">
    <h2 class="text-4xl font-bold mb-12">评论</h2>

    <form @submit.prevent="submitComment" class="mb-16">
      <input
        v-model="form.authorName"
        type="text"
        placeholder="昵称（可选）"
        class="input mb-4"
      />
      <input
        v-model="form.authorEmail"
        type="email"
        placeholder="邮箱（可选）"
        class="input mb-4"
      />
      <textarea
        v-model="form.content"
        placeholder="写下你的评论..."
        class="input textarea mb-4"
        required
      ></textarea>
      <button type="submit" class="btn-primary" :disabled="submitting">
        {{ submitting ? '发送中...' : '发表评论' }}
      </button>
    </form>

    <div v-if="comments.length === 0" class="text-center py-8 text-secondary">
      暂无评论
    </div>

    <div v-else class="space-y-8">
      <div v-for="comment in comments" :key="comment.id" class="border-b border-gray-200 pb-8">
        <div class="flex items-center gap-4 mb-4">
          <span class="font-semibold">{{ comment.authorName || '匿名' }}</span>
          <span class="text-sm text-tertiary">{{ formatDate(comment.createdAt) }}</span>
        </div>
        <p class="text-secondary">{{ comment.content }}</p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getComments, createComment, type Comment } from '@/services/api'

const props = defineProps<{ postId: number }>()

const comments = ref<Comment[]>([])
const submitting = ref(false)
const form = ref({
  authorName: '',
  authorEmail: '',
  content: ''
})

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}

const loadComments = async () => {
  try {
    const response = await getComments(props.postId)
    comments.value = response.data
  } catch (err) {
    console.error(err)
  }
}

const submitComment = async () => {
  try {
    submitting.value = true
    await createComment({
      postId: props.postId,
      authorName: form.value.authorName,
      authorEmail: form.value.authorEmail,
      content: form.value.content
    })
    form.value = { authorName: '', authorEmail: '', content: '' }
    alert('评论已提交，等待审核')
  } catch (err: any) {
    alert(err.message || '提交失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadComments()
})
</script>
