import { createRouter, createWebHistory } from 'vue-router';
import UserListView from '../views/UserListView.vue';

const routes = [
  {
    path: '/',
    name: 'users',
    component: UserListView
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
