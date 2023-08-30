import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import TaskView from '@/views/TaskView.vue'
import ProjectView from '@/views/ProjectView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/project/:projectId',
      name: 'Project',
      component: ProjectView
    },
    {
      path: '/project/:projectId/task/:taskId',
      name: 'Task',
      component: TaskView
    },
  ]
})

export default router
