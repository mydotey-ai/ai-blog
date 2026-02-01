<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="max-w-md w-full bg-white p-12 rounded-lg shadow-lg">
      <h1 class="text-4xl font-bold mb-8">管理员登录</h1>

      <form @submit.prevent="handleLogin">
        <input
          v-model="username"
          type="text"
          placeholder="用户名"
          class="input mb-4"
          required
        />
        <input
          v-model="password"
          type="password"
          placeholder="密码"
          class="input mb-6"
          required
        />
        <button type="submit" class="btn-primary w-full" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>

      <p v-if="error" class="text-red-500 mt-4">{{ error }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/services/auth'

const router = useRouter()
const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

const handleLogin = async () => {
  try {
    loading.value = true
    error.value = ''
    await login(username.value, password.value)
    router.push('/admin')
  } catch (err: any) {
    error.value = err.response?.data?.message || '登录失败'
  } finally {
    loading.value = false
  }
}
</script>
