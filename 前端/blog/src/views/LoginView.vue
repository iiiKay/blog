<template>
  <div class="box">
    <img src="../../public/img/panda.svg" alt="panda" />
    <span>用户登录</span>
  </div>
  <div class="formbox">
    <div class="inputbox">
      <el-input
        v-model="name"
        maxlength="10"
        placeholder="请输入用户名"
        prefix-icon="User"
      ></el-input>
    </div>
    <div class="inputbox">
      <el-input
        v-model="pwd"
        type="password"
        placeholder="请输入密码"
        maxlength="16"
        show-password
        prefix-icon="Lock"
      ></el-input>
    </div>
    <div class="btnbox">
      <el-button type="primary" class="btn" @click="login">登录</el-button>
    </div>
    <div class="rbox">
      <a @click="isShow = true">注册账号</a>
    </div>
    <el-dialog title="注册" v-model="isShow" :show-close="closebtn">
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
        <el-button type="primary" @click="register">注册</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import axios from "axios";
export default {
  data() {
    return {
      name: "",
      pwd: "",
      isShow: false,
      closebtn: false,
      form: {
        name: "",
        pwd: "",
      },
    };
  },
  methods: {
    login() {
      if (this.name === "") {
        ElMessage({
          type: "warning",
          message: "请输入用户名",
          grouping: true,
        });
      } else if (this.pwd === "") {
        ElMessage({
          type: "warning",
          message: "请输入密码",
          grouping: true,
        });
      } else if (this.pwd.trim().length < 4) {
        ElMessage({
          type: "warning",
          message: "密码至少为4位",
          grouping: true,
        });
      } else {
        const json = {
          name: this.name,
          pwd: this.pwd,
        };
        let type = "error";
        this.axios.post("/user/login", json).then((res) => {
          if (res.data.code === 200) {
            type = "success";
            localStorage.setItem("token", res.data.item.token);
            this.axios.defaults.headers.common["Authorization"] =
              localStorage.getItem("token");
            this.$router.push("/main/home");
          }
          ElMessage({
            type: type,
            message: res.data.msg,
            goruping: true,
          });
        });
      }
    },
    register() {
      if (this.form.name === "") {
        ElMessage({
          type: "warning",
          message: "请输入用户名",
          grouping: true,
        });
      } else if (this.form.pwd === "") {
        ElMessage({
          type: "warning",
          message: "请输入密码",
          grouping: true,
        });
      } else if (this.form.pwd.trim().length < 4) {
        ElMessage({
          type: "warning",
          message: "密码至少为4位",
          grouping: true,
        });
      } else {
        const json = {
          name: this.form.name,
          pwd: this.form.pwd,
        };
        let type = "error";
        this.axios.post("/user/register", json).then((res) => {
          if (res.data.code === 200) {
            type = "success";
            this.form.name = "";
            this.form.pwd = "";
            this.isShow = false;
          }
          ElMessage({
            type: type,
            message: res.data.msg,
            goruping: true,
          });
        });
      }
    },
  },
  watch: {
    isShow(val) {
      if (val === false) {
        this.form.name = "";
        this.form.pwd = "";
      }
    },
  },
  mounted() {
    axios
      .get("/user/verify", {
        params: { token: localStorage.getItem("token") },
      })
      .then((res) => {
        if (res.data.code === 200) {
          this.$router.push("/main");
        }
      });
  },
};
</script>
<style scoped>
.box {
  width: 100%;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: linear-gradient(135deg, #abdcff 10%, #0396ff 100%);
}

.box img {
  width: 150px;
  height: 150px;
}

.box span {
  color: white;
  font-size: 28px;
  font-weight: 100;
}

.formbox {
  margin: auto;
  width: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.inputbox {
  margin-top: 40px;
  width: 100%;
}

.btnbox {
  margin-top: 40px;
}

.btn {
  width: 200px;
  height: 40px;
}

.rbox {
  margin-top: 40px;
  color: #0396ff;
}

:deep(.el-input__wrapper) {
  box-shadow: none;
  border-radius: 0%;
  border-bottom: gray 1px solid;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: none;
}
:deep(.el-input__wrapper.is-focus) {
  box-shadow: none;
  border-bottom: #0396ff 1px solid;
}

:deep(.el-dialog) {
  width: 80%;
}
</style>
