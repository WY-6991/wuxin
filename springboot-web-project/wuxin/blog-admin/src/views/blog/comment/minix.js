import { getCommentList } from '@/api/comment'
import CommentList from '@/views/blog/comment/CommentList.vue'
import { query } from '@/mixin/query'

export const minix = {
  components: { CommentList },
  mixins: [query],
  data() {
    return {
      list: [],
      listLoading: true,
      total: 0,
      query: {
        current: 1,
        limit: 10,
        type: null,
        keywords: null,
        id: null
      }
    }
  },
  methods: {
    getList() {
      getCommentList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    }
  }
}
