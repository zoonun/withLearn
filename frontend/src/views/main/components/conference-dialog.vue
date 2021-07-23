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
        <el-input type="file" @change="fileSelect(this, 'thumbnailPreview')" accept=".jpg, .jpeg, .png, .gif" v-model="state.form.thumbnail"/>
        <!-- <el-upload
          class="upload-demo"
          ref="upload"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          list-type="picture"
          accept=".jpg, .jpeg, .png, .gif">
          <template #trigger>
            <el-button size="small" type="primary">찾아보기</el-button>
          </template>
          <el-button
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="uploadThumbnail"
            >업로드
          </el-button>
          <template #tip>
            <div class="el-upload__tip">
              png/jpg/jpeg/gif 파일만 업로드 가능합니다.
            </div>
          </template>
        </el-upload> -->
      </el-form-item>
      <img src="about:black" name="thumbnailPreview" alt="">
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
      }
    }

    const state = reactive({
      form: {
        title: '',
        usage: '',
        conferenceId: '',
        description: '',
        thumbnail: null,
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

    onMounted(() => {

    })

    const clickConference = function () {
      conferenceForm.value.validate((valid) => {
        if (valid) {
          state.isSpinning = true
          store.dispatch('root/requestConference', state.form)
          .then(function (res) {
            console.log(res)
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
          .finally(state.isSpinning = false)
        } else {
          alert('잘못된 입력입니다.')
        }
      })
    }

    const fileSelect = function () {
      console.log(conferenceForm.value.model.thumbnail)
      const render = new FileReader()
      render.onload = function (e) {
        $('#' + )
      }
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
