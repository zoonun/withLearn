<template @keyup.esc="handleClose">
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container">
      <div class="modal-header">
        로그인
      </div>
      <hr class="modal-hr">
      <Form @submit="clickLogin" :validation-schema="schema">
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
        소셜로그인
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
      password: Yup.string()
        // .matches(pwRegExp, '비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.')
        // .min(9, '최소 9글자를 입력해야 합니다.')
        // .max(16, '최대 16글자를 입력해야 합니다.')
        .required('필수 입력 항목입니다.'),
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
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
        } else {
          Swal.fire({
              icon: 'error',
              html: '잘못된 입력입니다.',
              showConfirmButton: false,
              timer: 1000
            })
        }
      });
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
