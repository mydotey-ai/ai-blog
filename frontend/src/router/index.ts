import { createRouter, createWebHistory } from 'vue-router'
import { isAuthenticated } from '@/services/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/Home.vue')
    },
    {
      path: '/posts/:slug',
      name: 'PostDetail',
      component: () => import('@/views/PostDetail.vue')
    },
    {
      path: '/tags/:slug',
      name: 'TagPosts',
      component: () => import('@/views/TagPosts.vue')
    },
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: () => import('@/views/admin/Login.vue')
    },
    {
      path: '/admin',
      name: 'AdminDashboard',
      component: () => import('@/views/admin/Dashboard.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/posts',
      name: 'AdminPosts',
      component: () => import('@/views/admin/Posts.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/posts/new',
      name: 'AdminNewPost',
      component: () => import('@/views/admin/PostEditor.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/posts/edit/:id',
      name: 'AdminEditPost',
      component: () => import('@/views/admin/PostEditor.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/comments',
      name: 'AdminComments',
      component: () => import('@/views/admin/Comments.vue'),
      meta: { requiresAuth: true }
    }
  ],
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !isAuthenticated()) {
    next({ name: 'AdminLogin' })
  } else {
    next()
  }
})

export default router
