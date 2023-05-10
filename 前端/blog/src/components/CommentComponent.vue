<template>
  <div class="drawer">
    <div class="comment">
      <div class="comment-box" v-for="comment in comments">
        <text-componet :comment="comment"></text-componet>
      </div>
    </div>
    <div class="comment-input">
      <el-input
        v-model="mycontent"
        placeholder="良言一句三冬暖 恶语伤人六月寒"
        maxlength="100"
        type="textarea"
        :autosize="true"
      ></el-input>
      <span class="send" @click="send">发布</span>
    </div>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import TextComponet from "./TextComponet.vue";
export default {
  components: { TextComponet },
  mounted() {
    this.getComments();
  },
  data() {
    return {
      comments: [],
      mycontent: "",
    };
  },
  methods: {
 
    send() {
      if (this.mycontent === "") {
        ElMessage.error("请输入评论内容!");
      } else {
        const json = {
          bid: this.$route.params.id,
          content: this.mycontent,
        };
        this.axios.put("/comment/addComment", json).then((res) => {
          if (res.data.code === 200) {
            ElMessage.success("评论成功");
            this.mycontent = "";
            this.getComments();
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
    getComments() {
      this.axios
        .get("/comment/getCommentByBid", {
          params: { bId: this.$route.params.id },
        })
        .then((res) => {
          if (res.data.code === 200) {
            this.comments = res.data.item.comments;
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
    reply(id) {
      this.replyShow = true;
    },
  },
};
</script>
<style>
.drawer {
  display: flex;
  height: 100%;
  flex-direction: column;
}
.comment {
  flex: 1;
  overflow-x: auto;
}

.open-btn {
  width: 70px;
  margin-left: 45px;
}

.comment-input {
  display: flex;
  height: 35px;
}

.comment-box {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 2px;
}

.replies {
  margin-top: 10px;
}

.reply {
  margin-left: 45px;
}

.rep {
  margin-left: 35px;
}

.send {
  margin-left: 10px;
  width: 50px;
  text-align: center;
  line-height: 35px;
  color: #0396ff;
}

.info {
  display: flex;
  align-items: center;
}

.img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.rimg {
  width: 30px;
  height: 30px;
}

.text {
  display: flex;
  flex-direction: column;
  margin-left: 45px;
}

.user-name {
  color: gray;
  margin-left: 5px;
}

.user-comment {
  display: block;
  word-break: break-all;
  white-space: pre-wrap;
}
</style>
