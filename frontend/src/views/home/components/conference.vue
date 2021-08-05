<template>
  <el-card :body-style="{ padding: '0px' }">
    <div class="image-wrapper">
      <el-skeleton style="width: 100%">
        <template #template>
          <el-skeleton-item variant="image" style="width: 100%; height: 190px" />
        </template>
      </el-skeleton>
      </div>
    <div class="content-wrapper" style="text-align: left; padding:14px;">
      <div class="header">
        <span class="title">{{ title }}</span>
        <div class="name">{{ name }}</div>
      </div>
      <div class="bottom">
        <span class='category'> {{ category }} </span>
        <span v-if="isactive" class='isactive'> 수강신청중 </span>
      </div>
    </div>
  </el-card>
</template>
<style>
.el-card {
  margin: 0 8px;
  margin-bottom: 40px;
}
.el-card .image-wrapper {
  width: 100%;
  height: 190px;
}
.el-card .title {
  font-weight: bold;
  width:100%;
  display:block;
  font-size:130%;
  margin-bottom: 5px;
}
.el-card .name {
  color: #7D7D7D;

}

.el-card .isactive {
  font-size:70%;
  display: block;
  background-color: greenyellow;
  padding:.75em;
  border-radius: 10px;
  margin-top:5px;
  float: right;
}
.el-card .bottom {
  margin-top: 20px;
  display:-webkit-box;
  word-wrap:break-word;
  -webkit-box-orient:vertical;
  overflow:hidden;
  text-overflow:ellipsis;
}
.el-card .category {
  font-size:70%;
  display: block;
  background-color: skyblue;
  padding:.75em;
  border-radius: 10px;
  margin-top:5px;
  float: left;
  color:white;
  font-weight: bold;
}

/* 테블릿, 모바일의 경우 두 줄 말줄임표시 */
@media (max-width: 1269px) {
  .el-card .bottom {
    -webkit-line-clamp: 2;
    height:42px;
  }
}
/* 데스크탑의 경우 세 줄 말줄임표시 */
@media (min-width: 1270px) {
  .el-card .bottom {
    -webkit-line-clamp: 3;
    height:60px;
  }
}

</style>
<script>
export default {
  name: 'Home',

  props: {
    title: {
      type: String,
      default: '제목'
    },
    name: {
      type: String,
      default: '백준'
    },
    isactive: {
      type: Boolean,
      default:true
    },
    category: {
      type: String,
      default: '알고리즘'
    }
  },

  setup () {

  }
}
</script>
