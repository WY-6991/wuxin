const webpack = require('webpack')
module.exports = {

  configureWebpack: {
    resolve: {
      alias: {
        'assets': '@/assets',
        'common': '@/common',
        'style': '@/style',
        'components': '@/components',
        'network': '@/network',
        'configs': '@/configs',
        'views': '@/views',
        'plugins': '@/plugins',
      }
    },

  },

  chainWebpack: config => {
    config.plugin('provide').use(webpack.ProvidePlugin, [{
      $: 'jquery',
      jquery: 'jquery',
      jQuery: 'jquery',
      'window.jQuery': 'jquery'
    }])
  }



}