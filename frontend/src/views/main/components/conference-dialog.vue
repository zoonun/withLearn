<template>
  <el-dialog title="컨퍼런스 생성하기" v-model="state.dialogVisible" @close="handleClose">
    <Spinner :loading="state.isSpinning"/>
    <el-form :model="state.form" :rules="state.rules" ref="conferenceForm" :label-position="state.form.align">
      <el-form-item prop="usage" label="용도" :label-width="state.formLabelWidth">
        <!-- ConferenceId API 통신으로 select 메뉴 구성 -->
        <el-select
          v-model="state.form.usage"
          placeholder="선택">
          <el-option
            v-for="item in state.conferenceOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            selected>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="title" label="제목" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.title" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="description" label="설명" :label-width="state.formLabelWidth">
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" v-model="state.form.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="thumbnail" label="썸네일" :label-width="state.formLabelWidth">
        <input
          type="file"
          @change="fileSelect()"
          accept=".jpg, .jpeg, .png, .gif"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickConference">생성</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, ref, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Spinner from './spinner'

export default {
  name: 'conference-dialog',
  components: {
    Spinner
  },
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const conferenceForm = ref(null)
    /*
      // Conference Validator
    */
    const validateThumbnail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else {
        console.log('thumb', value.type)
      }
    }

    const state = reactive({
      form: {
        title: '',
        usage: '',
        conferenceId: '',
        description: '',
        thumbnail: '',
        align: 'left'
      },
      rules: {
        usage: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          { max: 30, message: '최대 30글자를 입력해야 합니다.', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' }
        ],
        thumbnail: [
          { required: true, validator: validateThumbnail, trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      isSpinning: false,
      // conference API 통신으로 받아야 함
      conferenceOptions: [
        {
          value: 'option1',
          label: 'label1'
        },
        {
          value: 'option2',
          label: 'label2'
        }
      ]
    })

    const clickConference = function () {
      conferenceForm.value.validate((valid) => {
        if (valid) {
          store.commit('root/startSpinner')

          const formData = new FormData()
          formData.append('title', state.form.title)
          formData.append('conferenceId', state.form.conferenceId)
          formData.append('description', state.form.description)
          formData.append('thumbnail', state.form.thumbnail)

          // FormData 객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
          // FormData를 로깅하려면 FormData.entries()를 이용하여 key : value 쌍을 뽑아야 한다.
          for (let key of formData.entries()) {
            console.log(`${key}`)
          }

          store.dispatch('root/requestConference', formData)
          .then(function (res) {
            console.log('컨퍼런스 생성 결과 : ', res)
            emit('closeConferenceDialog')
            router.push({
              name: 'conference-detail',
              params: {
                conferenceId: res.id
              }
            })
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

    const fileSelect = function (e) {
      state.form.thumbnail =
      console.log(e)
    }

    const handleClose = function () {
      state.form.title = ''
      state.form.usage = ''
      state.form.description = ''
      state.form.thumbnail = ''
      emit('closeConferenceDialog')
    }

    return { conferenceForm, state, clickConference, handleClose, fileSelect }
  }
}
</script>

<style>

</style>
