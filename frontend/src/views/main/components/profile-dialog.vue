<template>
  <el-dialog custom-class="profile-dialog" title="프로필" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="profileForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" style="margin-left:10.55px;" >
        <el-input v-model="state.form.userId" disabled="true" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.name" autocomplete="off" style="margin-left:10.55px;"></el-input>
      </el-form-item>
      <el-form-item prop="department" label="소속" :label-width="state.formLabelWidth" style="margin-left:10.55px;">
        <el-input v-model="state.form.department" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="position" label="직책" :label-width="state.formLabelWidth" style="margin-left:10.55px;">
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
import Swal from 'sweetalert2'

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

    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length > 30) {
        callback(new Error('최대 30글자를 입력해야 합니다.'))
      } else {
        callback()
      }
    }

    const state = reactive({
      form: computed(() => store.getters['root/getProfile']),
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
          store.dispatch('root/requestUpdate', {id:state.form.userId, department:state.form.department, name:state.form.name, position:state.form.position})
          .then(function () {
            Swal.fire({
              icon: 'success',
              html: '저장되었습니다.',
              showConfirmButton: false,
              timer: 1000,
            })
            emit('closeProfileDialog')
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
          .finally(store.commit('root/endSpinner'))
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

    const handleClose = function () {
      emit('closeProfileDialog')
    }

    return { profileForm, state, clickSave, handleClose, }
  }
}
</script>

<style>
.el-dialog .el-form .el-form-item__label {
  text-align:left;
}

</style>
