<template>
  <main class="container">
    <section class="menu">
      <h1 class="title">Catálogo de Shisha</h1>
      <p class="subtitle">Selecciona una categoría para gestionar tabacos o crear nuevas mezclas.</p>
      <div class="menu-grid">
        <RouterLink class="menu-card" to="/tabacos">
          <h2>Tabacos</h2>
          <p>Consulta y añade nuevos tabacos a tu colección.</p>
        </RouterLink>
        <RouterLink class="menu-card" to="/mezclas">
          <h2>Mezclas</h2>
          <p>Crea combinaciones personalizadas usando tus tabacos favoritos.</p>
        </RouterLink>
      </div>
    </section>
  </main>
</template>

<script setup>
</script>

<style scoped>
.menu {
  background: #ffffff;
  border-radius: 16px;
  padding: 2.5rem;
  box-shadow: 0 20px 45px rgba(15, 23, 42, 0.18);
  text-align: center;
}

.title {
  font-size: 2.4rem;
  margin-bottom: 0.5rem;
  color: #0f172a;
}

.subtitle {
  color: #475569;
  margin-bottom: 2.5rem;
}

.menu-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1.5rem;
}

.menu-card {
  display: block;
  background: linear-gradient(135deg, #38bdf8, #6366f1);
  color: #f8fafc;
  padding: 2rem 1.5rem;
  border-radius: 12px;
  text-decoration: none;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.menu-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 18px 40px rgba(99, 102, 241, 0.35);
}

.menu-card h2 {
  margin: 0 0 0.5rem;
  font-size: 1.8rem;
}

.menu-card p {
  margin: 0;
  line-height: 1.5;
}
</style>
