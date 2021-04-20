import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import BookList from "../views/BookList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import BookStore from "@/views/BookStore";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Book" });
      }
    },
  },
  {
    path: "/books",
    name: "Books",
    component: BookList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/bookstore",
    name: "Bookstore",
    component: BookStore,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Bookstore" });
      }
   },
  },
];

const router = new VueRouter({
  routes,
});

export default router;
