<template>
  <div class="filter-container">
    <slot name="pre" />
    <el-input
      v-if="showSearchInput"
      v-model="query.keywords"
      :placeholder="placeholder"
      size="small"
      clearable
      class="m-search-input m-margin-left-small "
      @keyup.enter.native="handleSearch"
    />
    <el-date-picker
      v-if="showTimeButton"
      v-model="query.date"
      class="m-margin-left-small"
      :picker-options="pickerOptions"
      :value-format="type==='daterange'?'yyyy-MM-dd':'yyyy-MM-dd HH:mm:ss'"
      :type="type"
      :style="{width: type==='daterange'?'260px':'350px'}"
      range-separator="至"
      size="small"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      @blur="handleSearch"
    />
    <el-button
      v-if="showSearchButton"
      class="m-margin-left-small"
      type="primary"
      icon="el-icon-search"
      size="mini"
      @click.prevent="handleSearch"
    >
      搜索
    </el-button>
    <el-button
      v-if="showCreateButton"
      class="m-margin-left-small"
      type="primary"
      icon="el-icon-plus"
      size="mini"
      @click="handlerCreate"
    >
      添加
    </el-button>
    <slot />

  </div>

</template>
<script>
export default {
  name: 'MySearchHeader',
  props: {

    /* 默认关键词*/
    placeholder: {
      type: String,
      default: '关键词...'
    },

    type: {
      type: String,
      default: 'daterange'
    },

    /* 是否显示搜索输入框*/
    showSearchInput: {
      type: Boolean,
      default: true
    },

    /* 是否显示时间组件*/
    showTimeButton: {
      type: Boolean,
      default: true
    },
    /* 是否显示搜索按钮*/
    showSearchButton: {
      type: Boolean,
      default: true
    },

    /* 是否显示添加按钮*/
    showCreateButton: {
      type: Boolean,
      default: true
    },

    /* 是否失去焦点时候触发搜索*/
    activeBlur: {
      type: Boolean,
      default: true
    },

    /* 搜索条件*/
    query: {
      type: Object,
      default: () => {
        return { current: 1, limit: 10, keywords: '', start: '', end: '' }
      }
    }
  },
  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近三天',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 3)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近半年',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 180)
            picker.$emit('pick', [start, end])
          }
        }]
      }

    }
  },
  created() {
    if (this.type !== 'daterange') {
      this.createData()
    }
  },

  methods: {
    /* 添加事件*/
    handlerCreate() {
      this.$emit('handleCreate')
    },

    /* 搜索事件*/
    handleSearch() {
      try {
        if (this.query.date && this.query.date[0] && this.query.date[1]) {
          this.query.start = this.query.date[0]
          this.query.end = this.query.date[1]
        }
      } catch (e) {
        this.query.start = null
        this.query.end = null
      } finally {
        console.log('header中查询条件=>' + JSON.stringify(this.query))
        this.$emit('handleSearch', this.query)
      }
    },
    cancel() { /* 不触发事件，防止报错*/
    },
    createData() {
      this.pickerOptions.shortcuts = [
        {
          text: '最近一小时',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 1)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近三小时',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 3)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近十二小时',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 12)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近一天',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近三天',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 3)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }
      ]
    }
  }

}
</script>
