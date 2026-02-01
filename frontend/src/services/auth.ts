import api from './api'

export interface LoginResponse {
  token: string
  username: string
}

export const login = async (username: string, password: string) => {
  const response = await api.post<LoginResponse>('/auth/login', { username, password })
  const { token, username: user } = response.data
  localStorage.setItem('token', token)
  localStorage.setItem('username', user)
  return response.data
}

export const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
}

export const isAuthenticated = () => {
  return !!localStorage.getItem('token')
}

export const getUsername = () => {
  return localStorage.getItem('username')
}
