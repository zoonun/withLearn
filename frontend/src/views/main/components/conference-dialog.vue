<template>
  <el-dialog title="컨퍼런스 생성하기" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="conferenceForm" :label-position="state.form.align">
      <el-form-item prop="conferenceCategory" label="용도" :label-width="state.formLabelWidth">
        <el-select
          v-model="state.form.conferenceCategory"
          allow-create
          filterable
          default-first-option
          clearable
          placeholder="선택">
          <el-option
            v-for="item in state.conferenceIds"
            :key="item.id"
            :label="item.name"
            :value="{id: item.id, name: item.name}">
          </el-option>
        </el-select>
        <div>
          <el-button-group>
            <el-button size="small" type="primary" icon="el-icon-edit" @click="createConferenceId">카테고리 생성</el-button>
            <el-button size="small" type="danger" icon="el-icon-delete" @click="deleteConferenceId">카테고리 삭제</el-button>
          </el-button-group>
        </div>
      </el-form-item>
      <el-form-item prop="title" label="제목" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.title" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="description" label="설명" :label-width="state.formLabelWidth">
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" v-model="state.form.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="썸네일" :label-width="state.formLabelWidth">
        <input
          type="file"
          @change="fileSelect"/>
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
import { reactive, ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'conference-dialog',
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

    const state = reactive({
      form: {
        title: '',
        conferenceCategory: null,
        description: '',
        thumbnail: null,
        align: 'left'
      },
      rules: {
        conferenceCategory: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          { max: 30, message: '최대 30글자를 입력해야 합니다.', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      isSpinning: false,
      conferenceIds: computed(() => store.getters['root/getConferenceId'])
    })

    const thumbnailRegExp = /.*\.(jpg|jpeg|png|gif)$/
    const maxSize = 5 * 1024 * 1024

    const thumbnailValidate = function (value) {
      if (value === null) {
        alert('첨부파일은 필수 항목입니다.')
        return
      } else {
        let thumbnailSize = value.size

        if (!thumbnailRegExp.test(value.name)) {
          alert('이미지 파일만 업로드 가능합니다.')
          return state.form.thumbnail = null
        } else if (thumbnailSize > maxSize) {
          alert('5MB 이하의 파일만 업로드 가능합니다.')
          return state.form.thumbnail = null
        } else {
          return true
        }
      }
    }

    onMounted(() => {
      store.dispatch('root/requestConferenceId')
    })

    const clickConference = function () {
      conferenceForm.value.validate((valid) => {
        if (valid) {
          if (thumbnailValidate(state.form.thumbnail)) {
            store.commit('root/setSpinnerStart')

            const formData = new FormData()
            formData.append('title', state.form.title)
            formData.append('conferenceId', state.form.conferenceCategory.id)
            formData.append('description', state.form.description)
            formData.append('thumbnail', state.form.thumbnail)

            store.dispatch('root/requestConferenceCreate', formData)
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
            .finally(store.commit('root/setSpinnerEnd'))
          }
        } else {
          alert('잘못된 입력입니다.')
        }
      })
    }

    const fileSelect = function (event) {
      state.form.thumbnail = event.target.files[0]
    }

    const createConferenceId = function () {
      const newCategory = state.form.conferenceCategory
      store.dispatch('root/requestConferenceIdCreate', {'name': newCategory})
      .then(() => {
        document.location.reload()
        alert(newCategory + '카테고리가 추가되었습니다.')
      })
      .catch(err => {
        console.log(err)
      })
    }

    const deleteConferenceId = function () {
      const curCategory = state.form.conferenceCategory.name
      store.dispatch('root/requestConferenceIdDelete', {'name': curCategory})
      .then(() => {
        document.location.reload()
        alert(curCategory + '카테고리가 삭제되었습니다.')
      })
      .catch(err => {
        console.log(err)
      })
    }

    const handleClose = function () {
      state.form.title = ''
      state.form.usage = ''
      state.form.description = ''
      state.form.thumbnail = null
      emit('closeConferenceDialog')
    }

    return { conferenceForm, state, clickConference, handleClose, fileSelect, createConferenceId, deleteConferenceId }
  }
}
</script>

<style>

</style>
