import {updateMySystemInfo} from "@/api/system";
import MyCard from "@/components/MyComponents/MyCard";
export const minix = {
  props:['systemInfo'],
  components: {MyCard},
  methods: {
    updateData() {
      if(!this.isRoot){
        this.$message.error('修改失败，无权限执行该操作！')
        return
      }
      updateMySystemInfo(this.systemInfo).then(res=>{
        if(res.code===200){
          this.$message.success('修改成功！')
        }
      })
    }
  },
}
