<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container" @keyup.esc="handleClose" style="width: 960px">
      <div class="modal-header">
        강의 개설하기
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
      <hr class="modal-hr">
      <br>
      <Form @submit="clickConference" :validation-schema="schema" class="modal-body">
        <select v-model="state.form.conferenceCategoryId" class="">
          <option value="" selected disabled hidden>카테고리</option>
          <option v-for="(item, idx) in state.conferenceIds" :key="idx" :value="item.id"> {{ item.name }}</option>
        </select>
        <TextInput
          name="title"
          type="text"
          v-model="state.form.title"
          placeholder="강의 제목"
        />
        <TextInput
          name="description"
          type="text"
          v-model="state.form.description"
          placeholder="강의 설명"
        />
        <div class="modal-group">
          <label for="input-thumbnail" class="label-modal-thumbnail">
            썸네일 선택
          </label>
          <input
          type="file"
          id="input-thumbnail"
          style="display:none"
          @change="fileSelect"/>
          <button class="btn btn-submit" type="submit" style="width: 308px; margin: 10 0;">개설하기</button>
        </div>
        <p class="modal-thumbnail-selected-title" v-if="state.form.thumbnail">
          파일명: {{ state.form.thumbnail.name }}
        </p>
        <p class="modal-thumbnail-selected-title" v-else>
          썸네일을 위한 사진 파일을 선택해 주세요.
        </p>
      </Form>
    </div>
  </div>
</template>

<script>
import { reactive, ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'
import { Form } from 'vee-validate'
import * as Yup from 'yup'
import TextInput from '@/components/TextInput'

export default {
  name: 'post-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    width: {
      type: Number,
      default: 960
    }
  },
  components: {
    TextInput,
    Form,
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const conferenceForm = ref(null)

    const state = reactive({
      form: {
        title: '',
        conferenceCategoryId: '',
        description: '',
        thumbnail: null,
        align: 'left'
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      isSpinning: false,
      conferenceIds: computed(() => store.getters['root/getConferenceId'])
    })

    let schema = Yup.object().shape({
      title: Yup
        .string()
        .required('필수 입력 항목입니다.'),
      description: Yup
        .string()
        .required('필수 입력 항목입니다.'),
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
      if (thumbnailValidate(state.form.thumbnail)) {

        store.commit('root/setSpinnerStart')
        const formData = new FormData()
        formData.append('title', state.form.title)
        formData.append('conferenceCategoryId', state.form.conferenceCategoryId)
        formData.append('description', state.form.description)
        formData.append('thumbnail', state.form.thumbnail)
        console.log(state.form)
      //   store.dispatch('root/requestConferenceCreate', formData)
      //   .then(function (res) {
      //     console.log('컨퍼런스 생성 결과 : ', res)
      //       Swal.fire({
      //       icon: 'success',
      //       html: '컨퍼런스가 생성되었습니다.',
      //       showConfirmButton: false,
      //       timer:1000
      //     })
      //     setTimeout(function(){
      //       emit('closeConferenceDialog')
      //       router.push({
      //         name: 'conference-detail',
      //         params: {
      //           conferenceId: res.data.conferenceId
      //         }
      //       })
      //     }, 1000);
      //   })
      //   .catch(function (err) {
      //     Swal.fire({
      //     icon: 'error',
      //     html: err.response.data.message,
      //     showConfirmButton: false,
      //     timer: 1000
      //     })
      //   })
      //   .finally(store.commit('root/setSpinnerEnd'))
      }
    }

    const fileSelect = function (event) {
      state.form.thumbnail = event.target.files[0]
    }

    const handleClose = function () {
      emit('closeConferenceDialog')
    }

    return { conferenceForm, state, clickConference, handleClose, fileSelect, schema }
  }
}
</script>
