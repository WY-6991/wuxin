<template>
  <Comment :is-blog-comment="true" />
</template>
<script>
import Comment from '@/views/blog/comment/index'
export default {
  name: 'BlogComment',
  components: {
    Comment
  }
}
</script>

