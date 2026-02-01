import api from './api';
export const login = async (username, password) => {
    const response = await api.post('/auth/login', { username, password });
    const { token, username: user } = response.data;
    localStorage.setItem('token', token);
    localStorage.setItem('username', user);
    return response.data;
};
export const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
};
export const isAuthenticated = () => {
    return !!localStorage.getItem('token');
};
export const getUsername = () => {
    return localStorage.getItem('username');
};
