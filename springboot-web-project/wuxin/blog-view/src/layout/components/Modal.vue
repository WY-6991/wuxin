<template lang="html">
  <div>
    <sui-button @click.native="toggle">公告</sui-button>
    <sui-modal v-model="open">
      <sui-modal-header>头</sui-modal-header>
      <sui-modal-content image>
        <sui-image
            wrapped
            size="medium"
            src="https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202201/20220105175236.png"
        />
        <sui-modal-description>
          <sui-header>div</sui-header>
          <p>
          内容
          </p>
          <p>Is it okay to use this photo?</p>
        </sui-modal-description>
      </sui-modal-content>
      <sui-modal-actions>
        <sui-button positive @click.native="toggle">
          OK
        </sui-button>
      </sui-modal-actions>
    </sui-modal>
  </div>
</template>

<script>
export default {
  name: 'ModalExample',
  data() {
    return { open: false };
  },
  methods: {
    toggle() {
      this.open = !this.open;
    },
  },
};
</script>

<style lang="css"></style>
