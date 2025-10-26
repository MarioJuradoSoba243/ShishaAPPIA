<template>
  <main class="container">
    <section>
      <h1 class="title">Usuarios</h1>
      <p class="subtitle">
        La lista se carga desde el backend cuando está disponible.
      </p>
      <UserList />
    </section>
  </main>
</template>

<script>
import UserList from '../components/UserList.vue'

export default {
  name: 'UserListView',
  components: { UserList }
}
</script>
