<template>
  <div>
    <vue-qr
      :correctLevel="3"
      :autoColor="false"
      colorDark="#000000"
      :logoSrc="logo"
      :text="text"
      :size="100"
      :margin="0"
      :logoMargin="3"
    />
  </div>
</template>

<script>
import VueQr from "vue-qr";
export default {
  name: "BlogCode",
  components: {
    VueQr,
  },
  data() {
    return {
      logo: require("@/assets/logo.png"),
    };
  },
  props: {
    text: {
      type: String,
      default: "",
      require: true,
    },
  }
};
</script>


