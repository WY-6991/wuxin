const webpack = require('webpack')
module.exports = {

  // configureWebpack: {
  //   resolve: {
  //     alias: {
  //       'assets': '@/assets',
  //       'common': '@/common',
  //       'style': '@/style',
  //       'components': '@/components',
  //       'network': '@/network',
  //       'configs': '@/configs',
  //       'views': '@/views',
  //       'plugins': '@/plugins',
  //     }
  //   },
  //
  // },
  configureWebpack: {
    name: name,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  },





}