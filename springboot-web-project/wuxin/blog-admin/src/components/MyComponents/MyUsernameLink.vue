<template>
    <el-link icon="el-icon-user" :underline="false" type="primary">{{ username}}</el-link>
</template>
<script>
export default {
  name:'MyUserLink',
  props:['username']
}
</script>

