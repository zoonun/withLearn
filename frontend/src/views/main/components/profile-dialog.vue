<template>
  <el-dialog custom-class="profile-dialog" title="프로필" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="profileForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" >
        <span> {{ state.form.id }}</span>
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
        <el-button type="primary" @click="clickSave" :disabled="state.buttonDisabled">저장</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'profile-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()

    const profileForm = ref(null)

    /*
      // Profile Validator
    */

    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length > 30) {
        callback(new Error('최대 30글자를 입력해야 합니다.'))
      } else {
        callback()
      }
    }
    // const profileItem= computed(() => {
    //     let result = {}
    //     const profileArray =  store.getters['root/getProfile']
    //     console.log('profileArray', profileArray)
    //     if (profileArray.userId) {
    //       result.userId = profileArray.userId
    //     }else{
    //       result.userId = ''
    //     }
    //     if (profileArray.name) {
    //       result.name = profileArray.name
    //     }else{
    //       result.name = ''
    //     }
    //     if (profileArray.department) {
    //       result.department = profileArray.department
    //     }else {
    //       result.department = ''
    //     }
    //     if (profileArray.position) {
    //       result.position = profileArray.position
    //     }else {
    //       result.position = ''
    //     }
    //     return result
    //     })

    const state = reactive({

      form: {
        name: computed(() => store.getters['root/getProfile'].name),
        department: computed(() => store.getters['root/getProfile'].department),
        position: computed(() => store.getters['root/getProfile'].position),
        id: computed(() => store.getters['root/getProfile'].userId),
        align: 'left'
      },

      rules: {
        name: [
          { required: true, validator: validateName, trigger: 'blur' },
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
    })

    onMounted(() => {
    })

    const clickSave = function () {
      profileForm.value.validate((valid) => {
        if (valid) {
          store.commit('root/startSpinner')
          store.dispatch('root/requestUpdate', state.form.id, { name: state.form.name, department: state.form.department, position: state.form.position })
          .then(function () {
            alert('프로필 수정이 완료되었습니다.')
            emit('closeProfileDialog')
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

    const handleClose = function () {
      state.form.id = ''
      state.form.name = ''
      state.form.department = ''
      state.form.position = ''
      emit('closeProfileDialog')
    }

    return { profileForm, state, clickSave, handleClose, }
  }
}
</script>

<style>

</style>
