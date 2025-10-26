<template>
  <main class="container">
    <section class="panel">
      <header class="panel__header">
        <div>
          <h1 class="panel__title">Tabacos</h1>
          <p class="panel__subtitle">Gestiona las variedades disponibles en tu almacén.</p>
        </div>
        <button class="primary" type="button" @click="toggleForm">
          {{ showForm ? 'Cancelar' : 'Añadir tabaco' }}
        </button>
      </header>

      <form v-if="showForm" class="form" @submit.prevent="submitForm">
        <div class="form__row">
          <label for="name">Nombre</label>
          <input id="name" v-model="form.name" type="text" placeholder="Ej. Blue Mist" />
        </div>
        <div class="form__row">
          <label for="brand">Marca</label>
          <input id="brand" v-model="form.brand" type="text" placeholder="Ej. Starbuzz" />
        </div>
        <p v-if="localError" class="form__error">{{ localError }}</p>
        <p v-else-if="saveError" class="form__error">{{ saveError }}</p>
        <div class="form__actions">
          <button class="primary" type="submit" :disabled="saving">Guardar</button>
        </div>
      </form>

      <p v-if="error" class="status status--error">{{ error }}</p>
      <p v-else-if="loading" class="status">Cargando tabacos...</p>
      <ul v-else class="list">
        <li v-if="tobaccos.length === 0" class="list__empty">Aún no hay tabacos registrados.</li>
        <li v-for="tobacco in tobaccos" :key="tobacco.id" class="list__item">
          <span class="list__name">{{ tobacco.name }}</span>
          <span class="list__brand">{{ tobacco.brand }}</span>
        </li>
      </ul>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useTobaccoStore } from '../stores/tobaccoStore'

const tobaccoStore = useTobaccoStore()
const { tobaccos, loading, error, saving, saveError } = storeToRefs(tobaccoStore)

const showForm = ref(false)
const localError = ref(null)
const form = reactive({
  name: '',
  brand: ''
})

onMounted(async () => {
  if (tobaccos.value.length === 0) {
    await tobaccoStore.fetchAll()
  }
})

function toggleForm () {
  showForm.value = !showForm.value
  if (!showForm.value) {
    resetForm()
  }
}

async function submitForm () {
  localError.value = null
  if (!form.name.trim() || !form.brand.trim()) {
    localError.value = 'Completa todos los campos antes de guardar.'
    return
  }

  try {
    await tobaccoStore.create({
      name: form.name.trim(),
      brand: form.brand.trim()
    })
    resetForm()
    showForm.value = false
  } catch (e) {
    // El error ya se gestiona en el store
  }
}

function resetForm () {
  form.name = ''
  form.brand = ''
  localError.value = null
}
</script>

<style scoped>
.panel {
  background: #ffffff;
  border-radius: 16px;
  padding: 2rem;
  box-shadow: 0 16px 35px rgba(15, 23, 42, 0.16);
}

.panel__header {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.panel__title {
  margin: 0;
  font-size: 2rem;
  color: #0f172a;
}

.panel__subtitle {
  margin: 0.25rem 0 0;
  color: #475569;
}

.primary {
  background: linear-gradient(135deg, #6366f1, #8b5cf6);
  border: none;
  color: #fff;
  padding: 0.75rem 1.5rem;
  border-radius: 999px;
  cursor: pointer;
  font-weight: 600;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.primary:hover:not(:disabled) {
  transform: translateY(-3px);
  box-shadow: 0 12px 25px rgba(99, 102, 241, 0.35);
}

.form {
  background: #f1f5f9;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}

.form__row {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.form__row label {
  font-weight: 600;
  color: #0f172a;
}

.form__row input {
  padding: 0.75rem 1rem;
  border-radius: 8px;
  border: 1px solid #cbd5f5;
  font-size: 1rem;
}

.form__error {
  color: #dc2626;
  margin: 0 0 1rem;
}

.form__actions {
  display: flex;
  justify-content: flex-end;
}

.status {
  color: #475569;
}

.status--error {
  color: #dc2626;
}

.list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.list__item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f8fafc;
  border-radius: 12px;
  padding: 1rem 1.25rem;
}

.list__empty {
  color: #475569;
  text-align: center;
  padding: 2rem 1rem;
  background: #f8fafc;
  border-radius: 12px;
}

.list__name {
  font-weight: 700;
  color: #1e293b;
}

.list__brand {
  color: #475569;
}
</style>
