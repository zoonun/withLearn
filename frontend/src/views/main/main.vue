<template>
  <el-container class="main-wrapper">
    <main-header
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog()"
      @openSignupDialog="onOpenSignupDialog()"
      @openConferenceDialog="onOpenConferenceDialog()"/>
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="240px">
        <main-sidebar
          :width="`240px`"/>
      </el-aside>
      <el-main>
        <router-view></router-view>
        <Spinner :loading="state.isSpinning"/>
      </el-main>
    </el-container>
    <main-footer :height="`110px`"/>
  </el-container>
  <login-dialog
    :open="state.loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog()"/>
  <signup-dialog
    :open="state.signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog()"/>
  <conference-dialog
    :open="state.conferenceDialogOpen"
    @closeConferenceDialog="onCloseConferenceDialog()"/>
</template>
<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import '../../assets/css/main.css';
  @import '../../assets/css/common.css';
  @import '../../assets/css/element-plus.css';
</style>
<script>
import LoginDialog from './components/login-dialog'
import SignupDialog from './components/signup-dialog'
import ConferenceDialog from './components/conference-dialog'
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import Spinner from './components/spinner'
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter,
    LoginDialog,
    SignupDialog,
    ConferenceDialog,
    Spinner
  },
  setup() {
    const store = useStore()

    const state = reactive({
      loginDialogOpen: false,
      signupDialogOpen: false,
      conferenceDialogOpen: false,
      isSpinning: computed(() => store.getters['root/getIsSpinning'])
    })

    const onOpenLoginDialog = () => {
      state.loginDialogOpen = true
    }
    const onCloseLoginDialog = () => {
      state.loginDialogOpen = false
    }
    const onOpenSignupDialog = () => {
      state.signupDialogOpen = true
    }
    const onCloseSignupDialog = () => {
      state.signupDialogOpen = false
    }
    const onOpenConferenceDialog = () => {
      state.conferenceDialogOpen = true
    }
    const onCloseConferenceDialog = () => {
      state.conferenceDialogOpen = false
    }

    return { state, onOpenLoginDialog, onCloseLoginDialog, onOpenSignupDialog, onCloseSignupDialog, onOpenConferenceDialog, onCloseConferenceDialog}
  }
}
</script>
