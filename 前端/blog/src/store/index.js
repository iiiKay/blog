import { createStore } from "vuex";
import axios from "axios";
import { ElMessage } from "element-plus";
const store = createStore({
  state: {
    items: [],
    curPage: 1,
  },
  mutations: {
    setItems(state, items) {
      state.items = items;
    },
    addCurPage(state) {
      state.curPage++;
    },
  },
  actions: {
    getItems(context) {
      axios
        .get("/blog/getBlogs", {
          params: { curPage: context.state.curPage },
        })
        .then((res) => {
          if (res.data.code === 200) {
            context.commit("setItems", res.data.item.blogs);
          } else if (res.data.code === 500 || res.data.code === 600) {
            ElMessage({
              type: "error",
              message: res.data.msg,
            });
            window.location.href = "/";
          } else {
            ElMessage({
              type: "error",
              message: res.data.msg,
            });
          }
          return res.data.code;
        });
    },
  },
});
export default store;
