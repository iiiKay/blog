<template>
  <div class="container">
    <div class="top">
      <span>我的博客</span>
    </div>
    <div class="main">
      <div class="items" v-show="!isShow">
        <el-empty description="目前还没有属于你的博客哦~"></el-empty>
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
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
export default {
  mounted() {
    this.axios
      .get("/blog/getBlogByUId", {
        params: {
          curPage: this.curPage,
        },
      })
      .then((res) => {
        if (res.data.code === 200) {
          if (res.data.item.blogs.length === 0) {
            this.isShow = false;
          } else {
            this.items = res.data.item.blogs;
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
  },
  data() {
    return {
      items: [],
      isShow: true,
      curPage: 1,
    };
  },
  methods: {
    toBlog(val) {
      this.$router.push("/blog/" + val);
    },
    add() {
      if (this.items.length > 10) {
        this.curPage++;
        axios
          .get("/blog/getBlogByUId", {
            params: {
              curPage: this.curPage,
              token: localStorage.getItem("token"),
            },
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
                this.items.concat(res.data.item.blogs);
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
  },
};
</script>
<style scoped>
.container {
  height: 100%;
}
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
}
.top {
  height: 45px;
  display: flex;
  justify-content: flex-start;
}

.top span {
  font-size: 18px;
  width: 150px;
  margin-left: 10px;
}
</style>
