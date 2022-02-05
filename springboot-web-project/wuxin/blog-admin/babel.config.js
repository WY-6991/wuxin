module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  'env': {
    'development': {
      'plugins': ['dynamic-import-node']
    }
  },

  // plugins: [
  //   ["prismjs", {
  //     "languages":
  //       [
  //         "java",
  //         "log",
  //       ],
  //     "plugins": [
  //       "line-numbers",
  //       "copy-to-clipboard",
  //       "highlight-keywords",
  //       "jsonp-highlight",
  //     ], //配置显示行号插件
  //     "theme": "okaidia", //主体名称
  //     "css": true
  //   }]
  // ]
}
