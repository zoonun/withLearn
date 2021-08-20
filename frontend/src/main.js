import { createApp, h } from 'vue'
import App from './App.vue'
import store from './api/store'
import axios from './api/axios'
import VueAxios from './api/axios'
import i18n from './api/i18n'
import router from './router/vue-router'
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

const app = createApp({
  render: ()=>h(App)
})
app.use(VueAxios, axios)
app.use(store)
app.use(i18n)
app.use(router)
app.use(VueSweetalert2);

app.mount('#app')
