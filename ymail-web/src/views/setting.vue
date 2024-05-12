<template>
  <div style="display: flex;flex-direction:column;height: 100vh;background: white">
    <div style="width: 100%;">
      <div style="padding: 20px 0 0 20px">
        <span class="ymail">YMail</span>
        <span class="freeMail">免费邮箱</span>

      </div>
      <el-divider class="line"></el-divider>
    </div>
    <!--     最上面设置结束-->
    <div style="display: flex;overflow:hidden;">
      <div style="flex-shrink:0">
       <setting-menu></setting-menu>

      </div>

      <div style="width: 100%;overflow:hidden;padding: 10px 0 0 10px">
        <router-view />
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
import {onBeforeMount, toRaw} from "vue";
import store from "../store/index";
import axios from "axios";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";
import LeftMenu from "@/components/Home/leftMenu.vue";
import SettingMenu from "@/components/setting/settingMenu.vue";
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


let data;
onBeforeMount(() => {
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
