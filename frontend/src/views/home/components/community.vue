<template>
<div class="community">

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
    <div class="community-body" v-for="(post, index) in state.communityData" :key="index" style="overflow:auto">
      <button class="community-item" @click="clickPost(post)">
        <div class="community-title">
          {{ post.title }}
        </div>
        <div class="community-descript">
          {{ post.descript }}
        </div>
        <div class="community-information">
          {{ post.user.userId }} / <span>{{ post.time.slice(0, 16) }}</span> / {{ post.conferenceCategory.name }}
        </div>
      </button>
      <hr>
    </div>
  </div>
  <PostDialog
    :open="state.postDialogOpen"
    @closePostDialog="onClosePostDialog()"/>
</div>
</template>
<style>
.community {
  background-color: white;
  height:100%;
}
</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import PostDialog from '../../community/post-dialog'

export default {
  name: 'community',

  components: {
    PostDialog,
  },

  setup () {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      communityData: computed(() => store.getters['root/getCommunityData']),
      postDialogOpen:false,
    })


    onMounted(() => {
      store.dispatch('root/requestCommunity')
    })

    onUnmounted(() => {
    })

    const onOpenPostDialog = () => {
      state.postDialogOpen = true
    }

    const onClosePostDialog = () => {
      state.postDialogOpen = false
    }

    const clickPost = async function (post) {
      await store.dispatch('root/requestCommunityDetail', post.id)
      .then(() => {
        sessionStorage.setItem('postitem', JSON.stringify(post))
        router.push({
        name: 'post-detail',
        params: {
          postId: post.id,
          title: post.title,
          descript: post.descript,
          category: post.conferenceCategory.name,
          time: post.time,
          username: post.user.userId
        },
      })
      })
      // await router.push({
      //   name: 'post-detail',
      //   params: {
      //     postId: post.id,
      //     title: post.title,
      //     descript: post.descript,
      //     category: post.conferenceCategory.name,
      //     time: post.time,
      //     username: post.user.userId
      //   },
      // })
    }


    return { state, onOpenPostDialog, onClosePostDialog, clickPost }
  }
}
</script>

