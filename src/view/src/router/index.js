import { createRouter, createWebHistory } from 'vue-router'
import ClientView from '../views/ClientView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: ClientView
        },
        {
            path: '/add',
            name: 'add',
            component: () => import('../views/AddClient.vue')
        },
        {
            path: '/edit/:id',
            name: 'edit',
            component: () => import('../views/UpdateClient.vue')
        }
    ]
})

export default router