<template>
  <container class="main-wrapper">
    <MainHeader
      @openSearchDialog="onOpenSearchDialog()"
      @openLoginDialog="onOpenLoginDialog()"
      @openSignupDialog="onOpenSignupDialog()"
      @openConferenceDialog="onOpenConferenceDialog()"
      @openProfileDialog="onOpenProflieDialog()"/>
    <container class="main-container">
      <router-view></router-view>
      <Spinner :loading="state.isSpinning"/>
    </container>
    <MainFooter/>
  </container>
  <LoginDialog
    :open="state.loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog()"/>
  <signup-dialog
    :open="state.signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog()"/>
  <search-dialog
    :open="state.searchDialogOpen"
    @closeSearchDialog="onCloseSearchDialog()"/>
  <conference-dialog
    :open="state.conferenceDialogOpen"
    @closeConferenceDialog="onCloseConferenceDialog()"/>
  <profile-dialog
    :open="state.profileDialogOpen"
    @closeProfileDialog="onCloseProfileDialog()"/>
</template>
<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import '../../assets/css/common.css';
  @import '../../assets/css/tailwind.css';
  @import '../../assets/css/button.css';
  @import '../../assets/css/effect.css';
  @import '../../assets/css/font.css';
  @import '../../assets/css/modal.css';
</style>
<script>
import LoginDialog from './components/login-dialog'
import SignupDialog from './components/signup-dialog'
import ConferenceDialog from './components/conference-dialog'
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import Spinner from './components/spinner'
import SearchDialog from './components/search-dialog'
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
    SearchDialog,
    ConferenceDialog,
    ProfileDialog,
    Spinner
  },
  setup() {
    const store = useStore()

    const state = reactive({
      loginDialogOpen: false,
      signupDialogOpen: false,
      searchDialogOpen: false,
      conferenceDialogOpen: false,
      profileDialogOpen: false,
      isSpinning: computed(() => store.getters['root/getIsSpinning'])
    })

    const body = document.querySelector('body')

    const onOpenLoginDialog = () => {
      body.style.overflow = 'hidden'
      state.loginDialogOpen = true
    }
    const onCloseLoginDialog = () => {
      body.style.overflow = 'auto'
      state.loginDialogOpen = false
    }
    const onOpenSignupDialog = () => {
      state.signupDialogOpen = true
    }
    const onCloseSignupDialog = () => {
      state.signupDialogOpen = false
    }
    const onOpenSearchDialog = () => {
      state.searchDialogOpen = true
    }
    const onCloseSearchDialog = () => {
      state.searchDialogOpen = false
    }
    const onOpenConferenceDialog = () => {
      state.conferenceDialogOpen = true
    }
    const onCloseConferenceDialog = () => {
      state.conferenceDialogOpen = false
    }
    const onOpenProflieDialog = () => {
      state.profileDialogOpen = true
    }
    const onCloseProfileDialog = () => {
      state.profileDialogOpen = false
    }

    return { state, onOpenLoginDialog, onCloseLoginDialog, onOpenSignupDialog, onCloseSignupDialog, onOpenProflieDialog, onCloseProfileDialog, onOpenSearchDialog, onCloseSearchDialog, onOpenConferenceDialog, onCloseConferenceDialog}
  }
}
</script>
