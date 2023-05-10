import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import "../public/css/index.css";
import VueAxios from "vue-axios";
import "element-plus/dist/index.css";
import ElementPlus from "element-plus";
import Router from "/src/router";
import store from "/src/store";
const app = createApp(App);
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.use(Router);
app.use(store);
app.use(VueAxios, axios);
app.use(ElementPlus);
axios.defaults.headers.common["Authorization"] = localStorage.getItem("token");
app.mount("#app");
