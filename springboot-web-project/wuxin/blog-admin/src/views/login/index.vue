<template>
  <div class="login-main-container">
    <LoginBackground @isShow="showChange()"></LoginBackground>
    <transition enter-active-class="m-fade-in" leave-active-class="m-fade-out">
      <div class="login-container" v-show="isShow">
        <h2 style="text-align: center;color: #fff;letter-spacing: 1px;">{{
            componentName === 'Register' ? '注册' : '登录'
          }}</h2>
        <component :is="componentName" @showName="showName"></component>
      </div>
    </transition>
  </div>
</template>

<script>
import Pass from '@/views/login/components/Pass'
import Email from '@/views/login/components/Email'
import Register from '@/views/login/components/Register'
import LoginBackground from '@/views/login/components/LoginBackground'

export default {
  components: { LoginBackground, Email, Pass, Register },
  data() {
    return {
      isShow: true,
      componentName: 'Pass'
    }
  },
  methods: {
    showChange() {
      this.isShow = !this.isShow
    },
    showName(name) {
      this.componentName = name
    }
  }

}
</script>

<style lang="scss">

$cursor: #736e6e;
$bg: rgba(227, 223, 223, 0.4);
$input-color: #fff;
$custom-color: #eee;
@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

div.login-main-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: 0;
  padding: 0;
  width: 100%;
  min-height: 100vh;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;

  .m-fade-in {
    animation: fadeIn 1s ease-in-out !important;
    transform-style: preserve-3d;
  }

  .m-fade-out {
    animation: fadeOut 1s ease-in-out !important;
    transform-style: preserve-3d;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      filter: alpha(opacity=0);
      transform: translate3d(0, -30px, 0);

    }
    to {
      opacity: 1;
      filter: none;
      transform: translate3d(0, 0, 0px);
    }
  }
  @keyframes fadeOut {
    from {
      opacity: 1;
      filter: none;
    }
    to {
      opacity: 0;
      filter: alpha(opacity=0);
    }
  }

  .login-container {
    position: relative;
    width: 30%;
    min-width: 300px;
    background-color: $bg;
    background-repeat: no-repeat;
    background-attachment: fixed;
    z-index: 10;
    border-radius: 2%;

    .login-form {
      position: relative;
      padding: 0px 35px;
      margin: 0 auto;
      overflow: hidden;

      .login-tip {
        float: right;

        .tip {
          color: white !important;
        }
      }
    }
  }
}
</style>

