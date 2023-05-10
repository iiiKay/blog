<template>
  <div
    class="info"
    @click="reply(comment.id, comment.user.id, comment.user.name)"
  >
    <img :src="comment.user.img" alt="头像" class="img" />
    <span class="user-name">{{ comment.user.name }}</span>
  </div>
  <div class="text">
    <span class="user-comment">{{ comment.content }}</span>
  </div>
  <div class="replies">
    <div class="reply" v-for="reply in replies" @click="replyText(reply)">
      <div class="info">
        <img :src="reply.user.img" alt="头像" class="img rimg" />
        <span class="user-name" v-if="isDoubble(reply)">{{
          reply.user.name
        }}</span>
        <span class="user-name" v-else
          >{{ reply.user.name }} 回复 {{ reply.reUser.name }}</span
        >
      </div>
      <span class="user-comment rep">{{ reply.content }}</span>
    </div>
  </div>
  <el-button
    type="info"
    round
    size="small"
    class="open-btn"
    v-show="comment.hasReplies"
    v-if="isShow"
    @click="shoReplies(comment.id)"
    >展开回复</el-button
  >
  <el-drawer v-model="replyShow" title="回复" direction="btt" size="15%">
    <div class="comment-input">
      <el-input
        v-model="myreply"
        :placeholder="name"
        maxlength="100"
        type="textarea"
        :autosize="true"
      ></el-input>
      <span class="send" @click="send">回复</span>
    </div>
  </el-drawer>
</template>

<script>
import { ElMessage } from "element-plus";
export default {
  props: ["comment"],
  data() {
    return {
      replyShow: false,
      myreply: "",
      uid: "",
      uname: "",
      cid: "",
      curPage: 1,
      replies: [],
      isShow: true,
    };
  },
  methods: {
    isDoubble(reply) {
      if (reply.reUser != null) {
        return false;
      }
      return true;
    },
    replyText(reply) {
      this.uid = reply.user.id;
      this.cid = this.comment.id;
      this.replyShow = true;
      this.name = reply.user.name;
    },
    shoReplies(id) {
      this.axios
        .get("/reply/getRepliesByCid", {
          params: { id: id, curPage: this.curPage },
        })
        .then((res) => {
          if (res.data.code === 200) {
            this.replies = this.replies.concat(res.data.item.replies);
            this.curPage++;
            console.log(this);
            if (!res.data.item.hasNext) {
              this.isShow = false;
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
    reply(cid, uid, name) {
      this.name = "回复 " + name;
      this.replyShow = true;
      this.cid = cid;
    },
    send() {
      if (this.myreply === "") {
        ElMessage.error("请输入评论内容!");
      } else {
        const json = {
          cid: this.cid,
          ruid: this.uid,
          content: this.myreply,
        };
        this.axios.put("/reply/addReply", json).then((res) => {
          if (res.data.code === 200) {
            ElMessage.success("回复成功!");
            this.replyShow = false;
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
  },
  watch: {
    replyShow(oldVal) {
      if (!oldVal) {
        this.cid = "";
        this.name = "";
        this.uid = "";
        this.myreply = "";
      }
    },
  },
};
</script>
<style scoped></style>
