<template>
  <div class="card">
    <div>
      <img :src="'https://i5d106.p.ssafy.io/' + conference.thumbnail" alt="" class="thumbnail">
    </div>
    <div class="text-wrapper" style="text-align: left; padding: 14px;">
      <div class="top">
        <span class="title">{{ conference.title }}</span>
        <div class="name" v-if="conference.user.name">{{ conference.user.name }}</div>

      </div>
      <div class="bottom">
        <span class='category'> {{ conference.conferenceCategory.name }} </span>
        <span v-if="conference.is-active" class='isactive'> 수강신청중 </span>
        <span v-else class='unactive'> 수강마감 </span>
      </div>
    </div>

  </div>


</template>
<style>
 .thumbnail {
   background-size: cover;
   width: 100%;
   height: 250px;
   cursor: pointer;
 }
 .card {
  font-size: 1.8rem;
  margin: 0 8px;
  margin-bottom: 40px;
  border-radius: 0.25rem;
 }
  .card .title {
  font-weight: bold;
  width:100%;
  display:block;
  font-size:130%;
  margin-bottom: 5px;
}
.bottom {
  margin-top:5rem;
  display: flex;
  justify-content: space-between;
}
.isactive {
  font-size:70%;
  display: block;
  background-color: rgb(7, 248, 47);
  padding:.75em;
  border-radius: 10px;
  margin-top:5px;
  float: right;
  color:  white;
  font-weight: bold;
}
.unactive {
  font-size:70%;
  display: block;
  background-color: red;
  padding:.75em;
  border-radius: 10px;
  margin-top:5px;
  float: right;
  color:white;
  font-weight: bold;
}
.text-wrapper .top {
  height: 4rem;
}
.category {
  font-size:70%;
  display: block;
  background-color: skyblue;
  padding:.75em;
  border-radius: 10px;
  margin-top:5px;
  float: left;
  color:white;
  font-weight: bold;
  min-width: 40px;
  text-align: center;
}



</style>
<script>
export default {
  name: 'Home',

  props: {
    conference: {
      type: Object
    }
  },

  setup () {

  }
}
</script>
