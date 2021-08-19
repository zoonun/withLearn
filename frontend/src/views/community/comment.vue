<template>
  <div class="comment-wrapper">
    <div class="comment-head">
      <div class="user-icon">
        <img :src="state.images.icon" alt="유저 아이콘" style="height:40px;">
      </div>
      <div class="comment-user">
        {{ comment.user.userId }}
      </div>
    </div>
    <div class="comment-body">
      {{ comment.descript }}
      <p class="post-time" style="margin-top:1rem;">
      {{ comment.time.slice(0,16)}}
      </p>
    </div>
  </div>
  <hr>
</template>

<style>
.comment-head {
  display: flex;
}
.comment-user {
  margin-top:1rem;
  margin-left:1rem;
  font-size:1.5rem;
  font-weight: bold;

}
.comment-body {
  margin-left:45px;
  margin-top: 2rem;
}
.comment-delete {
  width:90%;
  text-align: end;
  color:#b7b7b7;
  cursor: pointer;
}

</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'comment',
  props:{
    comment:{
      type: Object
    }
  },

  setup (props) {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      images: {
        icon: require('@/assets/images/user_icon.png')
      },
    })


    onMounted(() => {
    })

    onUnmounted(() => {
    })

    const clickComment = () => {
    }


    return { state, clickComment }
  }
}
</script>
