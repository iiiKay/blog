<template>
  <div class="top">
    <el-input
      v-model="searchContent"
      prefix-icon="Search"
      class="input"
    ></el-input>
    <el-button type="primary" @click="search">搜索</el-button>
  </div>
  <div class="main">
    <div class="items" v-show="!isShow">
      <el-empty description="目前还没有博客哦~"></el-empty>
    </div>
    <div id="items" v-show="isShow" v-infinite-scroll="add">
      <div id="item" v-for="item of items" @click="toBlog(item.id)">
        <div id="info">
          <el-avatar :size="32" class="mr-3" :src="item.img" />
          <span class="user">{{ item.name }}</span>
        </div>
        <span class="title">{{ item.title }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import { toRaw } from "vue";
export default {
  mounted() {
    this.getItems();
    if (this.items === undefined) {
      this.isShow = false;
    }
    let me = document.getElementById("me");
    let home = document.getElementById("home");
    me.classList.remove("focus");
    home.classList.add("focus");
  },
  data() {
    return {
      searchContent: "",
      isShow: true,
      curPage: 1,
    };
  },
  computed: {
    items() {
      return toRaw(this.$store.state.items);
    },
  },
  methods: {
    search() {
      if (this.searchContent.trim() === "") {
        ElMessage({
          type: "error",
          message: "请输入要搜索的内容！",
          grouping: true,
        });
      } else {
        this.$router.push("/find/" + this.searchContent);
      }
    },
    toBlog(val) {
      console.log(val);
      this.$router.push("/blog/" + val);
    },
    add() {
      if (this.items.length >= 10) {
        this.curPage++;
        this.axios
          .get("/blog/getBlogs", {
            params: { curPage: this.curPage },
          })
          .then((res) => {
            if (res.data.code === 200) {
              if (res.data.item.blogs.length === 0) {
                ElMessage({
                  type: "warning",
                  message: "底下没有内容啦~",
                  grouping: true,
                });
              } else {
                this.$store.state.items = this.items.concat(
                  res.data.item.blogs
                );
              }
            } else if (res.data.code === 500 || res.data.code === 600) {
              ElMessage({
                type: "error",
                message: res.data.msg,
              });
              this.$router.push("/");
            } else {
              ElMessage({
                type: "error",
                message: res.data.msg,
              });
            }
          });
      }
    },
    getItems() {
      this.$store.dispatch("getItems");
    },
  },
};
</script>
<style scoped>
.top {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  height: 40px;
}
.input {
  width: 60%;
  margin-right: 10px;
}
.main {
  flex: 1;
  overflow-y: auto;
}

#items {
  overflow-y: auto;
  height: 100%;
}
#item {
  height: 100px;
  padding: 10px;
  margin: 10px;
  display: flex;
  border-radius: 5px;
  background-color: white;
  flex-direction: column;
}
#item:last-child {
  margin-bottom: 0;
}
#info {
  height: 40px;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.user {
  margin-left: 5px;
  color: gray;
  line-height: 40px;
}
.title {
  font-size: 22px;
  margin-left: 5px;
}
</style>
