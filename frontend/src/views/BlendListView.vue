<template>
  <main class="container">
    <section class="panel">
      <header class="panel__header">
        <div>
          <h1 class="panel__title">Mezclas</h1>
          <p class="panel__subtitle">Combina tabacos para crear perfiles de sabor únicos.</p>
        </div>
        <div class="panel__actions">
          <RouterLink class="secondary" to="/">Volver al menú</RouterLink>
          <button class="primary" type="button" @click="toggleForm">
            {{ showForm ? 'Cancelar' : 'Crear mezcla' }}
          </button>
        </div>
      </header>

      <form v-if="showForm" class="form" @submit.prevent="submitForm">
        <div class="form__row">
          <label for="blend-name">Nombre</label>
          <input id="blend-name" v-model="form.name" type="text" placeholder="Ej. Sunset Punch" />
        </div>
        <div class="form__row">
          <label for="blend-description">Descripción</label>
          <textarea id="blend-description" v-model="form.description" rows="3" placeholder="Notas sobre la mezcla" />
        </div>
        <div class="form__row">
          <label for="blend-tobaccos">Tabacos incluidos</label>
          <select id="blend-tobaccos" v-model="form.tobaccoIds" multiple>
            <option v-for="tobacco in tobaccos" :key="tobacco.id" :value="tobacco.id">
              {{ tobacco.name }} ({{ tobacco.brand }})
            </option>
          </select>
          <small class="form__hint">Mantén pulsado Ctrl o Cmd para seleccionar varios tabacos.</small>
        </div>
        <p v-if="localError" class="form__error">{{ localError }}</p>
        <p v-else-if="saveError" class="form__error">{{ saveError }}</p>
        <div class="form__actions">
          <button class="primary" type="submit" :disabled="saving || tobaccos.length === 0">
            Guardar
          </button>
        </div>
      </form>

      <p v-if="error" class="status status--error">{{ error }}</p>
      <p v-else-if="loading" class="status">Cargando mezclas...</p>
      <ul v-else class="list">
        <li v-if="blends.length === 0" class="list__empty">Aún no hay mezclas registradas.</li>
        <li v-for="blend in blends" :key="blend.id" class="list__item">
          <div>
            <h3>{{ blend.name }}</h3>
            <p class="list__description" v-if="blend.description">{{ blend.description }}</p>
            <ul class="chips">
              <li v-for="tobacco in blend.tobaccos" :key="tobacco.id" class="chip">
                {{ tobacco.name }}
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useBlendStore } from '../stores/blendStore'
import { useTobaccoStore } from '../stores/tobaccoStore'

const blendStore = useBlendStore()
const tobaccoStore = useTobaccoStore()

const { blends, loading, error, saving, saveError } = storeToRefs(blendStore)
const { tobaccos } = storeToRefs(tobaccoStore)

const showForm = ref(false)
const localError = ref(null)
const form = reactive({
  name: '',
  description: '',
  tobaccoIds: []
})

onMounted(async () => {
  if (tobaccos.value.length === 0) {
    await tobaccoStore.fetchAll()
  }
  await blendStore.fetchAll()
})

function toggleForm () {
  showForm.value = !showForm.value
  if (!showForm.value) {
    resetForm()
  }
}

async function submitForm () {
  localError.value = null
  if (!form.name.trim()) {
    localError.value = 'El nombre es obligatorio.'
    return
  }
  if (form.tobaccoIds.length === 0) {
    localError.value = 'Selecciona al menos un tabaco para la mezcla.'
    return
  }

  try {
    await blendStore.create({
      name: form.name.trim(),
      description: form.description.trim() || null,
      tobaccoIds: form.tobaccoIds
    })
    resetForm()
    showForm.value = false
  } catch (e) {
    // El store gestiona el mensaje de error
  }
}

function resetForm () {
  form.name = ''
  form.description = ''
  form.tobaccoIds = []
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

.panel__actions {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 0.75rem;
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
  background: linear-gradient(135deg, #f97316, #f43f5e);
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
  box-shadow: 0 12px 25px rgba(244, 63, 94, 0.35);
}

.secondary {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  background: transparent;
  border: 1px solid #fb923c;
  color: #c2410c;
  padding: 0.75rem 1.25rem;
  border-radius: 999px;
  font-weight: 600;
  text-decoration: none;
  transition: background 0.2s ease, color 0.2s ease, box-shadow 0.2s ease;
}

.secondary:hover {
  background: rgba(249, 115, 22, 0.12);
  color: #9a3412;
  box-shadow: 0 10px 20px rgba(244, 114, 182, 0.15);
}

.form {
  background: #fef3c7;
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

.form__row input,
.form__row textarea,
.form__row select {
  padding: 0.75rem 1rem;
  border-radius: 8px;
  border: 1px solid #fcd34d;
  font-size: 1rem;
}

.form__row select {
  min-height: 140px;
}

.form__hint {
  color: #ca8a04;
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
  background: #fff7ed;
  border-radius: 12px;
  padding: 1.25rem;
  box-shadow: inset 0 0 0 1px rgba(249, 115, 22, 0.25);
}

.list__item h3 {
  margin: 0 0 0.5rem;
  color: #9a3412;
}

.list__description {
  margin: 0 0 0.75rem;
  color: #7c2d12;
}

.list__empty {
  color: #9a3412;
  text-align: center;
  padding: 2rem 1rem;
  background: #fff7ed;
  border-radius: 12px;
}

.chips {
  list-style: none;
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  padding: 0;
  margin: 0;
}

.chip {
  background: #fdba74;
  color: #7c2d12;
  padding: 0.35rem 0.75rem;
  border-radius: 999px;
  font-size: 0.9rem;
}
</style>
