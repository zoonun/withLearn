<template>
  <el-card :body-style="{ padding: '0px' }">
    <div class="image-wrapper">
      <el-skeleton style="width: 100%">
        <template #template>
          <el-skeleton-item variant="image" style="width: 100%; height: 190px" />
        </template>
      </el-skeleton>
      </div>
    <div style="text-align: left; padding: 14px;">
      <div class="header">
        <span class="title">{{ title }}</span>
        <span class="date"> {{ date }}</span>
        <div class="category">{{ name }}</div>
      </div>
      <div class="bottom">
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
}
.el-card .bottom {
  margin-top: 5px;
  display:-webkit-box;
  word-wrap:break-word;
  -webkit-box-orient:vertical;
  overflow:hidden;
  text-overflow:ellipsis;
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
.el-card .header .date {
  float:right;
  margin-right:5%;
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
    desc: {
      type: String,
      default: 'Lrsions of Lorem Ipsum.'
    },
    name: {
      type: String,
      default: '백준'
    },
    date: {
      type: String,
      default: '2021-08-05 ~ 2021-09-30'
    },
    isactive: {
      type: Boolean,
      default:true
    }
  },

  setup () {

  }
}
</script>
