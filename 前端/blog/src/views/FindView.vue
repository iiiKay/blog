<template>
  <div class="content">
    <div class="top">
      <el-input v-model="content" prefix-icon="Search" class="input"></el-input>
      <el-button type="primary" @click="search">搜索</el-button>
    </div>
    <div class="main">
      <div class="items" v-show="!isShow">
        <el-empty description="什么都没找到哦~"></el-empty>
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
export default {
  mounted() {
    this.content = this.$route.params.key;
    this.axios
      .get("/blog/getBlogByKey", {
        params: {
          curPage: this.curPage,
          text: this.$route.params.key,
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
      curPage: 1,
      items: [],
      isShow: true,
      content: "",
    };
  },
  methods: {
    toBlog(val) {
      this.$router.push("/blog/" + val);
    },
    search() {
      if (this.content.trim() === "") {
        ElMessage({
          type: "error",
          message: "请输入要搜索的内容！",
          grouping: true,
        });
      } else {
        this.curPage = 1;
        this.$router.push("/find/" + this.content);
        this.axios
          .get("/blog/getBlogByKey", {
            params: {
              curPage: this.curPage,
              text: this.content,
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
      }
    },
    add() {
      if (this.items.length > 10) {
        this.curPage++;
        axios
          .get("/blog/getBlogs", {
            params: { curPage: this.curPage, text: this.content },
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
.content {
  flex: 1;
  display: flex;
  height: 100%;
  flex-direction: column;
  background-color: #f2f2f5;
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
</style>
