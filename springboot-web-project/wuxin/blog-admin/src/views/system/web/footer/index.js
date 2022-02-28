import FooterContent from '@/views/system/web/footer/FooterContent'
import MyCard from '@/components/MyComponents/MyCard'
import { delWebFooterLabel, updateWebFooterLabel } from '@/api/system'

export default {
  name: 'WebFooter',
  components: { MyCard, FooterContent },
  props: {
    footerLabel: {
      type: Array,
      default: () => {
        return []
      }
    }
  },

  methods: {
    createItem() {
      const temp = {
        'id': this.footerLabel.length + 1,
        'typeName': '',
        'typeColor': 'grey',
        'name': '',
        'color': 'rgb(115,110,110)',
        'url': '',
        'title': ''
      }
      this.footerLabel.push(temp)
    },

    updateItem(data) {
      if (!this.isRoot) {
        this.$message.error('操作失败！无权限执行该操作！')
        return
      }
      updateWebFooterLabel(data).then(res => {
        if (res.code === 200) {
          this.$notify.success(res.result)
        }
      })
    },

    deleteItem(id) {
      delWebFooterLabel(id).then(res => {
        if (res.code === 200) {
          this.$notify.success('删除成功')
          this.getData()
        }
      })
    }

  }

}
