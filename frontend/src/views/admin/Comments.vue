<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <router-link to="/admin" class="font-bold text-xl">管理后台</router-link>
      </div>
    </nav>

    <main class="container py-12">
      <h2 class="text-4xl font-bold mb-8">评论管理</h2>

      <div class="bg-white rounded-lg shadow">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left p-4">作者</th>
              <th class="text-left p-4">内容</th>
              <th class="text-left p-4">状态</th>
              <th class="text-left p-4">时间</th>
              <th class="text-left p-4">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="comment in comments" :key="comment.id" class="border-b border-gray-200">
              <td class="p-4">{{ comment.authorName || '匿名' }}</td>
              <td class="p-4">{{ comment.content.substring(0, 50) }}...</td>
              <td class="p-4">{{ comment.status }}</td>
              <td class="p-4">{{ formatDate(comment.createdAt) }}</td>
              <td class="p-4 flex gap-2">
                <button
                  v-if="comment.status !== 'APPROVED'"
                  @click="approve(comment.id)"
                  class="text-green-500"
                >
                  通过
                </button>
                <button @click="deleteComment(comment.id)" class="text-red-500">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { adminGetComments, adminUpdateCommentStatus, adminDeleteComment, type Comment } from '@/services/api'

const comments = ref<Comment[]>([])

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

const loadComments = async () => {
  try {
    const response = await adminGetComments()
    comments.value = response.data.content
  } catch (err) {
    console.error(err)
  }
}

const approve = async (id: number) => {
  try {
    await adminUpdateCommentStatus(id, 'APPROVED')
    loadComments()
  } catch (err) {
    alert('操作失败')
  }
}

const deleteComment = async (id: number) => {
  if (!confirm('确定删除？')) return
  try {
    await adminDeleteComment(id)
    loadComments()
  } catch (err) {
    alert('删除失败')
  }
}

onMounted(() => {
  loadComments()
})
</script>
