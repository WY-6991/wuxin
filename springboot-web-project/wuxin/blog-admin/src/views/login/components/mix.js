export const mixin = {


  methods:{
    showName(name){
      this.$emit('showName',name)
    }
  }
}


