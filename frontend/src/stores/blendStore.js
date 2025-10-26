import { defineStore } from 'pinia'
import axios from 'axios'

export const useBlendStore = defineStore('blendStore', {
  state: () => ({
    blends: [],
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
        const response = await axios.get('/api/blends')
        this.blends = response.data
      } catch (error) {
        console.error('Failed to load blends', error)
        this.error = 'No se pudo cargar la lista de mezclas.'
      } finally {
        this.loading = false
      }
    },
    async create (payload) {
      this.saving = true
      this.saveError = null
      try {
        const response = await axios.post('/api/blends', payload)
        this.blends.push(response.data)
        return response.data
      } catch (error) {
        console.error('Failed to create blend', error)
        const message = error.response?.data?.message ?? 'No se pudo guardar la mezcla.'
        this.saveError = message
        throw new Error(message)
      } finally {
        this.saving = false
      }
    }
  }
})
