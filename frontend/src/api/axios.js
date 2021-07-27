import VueAxios from 'vue-axios'
import axios from 'axios'
// import config from '../config'

const BASE_URL = '/api/v1'
const DEFAULT_ACCEPT_TYPE = 'application/json'
const USER = JSON.parse(localStorage.getItem('user'))

if (USER) {
  const DEFAULT_ACCESS_TOKEN = USER.accessToken
  axios.defaults.headers['Authorization'] = `Bearer ${DEFAULT_ACCESS_TOKEN}`
}

axios.defaults.baseURL = BASE_URL
axios.defaults.headers['Content-Type'] = DEFAULT_ACCEPT_TYPE

export default { VueAxios, axios }
