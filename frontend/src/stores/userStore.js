import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('userStore', {
  state: () => ({
    users: [],
    loading: false,
    error: null
  }),
  actions: {
    async fetchUsers() {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get('/api/users');
        this.users = response.data;
      } catch (error) {
        this.error = 'No se pudo cargar la lista de usuarios.';
        console.error('Failed to load users', error);
      } finally {
        this.loading = false;
      }
    }
  }
});
