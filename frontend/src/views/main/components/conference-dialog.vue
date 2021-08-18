<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container" @keyup.esc="handleClose" style="width: 960px">
      <div class="modal-header">
        글 쓰기
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
          placeholder="제목"
        />
        <TextInput
          name="description"
          type="text"
          v-model="state.form.description"
          placeholder="내용"
        />
        <div class="modal-group">
          <button class="btn btn-submit" type="submit" style="width: 308px; margin: 10 0;">작성하기</button>
        </div>
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
    const postForm = ref(null)

    const state = reactive({
      form: {
        title: '',
        conferenceCategoryId: '',
        description: '',
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

    onMounted(() => {
      store.dispatch('root/requestConferenceId')
    })

    const clickConference = function () {

        store.commit('root/setSpinnerStart')
        const formData = new FormData()
        formData.append('title', state.form.title)
        formData.append('descript', state.form.description)
        formData.append('conferenceCategoryid', state.form.conferenceCategoryId)
        console.log(state.form)
        // store.dispatch('root/requestCommunityCreate', formData)
        // .then(function (res) {
        //     Swal.fire({
        //     icon: 'success',
        //     html: '게시글이 생성되었습니다.',
        //     showConfirmButton: false,
        //     timer:1000
        //   })
        //   setTimeout(function(){
        //     document.location.reload();
        //   }, 1000);
        // })
        // .catch(function (err) {
        //   Swal.fire({
        //   icon: 'error',
        //   html: err.response.data.message,
        //   showConfirmButton: false,
        //   timer: 1000
        //   })
        // })
        // .finally(store.commit('root/setSpinnerEnd'))
    }

    const fileSelect = function (event) {
      state.form.thumbnail = event.target.files[0]
    }

    const handleClose = function () {
      emit('closePostDialog')
    }

    return { postForm, state, clickConference, handleClose, fileSelect, schema }
  }
}
</script>
