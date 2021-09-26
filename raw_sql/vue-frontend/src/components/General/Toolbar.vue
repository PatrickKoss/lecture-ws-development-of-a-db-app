<template>
  <v-app-bar :dark="state.dark" app elevate-on-scroll>
    <v-app-bar-nav-icon @click.stop="state.drawer = !state.drawer"/>
    <v-toolbar-title @click="navigateToRoute('/')" id="toolbarTitle"><span
            v-if="$vuetify.breakpoint.mobile">FE</span><span v-else>Frontend</span></v-toolbar-title>
    <v-spacer/>

    <v-switch class="ma-2" label="Dark Mode" style="padding-top: 27px" v-model="state.dark"/>
    <v-menu offset-y open-on-hover top>
      <template v-slot:activator="{ on }">
        <v-btn icon large v-on="on">
          <v-avatar size="28">
            <v-icon>mdi-account-circle</v-icon>
          </v-avatar>
        </v-btn>
      </template>

      <v-list>
        <v-list-item
                @click="navigateToRoute('/accounts')"
        >
          <v-list-item-title>Account</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
  import {Component, Vue, Watch} from 'vue-property-decorator';

  @Component({
    components: {}
  })
  class Toolbar extends Vue {
    state = this.$store.state;
    firstDarkCheck = true;

    /**
     * handle navigation
     */
    async navigateToRoute(route) {
      if (this.$router.currentRoute.path !== route) await this.$router.push(route)
    }

    /**
     * set up the localstorage based on what mode is selected
     * @private
     */
    @Watch('state.dark', {immediate: true, deep: true})
    __updateLocalStorageDark() {
      // avoid that the localstorage is set on the first watch because it will always be the initial value of state dark
      if (!this.firstDarkCheck) {
        localStorage.dark = this.state.dark;
      }
      this.firstDarkCheck = false;
    }
  }

  export default Toolbar
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
    background-color: lightgrey !important;
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
