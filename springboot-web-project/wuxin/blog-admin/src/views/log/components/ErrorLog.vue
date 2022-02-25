<template>
  <div>
    <pre class="language-number">
      <code class="language-java">{{error.result}}</code>
    </pre>
  </div>
</template>
<script>
export default {
  name: 'ErrorLogResult',
  props: {
    error: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  mounted() {
    setTimeout(() =>{
      this.$primsjs.highlightAll()
    }, 100)
  }
}
</script>
<style scoped>

code.language-java,
pre.language-java {
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
  overflow: auto;
  -moz-tab-size: 4;
  -o-tab-size: 4;
  tab-size: 4;

  -webkit-hyphens: none;
  -moz-hyphens: none;
  -ms-hyphens: none;
  hyphens: none;
}

pre.language-java {
  padding: 1em;
  margin: .5em 0;
  overflow: auto;
  border-radius: 0.3em;
}

:not(pre) > code.language-java,
pre.language-java {
  background: rgb(22, 22, 22);
}

:not(pre) > code.language-java {
  padding: .1em;
  border-radius: .3em;
  white-space: normal;
}
</style>
