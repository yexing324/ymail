<script  setup>
import {defineComponent} from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import Cookies from "js-cookie";
let avatarName = Cookies.get("avatarName");


function logout(){
  router.push({
    path: '/login',
  })
  Cookies.set('cookie', "", {expires: 7});
  Cookies.set('mail', "", {expires: 7});
}

const setting = () => {
  router.push({
    path: '/userInfo',
  })
}
</script>

<template>
  <div style="display: flex;margin: 10px 0 10px 10px">
    <span class="ymail">YMail</span>
    <span class="freeMail">免费邮箱</span>
<!--    用户头像，点击可以选择退出账户
右浮动

-->
    <el-dropdown trigger="click" style="margin-left: 80%"  >
      <span class="el-dropdown-link">
        <img :src="avatarName" alt="" style="width: 40px;height: 40px;border-radius: 50%;margin-left: 20px">
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="setting">设置界面</el-dropdown-item>
        </el-dropdown-menu>
        <el-dropdown-menu>
          <el-dropdown-item @click="logout">退出账户</el-dropdown-item>
        </el-dropdown-menu>
      </template>
      </el-dropdown  >


  </div>
  <el-divider class="line"></el-divider>
<!--  <div class="line"></div>-->
</template>

<style scoped>
.ymail {
  font-size: 50px;
  color: red;
  font-family: "华文彩云", serif;
}

.freeMail {
  font-size: 40rpx;
  margin: auto 10px;
}

/* 横线 */
.line {
  width: 100%;
  height: 2px;
  background: #b0c5ee;
  margin: 0;
}

</style>
