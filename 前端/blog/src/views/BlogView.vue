<template>
  <div class="box">
    <div class="top">
      <div class="items-center">
        <el-avatar :size="32" class="mr-3" :src="blog.img" />
        <span class="uNameBox">{{ blog.name }}</span>
        <div class="left">
          <el-tag>发布于{{ blog.date }}</el-tag>
          <el-button type="primary" v-if="isShow" @click="edit">编辑</el-button>
        </div>
      </div>
    </div>
    <MdEditor :preview-only="isBool" v-model="blog.content"> </MdEditor>
    <div class="bottom">
      <a @click="this.comment = true">查看评论</a>
      <el-drawer v-model="comment" title="评论" direction="btt" size="80%">
        <Comment />
      </el-drawer>
    </div>
  </div>
</template>

<script>
import MdEditor from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { ElMessage } from "element-plus";
import Comment from "../components/CommentComponent.vue";
export default {
  components: { MdEditor, Comment },
  data() {
    return {
      blog: [],
      isBool: true,
      isShow: false,
      comment: false,
    };
  },
  methods: {
    edit() {
      this.$router.push("/creation/" + this.$route.params.id);
    },
  },
  mounted() {
    this.axios
      .get("/blog/getBlogById", {
        params: { id: this.$route.params.id },
      })
      .then((res) => {
        if (res.data.code === 200) {
          this.blog = res.data.item.blog;
        } else if (res.data.code === 500 || res.data.code === 600) {
          ElMessage({
            type: "error",
            message: res.data.msg,
          });
          this.$router.push("/login");
        } else {
          ElMessage({
            type: "error",
            message: res.data.msg,
          });
        }
      });
    this.axios
      .get("/blog/isMyBlog", {
        params: { id: this.$route.params.id },
      })
      .then((res) => {
        if (res.data.code === 200) {
          if (res.data.msg === "true") {
            this.isShow = true;
          }
        } else if (res.data.code === 500 || res.data.code === 600) {
          ElMessage({
            type: "error",
            message: res.data.msg,
          });
          this.$router.push("/login");
        } else {
          ElMessage({
            type: "error",
            message: res.data.msg,
          });
        }
      });
  },
};
</script>
<style scoped>
.items-center {
  margin-left: 15px;
  height: 40px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.left {
  position: absolute;
  right: 15px;
}

.uNameBox {
  color: gray;
  line-height: 40px;
  margin-left: 5px;
}

.bottom {
  display: flex;
  justify-content: center;
}

.bottom a {
  text-decoration: none;
  color: darkgray;
}

:deep(.el-drawer__header) {
  margin-bottom: 0px;
}
</style>
