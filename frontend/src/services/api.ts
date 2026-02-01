import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export interface Post {
  id: number
  title: string
  slug: string
  content: string
  summary: string
  coverImage?: string
  status: string
  views: number
  tags: string[]
  createdAt: string
  updatedAt: string
}

export interface Comment {
  id: number
  postId: number
  parentId?: number
  authorName: string
  content: string
  status: string
  createdAt: string
}

export interface Tag {
  id: number
  name: string
  slug: string
}

export interface PageResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

// Public APIs
export const getPosts = (params?: { tag?: string; search?: string; page?: number; size?: number }) =>
  api.get<PageResponse<Post>>('/posts', { params })

export const getPost = (slug: string) =>
  api.get<Post>(`/posts/${slug}`)

export const getTags = () =>
  api.get<Tag[]>('/tags')

export const getComments = (postId: number) =>
  api.get<Comment[]>('/comments', { params: { postId } })

export const createComment = (data: {
  postId: number
  parentId?: number
  authorName?: string
  authorEmail?: string
  content: string
}) => api.post<Comment>('/comments', data)

// Admin APIs
export const adminGetPosts = (page = 0, size = 10) =>
  api.get<PageResponse<Post>>('/admin/posts', { params: { page, size } })

export const adminGetPost = (id: number) =>
  api.get<Post>(`/admin/posts/${id}`)

export const adminCreatePost = (data: {
  title: string
  slug: string
  content: string
  summary?: string
  coverImage?: string
  status?: string
  tagNames?: string[]
}) => api.post<Post>('/admin/posts', data)

export const adminUpdatePost = (id: number, data: any) =>
  api.put<Post>(`/admin/posts/${id}`, data)

export const adminDeletePost = (id: number) =>
  api.delete(`/admin/posts/${id}`)

export const adminGetComments = (page = 0, size = 20) =>
  api.get<PageResponse<Comment>>('/admin/comments', { params: { page, size } })

export const adminUpdateCommentStatus = (id: number, status: string) =>
  api.put<Comment>(`/admin/comments/${id}/status`, { status })

export const adminDeleteComment = (id: number) =>
  api.delete(`/admin/comments/${id}`)

export const adminCreateTag = (name: string) =>
  api.post<Tag>('/admin/tags', { name })

export const adminDeleteTag = (id: number) =>
  api.delete(`/admin/tags/${id}`)

export default api
