import { createRouter, createWebHashHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import BlogView from "../views/BlogView.vue";
import MainView from "../views/MainView.vue";
import FindView from "../views/FindView.vue";
import MyBlogView from "../views/MyBlogView.vue";
import HomeComponent from "../components/HomeComponent.vue";
import MeComponent from "../components/MeComponent.vue";
import CreationView from "../views/CreationView.vue";
const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      title: "用户登录",
    },
  },
  {
    path: "/blog/:id",
    name: "blog",
    component: BlogView,
    meta: {
      title: "博客",
    },
  },
  {
    path: "/main",
    name: "main",
    component: MainView,
    redirect: "/main/home",
    meta: {
      title: "主页",
    },
    children: [
      {
        path: "home",
        name: "home",
        component: HomeComponent,
      },
      {
        path: "me",
        name: "me",
        component: MeComponent,
      },
    ],
  },
  {
    path: "/find/:key",
    name: "find",
    component: FindView,
    meta: {
      title: "搜索",
    },
  },
  {
    path: "/myblogs",
    name: "myblogs",
    component: MyBlogView,
    meta: {
      title: "我的博客",
    },
  },
  {
    path: "/creation",
    name: "creation",
    component: CreationView,
    meta: {
      title: "创作博客",
    },
  },
  {
    path: "/creation/:id",
    name: "creation",
    component: CreationView,
    meta: {
      title: "创作博客",
    },
  },
];
const router = createRouter({
  routes,
  history: createWebHashHistory(),
});

router.beforeEach((to, from) => {
  document.title = to.meta.title;
});

export default router;
