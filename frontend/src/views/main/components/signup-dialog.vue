<template>
  <el-dialog custom-class="signup-dialog" title="회원가입" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="signupForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="id-check">
        <el-button @click="checkDuplicate">중복확인</el-button>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordConfirm" label="비밀번호 확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.passwordConfirm" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="department" label="소속" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.department" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="position" label="직책" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.position" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickSignup" :disabled="state.buttonDisabled">회원가입</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

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
      store.dispatch('root/checkDuplicate', value)
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length > 16) {
        callback(new Error('최대 16글자를 입력해야 합니다.'))
      } else if (!state.isAvailableId) {
        callback(new Error('이미 존재하는 아이디입니다.'))
      } else {
        callback()
      }
    }

    const validatePw = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length < 9) {
        callback(new Error('최소 9글자를 입력해야 합니다.'))
      } else if (value.length > 16) {
        callback(new Error('최대 16글자를 입력해야 합니다.'))
      } else if (!pwRegExp.test(value)) {
        callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
      } else {
        callback()
      }
    }

    const validatePw2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length < 9) {
        callback(new Error('최소 9글자를 입력해야 합니다.'))
      } else if (value.length > 16) {
        callback(new Error('최대 16글자를 입력해야 합니다.'))
      } else if (!pwRegExp.test(value)) {
        callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
      } else if (value !== state.form.password) {
        callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
      } else {
        callback()
      }
    }

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

    onMounted(() => {

    })

    const clickSignup = function () {
      signupForm.value.validate((valid) => {
        if (valid) {
          store.commit('root/startSpinner')
          store.dispatch('root/requestSignup', { id: state.form.id, password: state.form.password, name: state.form.name, department: state.form.department, position: state.form.position })
          .then(function () {
            alert('회원가입 되었습니다.')
            emit('closeSignupDialog')
            document.location.reload()
          })
          .catch(function (err) {
            alert(err.response.data.message)
          })
          .finally(store.commit('root/endSpinner'))
        } else {
          alert('잘못된 입력입니다.')
        }
      })
    }

    const checkDuplicate = function () {
      store.dispatch('root/checkDuplicate', state.form.id)
      .then(function () {
        if (state.isAvailableId) {
          alert('사용가능한 아이디입니다.')
        }
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

    return { signupForm, state, clickSignup, handleClose, checkDuplicate }
  }
}
</script>

<style>

</style>
