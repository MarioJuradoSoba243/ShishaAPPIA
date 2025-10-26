<template>
  <div class="user-list">
    <button class="refresh" @click="loadUsers">Recargar</button>
    <p v-if="error" class="error">{{ error }}</p>
    <p v-else-if="loading" class="loading">Cargando usuarios...</p>
    <ul v-else>
      <li v-if="users.length === 0" class="empty">No hay usuarios registrados todavía.</li>
      <li v-for="user in users" :key="user.id">
        <span class="name">{{ user.name }}</span>
        <span class="email">{{ user.email }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapStores } from 'pinia'
import { useUserStore } from '../stores/userStore'

export default {
  name: 'UserList',
  computed: {
    ...mapStores(useUserStore),
    users () {
      return this.userStore.users
    },
    loading () {
      return this.userStore.loading
    },
    error () {
      return this.userStore.error
    }
  },
  created () {
    this.loadUsers()
  },
  methods: {
    async loadUsers () {
      await this.userStore.fetchUsers()
    }
  }
}
</script>

<style scoped>
.user-list {
  background: #ffffff;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.08);
}

.refresh {
  background-color: #3b82f6;
  border: none;
  color: #fff;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  margin-bottom: 1rem;
}

.refresh:hover {
  background-color: #2563eb;
}

.error {
  color: #dc2626;
}

.loading {
  color: #1e293b;
}

.empty {
  color: #475569;
  font-style: italic;
}

.name {
  font-weight: 600;
}

.email {
  margin-left: 0.5rem;
  color: #6b7280;
}
</style>
