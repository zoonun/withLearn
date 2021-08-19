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
      <span class="conference-card-badge-health" v-if="conference.conferenceCategory.name === '헬스'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-music" v-else-if="conference.conferenceCategory.name === '노래'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-cook" v-else-if="conference.conferenceCategory.name === '요리'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-art" v-else-if="conference.conferenceCategory.name === '그림'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-game" v-else-if="conference.conferenceCategory.name === '게임'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-magic" v-else-if="conference.conferenceCategory.name === '마술'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-instrument" v-else-if="conference.conferenceCategory.name === '악기'">
        {{ conference.conferenceCategory.name }}</span>
      <span class="conference-card-badge-algo" v-else> {{ conference.conferenceCategory.name }}</span>

      <div class="conference-card-price">{{ conference.price }}원</div>
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
