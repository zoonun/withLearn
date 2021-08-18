<template>
  <div class="small-banner">
    <p class="small-banner-text">
      커뮤니티
    </p>
  </div>
  <div class="post-wrapper" >
    <div class="post-header">
      <div class="post-category">
        음악>
      </div>
      <div class="post-title">
        {{ title }}
      </div>
      <div class="post-user">
        <div class="user-icon">
          <img :src="state.images.icon" alt="유저 아이콘" style="height:50px;">
        </div>
        <div class="user-info">
          {{ username }}
          <p class="post-time">
            {{ createTime }}
          </p>
        </div>
      </div>
    </div>
    <hr>
    <div class="post-body">
      {{ descript }}
    </div>
    <hr>
    댓글>
    <div class="comment" v-for="idx in 3" :key="idx">
      <Comment/>
    </div>
    <div>
      <CreateComment
      :userId="username"/>
    </div>
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

</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
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
    },
    title: {
      type: String,
      default: '제목'
    },
    descript: {
      type: String,
      default: '내용'
    },
    category: {
      type: String,
      default: '음악'
    },
    username: {
      type: String,
      default: 'user'
    },
    createTime: {
      type: String,
      default: '2021.08.18 09:00'
    }
  },
  setup () {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      conferenceId: '',
      conferenceDetail: computed(() => store.getters['root/getConferenceDetail']),
      images: {
        icon: require('@/assets/images/user_icon.png')
      },
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
    })

    return { state }
  }
}
</script>

