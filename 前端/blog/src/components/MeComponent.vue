<template>
  <div class="content">
    <div class="head" @click="editInfo">
      <el-avatar :size="80" class="mr-3" :src="img" />
      <span class="uname">{{ name }}</span>
    </div>
    <div class="line">
      <el-divider style="margin: 0"></el-divider>
    </div>
    <div class="imain">
      <ul>
        <li @click="goMyBlog">
          <el-icon style="margin-left: 10px">
            <MessageBox />
          </el-icon>
          <span class="ibtn">我的博客</span>
        </li>
        <li @click="goCreation()">
          <el-icon style="margin-left: 10px">
            <EditPen />
          </el-icon>
          <span class="ibtn"> 创作博客 </span>
        </li>
        <li @click="logout">
          <el-icon style="margin-left: 10px">
            <Remove />
          </el-icon>
          <span class="ibtn">退出登录</span>
        </li>
      </ul>
    </div>
  </div>
  <el-dialog title="编辑资料" v-model="isShow" :show-close="false">
    <el-form-item label="头像">
      <el-upload
        class="avatar-uploader"
        action="/user/uploadImg"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :headers="Headers"
      >
        <img v-if="form.img" :src="form.img" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
    </el-form-item>
    <el-form label-position="top">
      <el-form-item label="用户名">
        <el-input
          v-model="form.name"
          maxlength="10"
          prefix-icon="User"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input
          v-model="form.pwd"
          maxlength="16"
          type="password"
          prefix-icon="Lock"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="this.isShow = false">取消</el-button>
      <el-button type="primary" @click="enter">确定</el-button>
    </template>
  </el-dialog>
</template>

<script>
import { ElMessage } from "element-plus";

export default {
  methods: {
    goCreation() {
      this.$router.push("/creation/1314-0520-1220-9420");
    },
    logout() {
      localStorage.setItem("token", "");
      this.axios.defaults.headers.common["Authorization"] = "";
      this.$router.push("/");
      this.$store.state.items = [];
    },
    goMyBlog() {
      this.$router.push("/myblogs");
    },
    editInfo() {
      this.isShow = true;
    },
    enter() {
      if (this.form.name === "") {
        ElMessage({
          type: "warning",
          message: "请输入用户名",
          grouping: true,
        });
      } else {
        this.axios.put("/user/edit", this.form).then((res) => {
          if (res.data.code === 200) {
            this.form.pwd = "";
            ElMessage.success("信息修改成功！");
            this.name = this.form.name;
            this.img = this.form.img;
            this.isShow = false;
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
    handleAvatarSuccess(response, uploadFile) {
      this.form.img = response.msg;
    },
  },
  data() {
    return {
      isShow: false,
      name: "",
      img: "",
      form: {
        name: "",
        pwd: "",
        img: "",
      },
      Headers: {
        Authorization: localStorage.getItem("token"),
      },
    };
  },
  mounted() {
    let me = document.getElementById("me");
    let home = document.getElementById("home");
    me.classList.add("focus");
    home.classList.remove("focus");
    this.axios.get("/user/getInfo").then((res) => {
      if (res.data.code === 200) {
        this.name = res.data.item.user.name;
        this.img = res.data.item.user.img;
        this.form.name = this.name;
        this.form.img = this.img;
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
<style>
.content {
  display: flex;
  flex-direction: column;
}

.head {
  display: flex;
  align-items: center;
  margin: 5px 5px 10px 5px;
  border-radius: 5px;
  background-color: white;
  height: 100px;
}
.uname {
  font-size: 32px;
  margin-left: 15px;
}
.imian {
  flex-grow: 1;
}
ul {
  padding: 5px;
}
li {
  display: flex;
  align-items: center;
  width: 100%;
  height: 50px;
  background-color: white;
  margin-top: 5px;
  font-size: 20px;
  border-radius: 5px;
}
.line {
  display: flex;
  justify-content: center;
}
.ibtn {
  margin-left: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 60px;
  height: 60px;
  text-align: center;
}

.avatar {
  width: 60px;
  height: 60px;
}

.el-dialog {
  width: 80%;
}
</style>
