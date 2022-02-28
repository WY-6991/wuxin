export const repo = {
  data() {
    return {
      user: {
        token: '',
        avatar: '',
        username: ''
      },
      disabled: true,
      key: 'user_repo_token',
      isSetting: false
    }
  },

  created() {
    // 初始化用户仓库信息
    const user = this.getData()
    if (user && user.token && user.username && user.avatar) {
      this.user = user
      this.isSetting = true
    }
  },

  methods: {

    getData() {
      return JSON.parse(window.localStorage.getItem(this.key))
    }
  }
}
