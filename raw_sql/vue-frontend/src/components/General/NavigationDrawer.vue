<template>
  <span>
    <v-navigation-drawer
            :dark="state.dark"
            absolute
            left
            temporary
            v-model="state.drawer"
    >
      <v-list
              dense
              nav
      >
        <v-list-item-group :key="route.name" v-for="route in routes">
          <v-list-item @click="navigateToRoute(route.path)">
            <v-list-item-icon>
              <v-icon>{{route.icon}}</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{route.name}}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </span>
</template>

<script>
  import {Component, Vue} from 'vue-property-decorator'
  import {routes} from "../../router";

  @Component
  class NavigationDrawer extends Vue {
    drawer = this.$store.state.drawer;
    state = this.$store.state;
    routes = routes;

    /**
     * each navigation item has a route. Navigate to the route if the item is clicked.
     * @param route
     * @returns {Promise<void>}
     */
    async navigateToRoute(route) {
      if (this.$router.currentRoute.path !== route) await this.$router.push(route)
    }
  }

  export default NavigationDrawer
</script>
<style scoped>
</style>
