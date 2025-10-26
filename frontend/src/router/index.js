import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import TobaccoListView from '../views/TobaccoListView.vue'
import BlendListView from '../views/BlendListView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/tabacos',
    name: 'tobaccos',
    component: TobaccoListView
  },
  {
    path: '/mezclas',
    name: 'blends',
    component: BlendListView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
