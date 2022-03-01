import { getCategoryList } from '@/api/category'
import { getAllTagList } from '@/api/tag'
import { createVditor } from '@/plugins/CreateVditor'
import ElDragSelect from '@/components/DragSelect'

export const minix = {
  components: { ElDragSelect },
  data() {
    const validateDesc = (rule, value, callback) => {
      if (this.descriptionVditor.getValue().length === 1) {
        this.msg = '请填写描述'
        callback(new Error('请填写描述'))
      }
      callback()
    }

    const validateContent = (rule, value, callback) => {
      if (this.contentVditor.getValue().length === 1) {
        this.msg = '请填写内容'
        callback(new Error('请填写内容'))
      }
      callback()
    }

    const validatePass = (rule, value, callback) => {
      // 密码是否开启 只有开启了才会要求输入密码
      if (!this.blog.secrecy) {
        callback()
      }
      if (value.length < 4 || value.length > 16) {
        callback(new Error('密码长度4-16'))
      }
      callback()
    }

    const validateTagIds = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('至少选择一个标签'))
      }
      if (value.length >= 4) {
        callback(new Error('最多可选择4个标签'))
      }
      callback()
    }
    return {
      descriptionVditor: '',
      contentVditor: '',
      listLoading: true,
      dialogFormVisible: false,
      addTagDialogFormVisible: false,
      addCategoryDialogFormVisible: false,
      categoryList: [],
      tagList: [],
      rules: {
        title: [{ required: true, message: '标题不能为空！', trigger: 'blur' }],
        desc: [{ validator: validateDesc, trigger: 'blur' }],
        content: [{ validator: validateContent, trigger: 'blur' }],
        tagIds: [{ validator: validateTagIds, trigger: 'blur' }],
        category: [{ required: true, message: '分类不能为空！', trigger: 'blur' }],
        password: [{ validator: validatePass, trigger: 'blur' }]
      }
    }
  },

  methods: {
    // 描述
    initVditor() {
      this.descriptionVditor = createVditor('vditor-description', 400, true)
      this.contentVditor = createVditor('vditor-content', 500, true)
      this.getLabelList()
    },
    getLabelList() {
      getCategoryList().then(res => {
        this.categoryList = res.result
      })
      getAllTagList().then(res => {
        this.tagList = res.result
      })
    },

    addCategory() {
      this.addCategoryDialogFormVisible = true
    },
    addTag() {
      this.addTagDialogFormVisible = true
    },

    closeAddLabel() {
      this.addCategoryDialogFormVisible = false
      this.addTagDialogFormVisible = false
    }

  },
  mounted() {
    this.initVditor()
  }
}
