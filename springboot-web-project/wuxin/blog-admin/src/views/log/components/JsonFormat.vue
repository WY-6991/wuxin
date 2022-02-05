<template>
  <div>
   <el-card>
     <template v-if="params">
       <p style="margin-top: -10px;">请求参数</p>
       <pre><code class="language-json">{{ params }}</code></pre>
     </template>
     <template v-if="result">
       <p>请求结果</p>
       <pre><code class="language-json">{{ result }}</code></pre>
     </template>
   </el-card>
  </div>
</template>
<script>
export default {
  name: 'JsonCode',
  props: ['params', 'result'],
  // mounted() {
  //   const timer = setTimeout(() => {
  //     this.$prismjs.highlightAll()
  //   }, 500)
  // }

}
</script>
<style scoped>

code.language-json,
pre.language-json {
  color: #f8cb06;
  background: none;
  text-shadow: 0 1px rgba(0, 0, 0, 0.3);
  font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;
  font-size: 1em;
  text-align: left;
  white-space: pre;
  word-spacing: normal;
  word-break: normal;
  word-wrap: normal;
  line-height: 1.5;

  -moz-tab-size: 4;
  -o-tab-size: 4;
  tab-size: 4;

  -webkit-hyphens: none;
  -moz-hyphens: none;
  -ms-hyphens: none;
  hyphens: none;
}


pre.language-json {
  padding: 1em;
  margin: .5em 0;
  overflow: auto;
  border-radius: 0.3em;
}

:not(pre) > code.language-json,
pre.language-json {
  background: rgb(22, 22, 22);
}


:not(pre) > code.language-json {
  padding: .1em;
  border-radius: .3em;
  white-space: normal;
}

</style>
