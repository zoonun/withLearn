<template>
  <div>
    
  </div>
</template>

<script>
import { reactive, ref, computed } from 'vue'
import { useStore } from 'vuex'
import Swal from 'sweetalert2'

export default {
  name: 'signup-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const signupForm = ref(null)
    /*
      // Signup Validator
    */
    const pwRegExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*\(\)]).{9,}$/

    const validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length > 16) {
        callback(new Error('최대 16글자를 입력해야 합니다.'))
      } else {
        callback()
      }
    }

    // const validatePw = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('필수 입력 항목입니다.'))
    //   } else if (value.length < 9) {
    //     callback(new Error('최소 9글자를 입력해야 합니다.'))
    //   } else if (value.length > 16) {
    //     callback(new Error('최대 16글자를 입력해야 합니다.'))
    //   } else if (!pwRegExp.test(value)) {
    //     callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
    //   } else {
    //     callback()
    //   }
    // }

    const validatePw = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else {
        callback()
      }
    }

    const validatePw2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else {
        callback()
      }
    }
    // const validatePw2 = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('필수 입력 항목입니다.'))
    //   } else if (value.length < 9) {
    //     callback(new Error('최소 9글자를 입력해야 합니다.'))
    //   } else if (value.length > 16) {
    //     callback(new Error('최대 16글자를 입력해야 합니다.'))
    //   } else if (!pwRegExp.test(value)) {
    //     callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
    //   } else if (value !== state.form.password) {
    //     callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
    //   } else {
    //     callback()
    //   }
    // }

    const state = reactive({
      form: {
        name: '',
        department: '',
        position: '',
        id: '',
        password: '',
        passwordConfirm: '',
        align: 'left'
      },

      rules: {
        id: [
          { required: true, validator: validateId, trigger: 'blur' },
        ],
        password: [
          { required: true, validator: validatePw, trigger: 'blur' }
        ],
        passwordConfirm: [
          { required: true, validator: validatePw2, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          { max: 30, message: '최대 30글자를 입력해야 합니다.', trigger: 'blur' }
        ],
        department: [
          { max: 30, message: '최대 30글자를 입력해야 합니다.', trigger: 'blur' }
        ],
        position: [
          { max: 30, message: '최대 30글자를 입력해야 합니다.', trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      isAvailableId: computed(() => store.getters['root/getIsAvailableId'])
    })

    const clickSignup = function () {
      signupForm.value.validate((valid) => {
        if (valid) {
          store.commit('root/setSpinnerStart')
          store.dispatch('root/requestSignup', { id: state.form.id, password: state.form.password, name: state.form.name, department: state.form.department, position: state.form.position })
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
        } else {
          Swal.fire({
              icon: 'warning',
              html: '잘못된 입력입니다.',
              showConfirmButton: false,
              timer: 1000,
          })
        }
      })
    }

    const checkAvailableId = function () {
      store.dispatch('root/requestAvailableId', state.form.id)
      .then(function () {
      })
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      state.form.passwordConfirm = ''
      state.form.name = ''
      state.form.department = ''
      state.form.position = ''
      emit('closeSignupDialog')
    }

    return { signupForm, state, clickSignup, handleClose, checkAvailableId }
  }
}
</script>

<style>

.swal2-container {
  z-index: 10000;
}
.el-dialog .el-form .el-form-item .el-input {
  width:80%;
}


</style>
