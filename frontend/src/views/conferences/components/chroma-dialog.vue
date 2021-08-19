<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container" @keyup.esc="handleClose" style="width: 600px; height: 600px;">
      <div class="modal-header">
        가상배경 변경
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
        <form @submit="addChroma" class="modal-body">
        <div class="chroma-list container" v-if="state.chromaList">
          <div class="chroma-list-item col-2">
            <button @click="selectChroma('')">가상배경 없애기</button>
          </div>
          <div class="row" v-for="(item, idx) in state.chromaList" :key="idx">
            <div class="chroma-list-item col-2" @click="selectChroma(item.imagePath)">
              <img :src="`https://i5d106.p.ssafy.io/${item.imagePath}`" alt="chroma">
              <span class="chroma-list-text">{{ item.imageName }}</span>
            </div>
          </div>
        </div>
          <div class="modal-group">
            <label for="input-chroma" class="label-modal-thumbnail">
              가상 배경 이미지 선택
            </label>
            <input
            type="file"
            id="input-chroma"
            style="display:none"
            @change="fileSelect"/>
            <button class="btn btn-submit" type="submit" style="width: 100px">제출</button>
          </div>
          <p class="modal-thumbnail-selected-title" v-if="state.chroma.newImage">
            파일명: {{ state.chroma.newImage.name }}
          </p>
          <p class="modal-thumbnail-selected-title" v-else>
            가상 배경을 위한 사진 파일을 선택해 주세요.
          </p>
        </form>
      </div>
    <!-- </div> -->
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'chroma-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    userId: {
      type: String,
      default: ''
    }
  },
  setup(props, {emit}) {
    const store = useStore()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      chromaList: computed(() => store.getters['root/getChromaList']),
      chroma: {
        newImage: null
      }
    })

    onMounted(() => {
      store.dispatch('root/requestChromaList', props.userId)
    })

    const handleClose = function () {
      emit('closeChromaDialog')
    }

    const fileSelect = function (event) {
      state.chroma.newImage = event.target.files[0]
    }

    const addChroma = function () {
      const fd = new FormData()
      fd.append('image', state.chroma.newImage)
      fd.append('userId', props.userId)
      store.dispatch('root/requestAddChromaImage', fd)
      .then((res) => console.log(res))
      .catch((err) => console.log(err))
    }

    const selectChroma = function (imagePath) {
      emit('changeChroma', `${imagePath}`)
      emit('closeChromaDialog')
    }

    return { state, handleClose, addChroma, fileSelect, selectChroma }
  }
}
</script>

<style>

</style>
