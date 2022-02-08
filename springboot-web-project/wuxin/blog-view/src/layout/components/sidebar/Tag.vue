<template>
  <div class="ui segments  m-box tag-sidebar" v-if="tagList&&tagList.length!==0" :style="bgColor">
    <div class="ui secondary segment " :style="bgColor"><i class="tags icon" :style="bgColor"></i>标签云</div>
    <div class="ui yellow segment m-padding-small" :style="bgColor">
      <router-link :to="`/tag/${tag.name}`" class="ui label m-text-500" :class="tag.color"
                   v-for="(tag,index) in tagList" :key="`tag${index}`">
        {{ tag.name }}
      </router-link>
      <div class="ui label teal">
        <span>{{$store.state.setting.background.color}}</span>
      </div>
      <div class="ui label teal">
        <span>{{$store.state.setting.nightMode}}</span>
      </div>
    </div>
  </div>
</template>

<script>


export default {
  name: "TagSidebar",
  props: {
    tagList: {
      type: Array,
      required: true,
      default: [
        {id: '1', name: 'springboot', color: "red"},
        {id: '2', name: 'vue', color: "green"},
      ]
    },
  },
  mounted() {
    let tag = document.querySelector('.tag-sidebar')
    // console.log(tag.scrollHeight)
    // console.log(tag.offsetHeight)
    // console.log(tag.clientHeight)
    // console.log(tag.clientHeight)
  }
}
</script>

<style scoped>
.secondary.segment {
  padding: 10px;
}

.m-padding-small {
  padding: 7px;
}

.label {
  margin: 3px !important;
}
</style>