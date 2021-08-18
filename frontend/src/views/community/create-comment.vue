<template>
  <div class="comment-box">
    <p>
      {{ userId }}
    </p>
    <input type="text" placeholder="댓글을 남겨보세요" class="comment-input" maxlength="50" v-model="state.comment">
    <div class="comment-bottom">
      <div class="comment-button" @click="clickCommentCreate">등록</div>
    </div>
  </div>
</template>

<style>
.comment-box {
  border: 3px solid rgba(0,0,0,0.1);
  height: 200px;
  padding:1.5rem;
  border-radius: 1rem;
}
.comment-input {
  /* border:0; */
  width:100%;
  overflow: hidden;
  height:80px;
  border:0;
}
.comment-bottom {
  font-size:1.8rem;
  display: flex;
  justify-content: flex-end;
  background-color: white;
  color:#b7b7b7;
  font-weight: bold;
  cursor: pointer;
}

</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'create-comment',

  props: {
    userId: {
      type: String,
      default: 'user'
    }
  },

  setup () {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      userInfo: computed(() => store.getters['root/getProfile']),
      images: {
        icon: require('@/assets/images/user_icon.png')
      },
      comment:null,
    })


    onMounted(() => {
    })

    onUnmounted(() => {
    })

    const clickCommentCreate = function () {
      const payload = state.comment
      store.dispatch('root/requestCommentCreate', payload)
    }


    return { state, clickCommentCreate }
  }
}
</script>
