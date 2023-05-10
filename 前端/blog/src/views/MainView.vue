<template>
  <div class="container">
    <div id="HomeBox">
      <router-view></router-view>
    </div>

    <div class="footer">
      <div id="home" class="focus" @click="change(0)">
        <HomeFilled />
        <span>首页</span>
      </div>
      <div class="int"></div>
      <div id="me" @click="change(1)">
        <UserFilled />
        <span>我的</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  mounted() {
    var location = window.location.href;
    if (location.substring(location.lastIndexOf("/")) === "me") {
      this.change(0);
    } else if (location.substring(location.lastIndexOf("/")) === "home") {
      this.change(1);
    }
  },
  methods: {
    change(val) {
      let me = document.getElementById("me");
      let home = document.getElementById("home");
      if (val === 0) {
        me.classList.remove("focus");
        home.classList.add("focus");
        this.$router.push("/main/home");
      } else {
        home.classList.remove("focus");
        me.classList.add("focus");
        this.$router.push("/main/me");
      }
    },
  },
};
</script>
<style>
.container {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #f2f2f5;
}
.footer {
  display: flex;
  justify-content: center;
  align-items: center;
  border-top: #f2f2f5 1px solid;
  height: 45px;
  background-color: white;
}

.int {
  width: 25%;
}

.focus {
  color: #0396ff;
}

#home,
#me {
  font-size: 12px;
}
#HomeBox {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 90%;
}
</style>
