<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <router-link to="/admin" class="font-bold text-xl">管理后台</router-link>
        <button @click="logout" class="text-red-500">退出</button>
      </div>
    </nav>

    <main class="container py-12">
      <div class="flex items-center justify-between mb-8">
        <h2 class="text-4xl font-bold">文章管理</h2>
        <router-link to="/admin/posts/new" class="btn-primary">新建文章</router-link>
      </div>

      <div class="bg-white rounded-lg shadow">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left p-4">标题</th>
              <th class="text-left p-4">状态</th>
              <th class="text-left p-4">浏览量</th>
              <th class="text-left p-4">创建时间</th>
              <th class="text-left p-4">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in posts" :key="post.id" class="border-b border-gray-200">
              <td class="p-4">{{ post.title }}</td>
              <td class="p-4">{{ post.status }}</td>
              <td class="p-4">{{ post.views }}</td>
              <td class="p-4">{{ formatDate(post.createdAt) }}</td>
              <td class="p-4 flex gap-2">
                <router-link :to="`/admin/posts/edit/${post.id}`" class="text-klein-blue">编辑</router-link>
                <button @click="deletePost(post.id)" class="text-red-500">删除</button>
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
import { useRouter } from 'vue-router'
import { adminGetPosts, adminDeletePost, type Post } from '@/services/api'
import { logout as authLogout } from '@/services/auth'

const router = useRouter()
const posts = ref<Post[]>([])

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

const loadPosts = async () => {
  try {
    const response = await adminGetPosts()
    posts.value = response.data.content
  } catch (err) {
    console.error(err)
  }
}

const deletePost = async (id: number) => {
  if (!confirm('确定删除？')) return
  try {
    await adminDeletePost(id)
    loadPosts()
  } catch (err) {
    alert('删除失败')
  }
}

const logout = () => {
  authLogout()
  router.push('/admin/login')
}

onMounted(() => {
  loadPosts()
})
</script>
