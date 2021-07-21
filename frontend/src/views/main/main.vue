<template>
  <el-container class="main-wrapper">
    <main-header
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog()"
      @openSignupDialog="onOpenSignupDialog()"
      @openProfileDialog="onOpenProflieDialog()"/>
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="240px">
        <main-sidebar
          :width="`240px`"/>
      </el-aside>
      <el-main>
        <router-view></router-view>
        <spinner :loading="state.isSpinning"/>
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
  <profile-dialog
    :open="state.profileDialogOpen"
    @closeProfileDialog="onCloseProfileDialog()"/>
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
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import Spinner from './components/spinner'
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import ProfileDialog from './components/profile-dialog'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter,
    LoginDialog,
    SignupDialog,
    ProfileDialog,
    Spinner,
  },
  setup() {
    const store = useStore()

    const state = reactive({
      loginDialogOpen: false,
      signupDialogOpen: false,
      profileDialogOpen: false,
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
    const onOpenProflieDialog = () => {
      state.profileDialogOpen = true
    }
    const onCloseProfileDialog = () => {
      state.profileDialogOpen = false
    }

    return { state, onOpenLoginDialog, onCloseLoginDialog, onOpenSignupDialog, onCloseSignupDialog, onOpenProflieDialog, onCloseProfileDialog}
  }
}
</script>
