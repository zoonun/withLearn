<template>
  <div class="chat-wrap" v-if="state.dialogVisible">
    <div class="chat-header">
      클래스 채팅
    </div>
    <div class="chat-body">
      <div class="chat-content" v-if="state.chatData" id="chat-content">
        <div v-for="(chatObj, idx) in state.chatData" :key="idx">
          <div class="chat-text chat-text-mine" v-if="chatObj.userId === userId">
            <div class="chat-text-sender">
              {{ chatObj.name }}
            </div>
            <div class="chat-text-message">
              {{ chatObj.chat }}
            </div>
          </div>
          <div class="chat-text chat-text-others" v-else>
            <div class="chat-text-sender">
              {{ chatObj.name }}
            </div>
            <div class="chat-text-message">
              {{ chatObj.chat }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="chat-footer">
      <textarea class="chat-input-area" rows="5" cols="10" placeholder="메세지를 입력해 주세요." maxlength="120" @keyup.enter="sendChat" v-model="state.chatStream">
      </textarea>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from '@vue/reactivity'
import { useStore } from 'vuex'
export default {
  name: 'chat-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    userId: {
      type: String
    }
  },
  setup(props, {emit}) {
    const store = useStore()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      chatStream: '',
      chatData: computed(() => store.getters['root/getReceivedChat'])
    })

    const sendChat = function () {
      emit('sendChat', state.chatStream)
      state.chatStream = ''
      const chatContent = document.getElementById('chat-content')
      chatContent.scrollTop = chatContent.scrollHeight
    }

    return { state, sendChat }
  }
}
</script>

<style>

</style>
