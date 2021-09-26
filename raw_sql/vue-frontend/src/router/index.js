import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Accounts from "../views/Accounts";

Vue.use(VueRouter);

export const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    icon: "mdi-home",
  },
  {
    path: "/accounts",
    name: 'Accounts',
    component: Accounts,
    icon: "mdi-account",
  },
  {
    path: "/account-permissions",
    name: 'Account Permissions',
    component: Accounts,
    icon: "mdi-account",
  },
  {
    path: "/permissions",
    name: 'Permissions',
    component: Accounts,
    icon: "mdi-account",
  },
];

const router = new VueRouter({
  routes
});

export default router
