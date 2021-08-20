<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container" @keyup.esc="handleClose" style="width: 500px">
      <div class="modal-header">
        회원가입
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
      <Form @submit="clickSignup" :validation-schema="schema" class="modal-body">
        <TextInput
          name="id"
          type="text"
          placeholder="아이디"
        />
        <TextInput
          name="name"
          type="text"
          placeholder="이름"
        />
        <TextInput
          name="password"
          type="password"
          placeholder="비밀번호"
          success-message="사용 가능한 비밀번호입니다."
        />
        <TextInput
          name="passwordConfirm"
          type="password"
          placeholder="비밀번호 확인"
        />
        <button class="btn btn-submit" type="submit">회원가입</button>
      </Form>
    </div>
  </div>
</template>

<script>
import { reactive, ref, computed } from 'vue'
import { useStore } from 'vuex'
import Swal from 'sweetalert2'
import { Form } from 'vee-validate'
import * as Yup from 'yup'
import TextInput from '@/components/TextInput'

export default {
  name: 'signup-dialog',

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
    const signupForm = ref(null)
    const pwRegExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()]).{9,}$/

    let schema = Yup.object().shape({
      id: Yup
        .string()
        .required('필수 입력 항목입니다.'),
      name: Yup.string().required('필수 입력 항목입니다.'),
      password: Yup.string()
        .matches(pwRegExp, '비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.')
        .min(9, '최소 9글자를 입력해야 합니다.')
        .max(16, '최대 16글자를 입력해야 합니다.')
        .required('필수 입력 항목입니다.'),
      passwordConfirm: Yup.string()
        .oneOf([Yup.ref('password')], '비밀번호가 일치하지 않습니다.')
        .required('필수 입력 항목입니다.'),
    })

    const state = reactive({
      dialogVisible: computed(() => props.open),
      isAvailableId: computed(() => store.getters['root/getIsAvailableId'])
    })

    const clickSignup = function (value) {
      store.commit('root/setSpinnerStart')
      store.dispatch('root/requestSignup', value)
      .then(function () {
        emit('closeSignupDialog')
        Swal.fire({
          icon: 'success',
          html: '회원가입 되었습니다.',
          showConfirmButton: false,
          timer: 1000,
        })
        setTimeout(function(){
          document.location.reload();
        }, 1000);
      })
      .catch(function (err) {
        Swal.fire({
          icon: 'warning',
          html: err.response.data.message,
          showConfirmButton: false,
          timer: 1000,
        })
      })
      .finally(store.commit('root/setSpinnerEnd'))
    }

    const handleClose = function () {
      emit('closeSignupDialog')
    }

    return { signupForm, state, clickSignup, handleClose, schema }
  }
}
</script>

<style>
</style>
