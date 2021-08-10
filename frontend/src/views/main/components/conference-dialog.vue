<template>
  <div>

  </div>
</template>

<script>
import { reactive, ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

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
        conferenceCategoryId: '',
        description: '',
        thumbnail: null,
        align: 'left'
      },
      rules: {
        conferenceCategoryId: [
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
            formData.append('conferenceCategoryId', state.form.conferenceCategoryId)
            formData.append('description', state.form.description)
            formData.append('thumbnail', state.form.thumbnail)

            store.dispatch('root/requestConferenceCreate', formData)
            .then(function (res) {
              console.log('컨퍼런스 생성 결과 : ', res)
              emit('closeConferenceDialog')
                Swal.fire({
                icon: 'success',
                html: '컨퍼런스가 생성되었습니다.',
                showConfirmButton: false,
                timer:1000
              })
              setTimeout(function(){
                document.location.reload();
              }, 1000);
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
          }
        } else {
          Swal.fire({
              icon: 'error',
              html: '잘못된 입력입니다.',
              showConfirmButton: false,
              timer: 1000
            })
        }
      })
    }

    const fileSelect = function (event) {
      state.form.thumbnail = event.target.files[0]
    }

    const handleClose = function () {
      state.form.title = ''
      state.form.conferenceCategoryId = ''
      state.form.description = ''
      state.form.thumbnail = null
      emit('closeConferenceDialog')
    }

    return { conferenceForm, state, clickConference, handleClose, fileSelect }
  }
}
</script>

<style>
.swal2-container {
  z-index: 10000;
}

</style>
