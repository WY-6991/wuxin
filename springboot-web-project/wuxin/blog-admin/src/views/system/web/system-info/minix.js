import {updateMySystemInfo} from "@/api/system";
import MyCard from "@/components/MyComponents/MyCard";
export const minix = {
  props:['systemInfo'],
  components: {MyCard},
  methods: {
    updateData() {
      updateMySystemInfo(this.systemInfo).then(res=>{
        if(res.code===200){
          this.$message.success('修改成功！')
        }
      })
    }
  },
}
