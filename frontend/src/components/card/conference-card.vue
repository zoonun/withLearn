<template>
  <div class="conference-card">
    <div class="conference-card-header">
      <img :src="'https://i5d106.p.ssafy.io/' + conference.thumbnail" alt="" class="conference-card-thumbnail">
    </div>
    <div class="conference-card-body">
      <div class="conference-card-title">{{ conference.title }}</div>
      <div class="conference-card-description">{{ conference.description }}</div>
    </div>
    <div class="conference-card-footer">
      <span class="conference-card-badge-health" v-if="conference.conferenceCategory.name === '헬스'"> {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-music" v-else-if="conference.conferenceCategory.name === '음악'"> {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-algo" v-else> {{ conference.conferenceCategory.name }}</span>
    </div>
  </div>

</template>
<script>
export default {
  name: 'conference-card',

  props: {
    conference: {
      type: Object
    }
  },
  setup () {
  }
}
</script>
