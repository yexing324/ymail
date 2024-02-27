import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import "./assets/css/common.css"
import axios from 'axios'
import Menus from 'vue3-menus';
axios.defaults.withCredentials = true;




const app = createApp(App)



// @ts-ignore
app.use(Menus)
app.use(router)
app.use(ElementPlus)
app.mount('#app')