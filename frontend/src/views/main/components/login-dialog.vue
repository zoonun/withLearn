<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container" @keyup.esc="handleClose">
      <div class="modal-header">
        로그인
        <svg
          class="btn-modal-close"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
          @click="handleClose"
        >
        <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M6 18L18 6M6 6l12 12"
          ></path>
        </svg>
      </div>
      <hr class="modal-hr">
      <Form @submit="clickLogin" :validation-schema="schema" class="modal-body">
        <TextInput
          name="id"
          type="text"
          v-model="state.form.id"
          placeholder="아이디"
        />
        <TextInput
          name="password"
          type="password"
          v-model="state.form.password"
          placeholder="비밀번호"
        />
        <button class="btn btn-submit" type="submit">로그인</button>
      </Form>
      <hr class="modal-hr">
      <div class="modal-footer">
      </div>
    </div>
  </div>
</template>
<style>

</style>
<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { Form } from 'vee-validate'
import * as Yup from 'yup'
import TextInput from '@/components/TextInput'
import Swal from 'sweetalert2'

export default {
  name: 'login-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },
  components: {
    TextInput,
    Form
  },
  setup(props, { emit }) {
    const store = useStore()
    const loginForm = ref(null)
    const pwRegExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()]).{9,}$/
    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left'
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    let schema = Yup.object().shape({
      id: Yup
        .string()
        .required('필수 입력 항목입니다.'),
      password: Yup
        .string()
        .required('필수 입력 항목입니다.'),
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    const clickLogin = function () {
      store.commit('root/setSpinnerStart')
      store.dispatch('root/requestLogin', { id: state.form.id, password: state.form.password })
        .then(function (result) {
          store.dispatch('root/requestSaveJWT', result.data)
          emit('closeLoginDialog')
          Swal.fire({
            icon: 'success',
            html: '로그인되었습니다.',
            showConfirmButton: false,
            timer:1000
          })
          setTimeout(function(){
            document.location.reload();
          }, 1000);
          // document.location.reload()
        })
        .catch(function (err) {
          Swal.fire({
            icon: 'error',
            html: err.response.data.message,
            showConfirmButton: false,
            timer: 1000
          })
        })
        .finally(store.commit('root/setSpinnerEnd'))
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      emit('closeLoginDialog')
    }

    return { loginForm, state, clickLogin, handleClose, schema }
  }
}
</script>
