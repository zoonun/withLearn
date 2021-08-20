<template>
  <div id="notfound">
    <div class="notfound">
      <div class="notfound-404">
      <h1>404</h1>
      </div>
      <h2>존재하지 않는 페이지입니다.</h2>
      <p>요청하신 페이지가 사라졌거나, 잘못된 경로를 입력하셨습니다. <a href="/">홈으로 돌아가기</a></p>
      <div class="notfound-social">
      <a href="#"><i class="fa fa-facebook"></i></a>
      <a href="#"><i class="fa fa-twitter"></i></a>
      <a href="#"><i class="fa fa-pinterest"></i></a>
      <a href="#"><i class="fa fa-google-plus"></i></a>
      </div>
    </div>
  </div>
</template>

  <script>
export default {

}
</script>

<style scoped>
@font-face {
  font-family: 'Kanit';
  font-style: normal;
  font-weight: 200;
  src: url(/fonts.gstatic.com/s/kanit/v7/nKKU-Go6G5tXcr5aOhWzVaF5NQ.woff2) format('woff2');
  unicode-range: U+0E01-0E5B, U+200C-200D, U+25CC;
}
/* vietnamese */
@font-face {
  font-family: 'Kanit';
  font-style: normal;
  font-weight: 200;
  src: url(/fonts.gstatic.com/s/kanit/v7/nKKU-Go6G5tXcr5aOhWoVaF5NQ.woff2) format('woff2');
  unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
}
/* latin-ext */
@font-face {
  font-family: 'Kanit';
  font-style: normal;
  font-weight: 200;
  src: url(/fonts.gstatic.com/s/kanit/v7/nKKU-Go6G5tXcr5aOhWpVaF5NQ.woff2) format('woff2');
  unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Kanit';
  font-style: normal;
  font-weight: 200;
  src: url(/fonts.gstatic.com/s/kanit/v7/nKKU-Go6G5tXcr5aOhWnVaE.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

* {
  -webkit-box-sizing: border-box;
          box-sizing: border-box;
}

body {
  padding: 0;
  margin: 0;
}

#notfound {
  height: 100vh;
}

#notfound .notfound {
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
      -ms-transform: translate(-50%, -50%);
          transform: translate(-50%, -50%);
}

.notfound {
  max-width: 767px;
  width: 100%;
  line-height: 1.4;
  text-align: center;
  padding: 15px;
}

.notfound .notfound-404 {
  position: relative;
  height: 220px;
}

.notfound .notfound-404 h1 {
  font-family: 'Kanit', sans-serif;
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
      -ms-transform: translate(-50%, -50%);
          transform: translate(-50%, -50%);
  font-size: 186px;
  font-weight: 200;
  margin: 0px;
  background: linear-gradient(130deg, #ffa34f, #ff6f68);
  color:transparent;
  -webkit-background-clip: text;
  background-clip: text;
  text-transform: uppercase;
}

.notfound h2 {
  font-family: 'Kanit', sans-serif;
  font-size: 33px;
  font-weight: 200;
  text-transform: uppercase;
  margin-top: 0px;
  margin-bottom: 25px;
  letter-spacing: 3px;
}


.notfound p {
  font-family: 'Kanit', sans-serif;
  font-size: 16px;
  font-weight: 200;
  margin-top: 0px;
  margin-bottom: 25px;
}


.notfound a {
  font-family: 'Kanit', sans-serif;
  color: #ff6f68;
  font-weight: 200;
  text-decoration: none;
  border-bottom: 1px dashed #ff6f68;
  border-radius: 2px;
}

.notfound-social>a {
  display: inline-block;
  height: 40px;
  line-height: 40px;
  width: 40px;
  font-size: 14px;
  color: #ff6f68;
  border: 1px solid #efefef;
  border-radius: 50%;
  margin: 3px;
  -webkit-transition: 0.2s all;
  transition: 0.2s all;
}
.notfound-social>a:hover {
  color: #fff;
  background-color: #ff6f68;
  border-color: #ff6f68;
}

@media only screen and (max-width: 480px) {
  .notfound .notfound-404 {
    position: relative;
    height: 168px;
  }

  .notfound .notfound-404 h1 {
    font-size: 142px;
  }

  .notfound h2 {
    font-size: 22px;
  }
}
</style>
