// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: true,
    port: 80,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'https://i5d106.p.ssafy.io/'
      }
    },
    historyApiFallback: true,
    hot: true
  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },
  transpileDependencies: [
  ],
  lintOnSave: false,
  outputDir: '../frontend/homepage/dist'
}
