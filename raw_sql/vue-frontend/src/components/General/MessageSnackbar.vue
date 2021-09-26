<template>
  <v-snackbar
          :bottom="true"
          :color="state.message.messageType"
          :timeout="timeout"
          multi-line
          v-model="snackbar"
          v-show="state.message.message !== ''"
  >
    {{ state.message.message }}
    <v-btn
            @click="snackbar = false"
            dark
            text
    >
      Close
    </v-btn>
  </v-snackbar>
</template>

<script>
  import {Component, Vue, Watch} from 'vue-property-decorator'

  @Component({
    components: {}
  })
  class MessageSnackbar extends Vue {
    state = this.$store.state;
    snackbar = false;
    timeout = 6000;

    /**
     * if the state message change then view the message
     * @private
     */
    @Watch("state.message", {immediate: true, deep: true})
    __showMessages() {
      if (this.state.message.message !== "") {
        this.snackbar = true;
      }
    }
  }

  export default MessageSnackbar
</script>
<style lang="scss">
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  .light {
    background-color: #f2f2f2 !important;
  }

  .dark {
    background-color: black !important;
  }

  #nav {
    padding: 30px;

    a {
      font-weight: bold;
      color: #2c3e50;

      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }

  #toolbarTitle {
    cursor: pointer;
  }
</style>
