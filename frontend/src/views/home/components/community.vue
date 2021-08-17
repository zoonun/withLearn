<template>
  <div class="small-banner">
    <p class="small-banner-text">
      커뮤니티
    </p>
  </div>
  <div class="community-container">
    <div class="community-header">
      <button class="btn" style="background-color:var(--indigo-color); color:white;" @click="onOpenPostDialog">
        <i class="el-icon-edit-outline"></i>
        글쓰기
      </button>
    </div>
    <hr>
    <div class="community-body" v-for="(item, index) in 5" :key="index" style="overflow:auto">
      <button class="community-item">
        <div class="community-title">
          제목
        </div>
        <div class="community-descript">
          내용
        </div>
        <div class="community-information">
          작성자 / 시간 / 카테고리
        </div>
      </button>
      <hr>
    </div>
  </div>
  <PostDialog
    :open="state.postDialogOpen"
    @closePostDialog="onClosePostDialog()"/>
</template>
<style>

</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import PostDialog from '../../community/post-dialog'

export default {
  name: 'community',

  components: {
    PostDialog,
  },

  setup (props, { emit }) {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      communityData: computed(() => store.getters['root/getCommunityData']),
      postDialogOpen:false,
    })


    onMounted(() => {

    })

    onUnmounted(() => {
    })

    const onOpenPostDialog = () => {
      state.postDialogOpen = true
    }

    const onClosePostDialog = () => {
      state.postDialogOpen = false
    }
    return { state, onOpenPostDialog, onClosePostDialog }
  }
}
</script>

