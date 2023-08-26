import { createApp } from 'vue'
import App from './App.vue'
import './style.css'
import router from './router/index.js'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'

createApp(App)
    .use(router)
    .mount('#app')