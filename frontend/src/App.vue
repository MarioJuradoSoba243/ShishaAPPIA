<template>
  <div id="app">
    <header class="app-header">
      <h1>Shisha APPIA</h1>
      <p>Gestiona tu catálogo de tabacos y mezclas con Spring Boot, Vue.js y SQLite.</p>
    </header>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  min-height: 100vh;
  background: linear-gradient(135deg, #0ea5e9, #7c3aed);
  color: #0f172a;
}

.app-header {
  text-align: center;
  padding: 3rem 1rem 1rem;
  color: #f8fafc;
}

.app-header h1 {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
}

.container {
  max-width: 960px;
  margin: 0 auto;
  padding: 0 1.5rem 2rem;
}

.title {
  font-size: 2rem;
  margin-bottom: 0.5rem;
}

.subtitle {
  margin-bottom: 1.5rem;
  color: #475569;
}
</style>
