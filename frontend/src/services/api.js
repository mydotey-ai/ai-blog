import axios from 'axios';
const api = axios.create({
    baseURL: '/api',
    timeout: 10000
});
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
// Public APIs
export const getPosts = (params) => api.get('/posts', { params });
export const getPost = (slug) => api.get(`/posts/${slug}`);
export const getTags = () => api.get('/tags');
export const getComments = (postId) => api.get('/comments', { params: { postId } });
export const createComment = (data) => api.post('/comments', data);
// Admin APIs
export const adminGetPosts = (page = 0, size = 10) => api.get('/admin/posts', { params: { page, size } });
export const adminGetPost = (id) => api.get(`/admin/posts/${id}`);
export const adminCreatePost = (data) => api.post('/admin/posts', data);
export const adminUpdatePost = (id, data) => api.put(`/admin/posts/${id}`, data);
export const adminDeletePost = (id) => api.delete(`/admin/posts/${id}`);
export const adminGetComments = (page = 0, size = 20) => api.get('/admin/comments', { params: { page, size } });
export const adminUpdateCommentStatus = (id, status) => api.put(`/admin/comments/${id}/status`, { status });
export const adminDeleteComment = (id) => api.delete(`/admin/comments/${id}`);
export const adminCreateTag = (name) => api.post('/admin/tags', { name });
export const adminDeleteTag = (id) => api.delete(`/admin/tags/${id}`);
export default api;
