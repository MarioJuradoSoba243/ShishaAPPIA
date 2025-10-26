import { defineStore } from 'pinia'
import axios from 'axios'

export const useTobaccoStore = defineStore('tobaccoStore', {
  state: () => ({
    tobaccos: [],
    loading: false,
    error: null,
    saving: false,
    saveError: null
  }),
  actions: {
    async fetchAll () {
      this.loading = true
      this.error = null
      try {
        const response = await axios.get('/api/tobaccos')
        this.tobaccos = response.data
      } catch (error) {
        console.error('Failed to load tobaccos', error)
        this.error = 'No se pudo cargar la lista de tabacos.'
      } finally {
        this.loading = false
      }
    },
    async create (payload) {
      this.saving = true
      this.saveError = null
      try {
        const response = await axios.post('/api/tobaccos', payload)
        this.tobaccos.push(response.data)
        return response.data
      } catch (error) {
        console.error('Failed to create tobacco', error)
        const message = error.response?.data?.message ?? 'No se pudo guardar el tabaco.'
        this.saveError = message
        throw new Error(message)
      } finally {
        this.saving = false
      }
    }
  }
})
