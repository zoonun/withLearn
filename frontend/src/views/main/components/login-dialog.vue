<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container">
      <div class="modal-header">
        로그인
      </div>
      <hr class="modal-hr">
      <div class="modal-body">
        <div class="modal-input">
          <input id="username" type="text" name="username" placeholder="아이디">
          <label for="username">아이디</label>
        </div>
        <div class="modal-input">
          <input id="password" type="password">
          <label for="password">비밀번호</label>
        </div>
      </div>
      <div class="modal-footer">
        풋
      </div>
    </div>
  </div>
</template>
<style>

</style>
<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import Swal from 'sweetalert2'

export default {
  name: 'login-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const loginForm = ref(null)

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
   const pwRegExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*\(\)]).{9,}$/

    const validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else {
        callback()
      }
    }
    // const validateId = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('필수 입력 항목입니다.'))
    //   } else if (value.length > 16) {
    //     callback(new Error('최대 16글자를 입력해야 합니다.'))
    //   } else {
    //     callback()
    //   }
    // }

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

    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left'
      },
      rules: {
        id: [
          { required: true, validator: validateId, trigger: 'blur' },
        ],
        password: [
          { required: true, validator: validatePw, trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
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

    return { loginForm, state, clickLogin, handleClose }
  }
}
</script>
