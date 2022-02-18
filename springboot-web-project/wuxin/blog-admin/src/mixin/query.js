export const query = {
  data() {
    return {
      list: [],
      total: 0,
      listLoading: false,
      query: {
        current: 1,
        limit: 10,
        keywords: null,
        start: null,
        end: null
      }
    }
  },
  methods: {
    // 执行过滤 分页
    handleFilter(query) {
      this.query.current = 1
      this.query.limit = 10
      this.query.keywords = query.keywords
      try {
        this.query.start = query.start
        this.query.end = query.end
      } catch (e) {
        this.query.start = null
        this.query.end = null
      } finally {
        this.getList()
      }
    }
  }

}
