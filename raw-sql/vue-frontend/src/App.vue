<template>
  <v-app v-bind:class="{ dark: state.dark, light: !state.dark }">
    <Toolbar/>

    <NavigationDrawer/>

    <!-- Sizes your content based upon application components -->
    <v-main>

      <!-- Provides the application the proper gutter -->
      <v-container fluid>

        <!-- If using vue-router -->
        <router-view/>
      </v-container>
    </v-main>

    <v-footer app>
      <!-- -->
    </v-footer>

    <MessageSnackbar/>
  </v-app>
</template>

<script>
  import {Component, Vue} from 'vue-property-decorator'
  import NavigationDrawer from "./components/General/NavigationDrawer";
  import Toolbar from "./components/General/Toolbar";
  import MessageSnackbar from "./components/General/MessageSnackbar";

  @Component({
    components: {
      NavigationDrawer,
      Toolbar,
      MessageSnackbar,
    }
  })
  class App extends Vue {
    state = this.$store.state;

    /**
     * set some data when the App is created and check authentication
     * @returns {Promise<void>}
     */
    async created() {
      // set the state dark according to the local storage dark
      this.state.dark = localStorage.dark === 'true';
    }
  }

  export default App
</script>
<style lang="scss" scoped>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
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
