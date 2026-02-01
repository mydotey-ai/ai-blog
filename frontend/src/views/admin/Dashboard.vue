<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white border-b border-gray-200">
      <div class="container flex items-center justify-between h-16">
        <h1 class="font-bold text-xl">管理后台</h1>
        <div class="flex gap-6">
          <router-link to="/admin/posts" class="hover:text-klein-blue">文章</router-link>
          <router-link to="/admin/comments" class="hover:text-klein-blue">评论</router-link>
          <button @click="handleLogout" class="text-red-500">退出</button>
        </div>
      </div>
    </nav>

    <main class="container py-12">
      <h2 class="text-4xl font-bold mb-8">欢迎回来，{{ username }}</h2>

      <div class="grid grid-cols-3 gap-6">
        <router-link to="/admin/posts" class="bg-white p-8 rounded-lg shadow hover:shadow-lg transition">
          <h3 class="text-2xl font-bold mb-2">文章管理</h3>
          <p class="text-secondary">创建和管理博客文章</p>
        </router-link>

        <router-link to="/admin/comments" class="bg-white p-8 rounded-lg shadow hover:shadow-lg transition">
          <h3 class="text-2xl font-bold mb-2">评论审核</h3>
          <p class="text-secondary">审核和管理评论</p>
        </router-link>

        <router-link to="/" class="bg-white p-8 rounded-lg shadow hover:shadow-lg transition">
          <h3 class="text-2xl font-bold mb-2">查看网站</h3>
          <p class="text-secondary">前往公开页面</p>
        </router-link>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { logout, getUsername } from '@/services/auth'

const router = useRouter()
const username = getUsername()

const handleLogout = () => {
  logout()
  router.push('/admin/login')
}
</script>
