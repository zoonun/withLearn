<template>
<div class="post">
  <div class="small-banner">
    <p class="small-banner-text">
      커뮤니티
    </p>
  </div>
  <div class="post-wrapper" >
    <div class="post-header">
      <div class="post-category">
        {{ state.postitem.conferenceCategory.name }}>
      </div>
      <div class="post-title">
        {{ state.postitem.title }}
      </div>
      <div class="post-user">
        <div class="user-icon">
          <img :src="state.images.icon" alt="유저 아이콘" style="height:50px;">
        </div>
        <div class="user-info">
          {{ state.postitem.user.userId }}
          <p class="post-time">
            {{ state.postitem.time.slice(0,16) }}
          </p>
        </div>
      </div>
    </div>
    <hr>
    <div class="post-body">
      {{ state.postitem.descript }}
    </div>
    <hr>
    댓글>
    <div class="comment" v-for="(comment, idx) in state.commentList" :key="idx">
      <Comment
      :comment="comment"
      />
    </div>
    <div class="create-comment">
      <CreateComment
      :postId="postId"
      :userId="username"/>
    </div>
  </div>
  <hr>
  </div>
</template>
<style>
.post-wrapper {
  display: flex;
  margin-right:30rem;
  margin-left:30rem;
  font-size: 2.25rem;
  margin-top:8rem;
  flex-direction: column;
}
.user-iocn {
  height:30px;
}
.post-title {
  font-weight:bold;
  font-size:5rem;
  margin-bottom:3rem;
}
.post-time {
  color:#979797;
  font-size:1.5rem;
}
.post-body {
  margin-top:3rem;
  margin-bottom:3rem;
  min-height: 250px;
}
.post-user {
  font-size:2rem;
  display: flex;
}
.user-info {
  margin-left:1rem;
}
.comment {
  margin-top:3rem;
  margin-bottom:3rem;
  font-size:2rem;
}
.post-category {
  color:#1dc078;
}
.post-update-btn {
  font-size: 1.8rem;
  font-weight: bold;
  min-width: 90px;
  margin: 0 5px;
  padding: 5px;
  border-radius: 5px;
  background-color: #1dc078;
  border: 0;
  color:white;
}
.post-delete-btn {
  font-size: 1.8rem;
  font-weight: bold;
  min-width: 90px;
  margin: 0 5px;
  padding: 5px;
  border-radius: 5px;
  background-color: var(--error-color);
  border: 0;
  color:white;
}
.post-bottom {
  margin-top:1rem;
}
.post {
  background-color: white;
}
.create-comment {
  margin-bottom:2rem;
}
</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Comment from './comment'
import CreateComment from './create-comment'

export default {
  name: 'post-detail',

  components: {
    Comment,
    CreateComment
  },

  props: {
    postId: {
      type: String
    }
  },
  setup (props) {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      images: {
        icon: require('@/assets/images/user_icon.png')
      },
      commentList: computed(() => store.getters['root/getCommentList']),
      // postitem: computed(() => store.getters['root/getCommunityDetail'])
      postitem:computed(() => JSON.parse(sessionStorage.getItem('postitem')))
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      store.dispatch('root/requestCommentList', props.postId)
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      sessionStorage.clear()
    })

    return { state }
  }
}
</script>

