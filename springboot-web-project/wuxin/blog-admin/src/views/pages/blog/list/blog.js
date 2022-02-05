import {addArchive} from "@/api/archive";
import AddArchive from "@/views/pages/blog/components/AddArchive";
import MyImage from "@/components/MyComponents/MyImage";
import {getCategoryList} from "@/api/category";
import {delBlog, getBlogList, updateBlog} from "@/api/blog";
import {query} from "@/mixin/query";
export default {
  name: "list",
  components: {MyImage, AddArchive},
  mixins:[query],
  data() {
    return {
      list: [],
      categoryList: [],
      total: 0, // 数据总数
      listLoading: true,
      dialogFormArchive: false,
      dialogBlogVisible: false,
      dialogStatus: "",
      blog: {},
      archive: {
        blogId: '',
        title: '',
        type: 1
      },
      category: '',
      state1: '',

    };
  },
  created() {

    this.getList()
    this.getCategoryList()

  },
  methods: {


    // 显示修改我的blog权限
    showBlog(row) {
      // 结构
      this.blog = row
      this.dialogBlogVisible = true
    },

    /* 添加 */
    handleCreate() {
      this.$router.replace("add");
    },

    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    loadAll() {
      let queryList = []
      console.log(this.categoryList)
      this.categoryList.forEach(category => {
        let obj = {}
        obj.value = category.name
        queryList.push(obj)
      })
      console.log('queryList', JSON.stringify(queryList))
      return queryList
    },


    // 修改操作
    handleUpdate(blogId) {
      this.$router.push(`edit/${blogId}`)
    },

    update(blog) {
      updateBlog(blog).then(res => {
        if (res.code === 200) {
          this.dialogBlogVisible = false
        }
      })

    },


    // 修改操作
    handleComment(row) {
      this.$router.push('comment/' + row.blogId)
    },


    // 删除操作
    handleDelete(blogId, index) {
      delBlog(blogId).then(res => {
        this.list.splice(index, 1);
      })
    },

    // 获取list
    getList() {
      this.listLoading = true;
      getBlogList(this.query).then(res => {
        //是否能够获取到数据，获取不到数据就清空！
        this.list = res.result.records ? res.result.records : []
        this.total = res.result.total ? res.result.total : 0
        this.listLoading = false
      })
      // 如果5秒钟没有获取到数据就说明加载失败 将load状态为false
      setTimeout(() => {
        this.listLoading = false
      }, 5000)
    },

    handleArchive(blog) {
      this.dialogFormArchive = true
      this.archive.blogId = blog.blogId
      this.archive.title = blog.title


    },


    addArchive(archive) {
      addArchive(archive).then(res => {
        if (res.code === 200) {
          this.$notify.success("添加成功")
          this.dialogFormArchive = false
        }
      })
    },
    cancelArchive() {
      this.dialogFormArchive = false
    },

    getCategoryList() {
      getCategoryList().then(res => {
        if (res.code === 200) {
          this.categoryList = res.result
          this.$nextTick(() => {
            this.restaurants = this.loadAll();
          })
        }
      })
    }
  }
};
