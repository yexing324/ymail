<template>
  <div style="display: flex;flex-direction:column;height: 100vh;background: white">
    <div style="width: 100%;">
      <div style="padding: 20px 0 0 20px">
        <span class="ymail" @click="returnHome">YMail</span>
        <span class="freeMail">免费邮箱</span>

        <el-dropdown trigger="click" style="margin-left: 80%">
      <span class="el-dropdown-link">
        <img :src="avatarName" alt="" style="width: 40px;height: 40px;border-radius: 50%;margin-left: 20px">
      </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="logout">退出账户</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </div>
      <el-divider class="line"></el-divider>
    </div>
    <!--     最上面设置结束-->
    <div style="display: flex;overflow:hidden;">
      <div style="flex-shrink:0">
        <setting-menu></setting-menu>

      </div>

      <div style="width: 100%;overflow:hidden;padding: 10px 0 0 10px">
        <router-view/>
      </div>

      <!--      &lt;!&ndash;内部将变成上下布局&ndash;&gt;-->
      <!--      <div style="display: inline-block;>-->
      <!--        <router-view></router-view>-->
      <!--      </div>-->

    </div>

  </div>


</template>

<script setup>
import router from "@/router";
import {onBeforeMount, ref, toRaw} from "vue";
import store from "../store/index";
import axios from "axios";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";
import LeftMenu from "@/components/Home/leftMenu.vue";
import SettingMenu from "@/components/setting/settingMenu.vue";
import eventBus from "@/assets/util/eventBus";

let avatarName = ref('')

let menu1 = [
  {
    label: '收件箱',
  },
  {
    label: '星标邮件',
  },
  {
    label: '草稿箱',
  },
  {
    label: '已发送',
  },

  {
    label: '待办邮件',
  },
]

const test = () => {
  console.log("123")
}

function returnHome() {
  router.push({
    path: '/home',
  })
}


let data;
onBeforeMount(() => {
  avatarName.value=Cookies.get("avatarName");
  //初始化菜单
  data = toRaw(store.getters.getData)
  //初始化数据
  let cookie = Cookies.get("cookie")
  if (cookie === "") {
    ElMessage.warning("请先登录")
    router.push({
      path: '/login',
    })
  }

  eventBus.on("updateImg",()=>{
    avatarName.value=Cookies.get("avatarName")
  })


})

//在这里进行路由跳转
const handleNodeClick = (e) => {
  let label = e.label
  switch (label) {
    case "收件箱":
      router.push('/receiveBox')
      break;
    case "发件箱":
      router.push('/sentBox')
      break;
  }
}

const append = (type, data, node) => {
  console.log(type, 11111);
}

const defaultProps = {
  children: 'children',
  label: 'label',
}
function logout(){
  router.push({
    path: '/login',
  })
  Cookies.set('cookie', "", {expires: 7});
  Cookies.set('mail', "", {expires: 7});
}
</script>


<style scoped>

.el-tree-node:focus > .el-tree-node__content {
  background-color: #ebf2ff !important;
}

.el-tree-node__content:hover {
  background-color: #ebf2ff;
}

.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #ebf2ff;
}

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
