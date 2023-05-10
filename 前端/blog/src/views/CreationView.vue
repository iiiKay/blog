<template>
  <div class="container">
    <el-form>
      <el-form-item label="标题" required>
        <el-input v-model="form.title"></el-input>
        <el-button type="primary" @click="send" style="margin-left: 5px"
          >发布</el-button
        >
      </el-form-item>
    </el-form>
    <div class="content">
      <MdEditor
        :toolbars="toolbar"
        :preview="form.preview"
        v-model="form.content"
        @onUploadImg="onUploadImg"
      />
    </div>
  </div>
</template>

<script>
import MdEditor from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { ElMessage } from "element-plus";
import axios from "axios";
export default {
  components: { MdEditor },
  data() {
    return {
      form: {
        title: "",
        content: "",
        preview: false,
      },
      toolbar: [
        "bold",
        "underline",
        "italic",
        "strikeThrough",
        "title",
        "sub",
        "sup",
        "quote",
        "unorderedList",
        "orderedList",
        "codeRow",
        "code",
        "link",
        "image",
        "table",
        "pageFullscreen",
        "preview",
      ],
    };
  },
  mounted() {
    if (this.$route.params.id != "1314-0520-1220-9420") {
      this.axios
        .get("/blog/getBlogById", {
          params: { id: this.$route.params.id },
        })
        .then((res) => {
          if (res.data.code === 200) {
            this.form.title = res.data.item.blog.title;
            this.form.content = res.data.item.blog.content;
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
    }
  },
  beforeMount() {
    if (document.body.clientWidth >= 900) {
      this.form.preview = true;
    }
  },
  methods: {
    send() {
      if (this.form.title.trim() === "" || this.form.content.trim() === "") {
        ElMessage.error("请完成写作后发布");
      } else {
        if (this.$route.params.id === "1314-0520-1220-9420") {
          const json = {
            title: this.form.title,
            content: this.form.content,
          };
          this.axios.post("/blog/issue", json).then((res) => {
            if (res.data.code === 200) {
              ElMessage.success("发布成功！");
              this.$router.push("/main/home");
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
        } else {
          const json = {
            bid: this.$route.params.id,
            title: this.form.title,
            content: this.form.content,
          };
          this.axios.put("/blog/updateBlog", json).then((res) => {
            if (res.data.code === 200) {
              ElMessage.success("修改成功！");
              this.$router.push("/main/me");
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
        }
      }
    },
  },
  setup() {
    let _this = this;
    const onUploadImg = async (files, callback) => {
      const res = await Promise.all(
        Array.from(files).map((file) => {
          return new Promise(async (rev, rej) => {
            const form = new FormData();
            form.append("file", file);
            axios.post("/user/uploadImg", form).then((res) => {
              if (res.data.code === 200) {
                rev(res);
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
          });
        })
      );
      callback(res.map((item) => item.data.msg));
    };
    return {
      onUploadImg,
    };
  },
};
</script>
<style scoped>
.content {
  height: 100%;
}

:deep(.el-form-item) {
  flex-wrap: nowrap;
  margin: 5px;
}

:deep(.el-input) {
  flex: 1;
}
</style>
