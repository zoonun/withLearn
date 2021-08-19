<template>
  <div class="main-wrapper">
    <MainHeader
      @openSearchDialog="onOpenSearchDialog()"
      @openLoginDialog="onOpenLoginDialog()"
      @openSignupDialog="onOpenSignupDialog()"
      @openConferenceDialog="onOpenConferenceDialog()"
      @openProfileDialog="onOpenProflieDialog()"/>
    <div class="main-container">
      <router-view></router-view>
      <Spinner :loading="state.isSpinning"/>
    </div>
    <MainFooter/>
  </div>
  <LoginDialog
    :open="state.loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog()"/>
  <SignupDialog
    :open="state.signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog()"/>
  <SearchDialog
    :open="state.searchDialogOpen"
    @closeSearchDialog="onCloseSearchDialog()"/>
  <ConferenceDialog
    :open="state.conferenceDialogOpen"
    @closeConferenceDialog="onCloseConferenceDialog()"/>
  <ProfileDialog
    :open="state.profileDialogOpen"
    @closeProfileDialog="onCloseProfileDialog()"/>
</template>
<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import '../../assets/css/common.css';
  @import '../../assets/css/button.css';
  @import '../../assets/css/effect.css';
  @import '../../assets/css/font.css';
  @import '../../assets/css/modal.css';
  @import '../../assets/css/search.css';
  @import '../../assets/css/community.css';
  @import '../../assets/css/detail.css';
  @import '../../assets/css/groupcall.css';
  @import '../../assets/css/card.css';
</style>
<script>
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import LoginDialog from '@/components/dialog/login-dialog'
import SignupDialog from '@/components/dialog/signup-dialog'
import SearchDialog from '@/components/dialog/search-dialog'
import ConferenceDialog from '@/components/dialog/conference-dialog'
import ProfileDialog from '@/components/dialog/profile-dialog'
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
      body.style.overflow = 'hidden'
      state.signupDialogOpen = true
    }
    const onCloseSignupDialog = () => {
      body.style.overflow = 'auto'
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
