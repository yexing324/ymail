<template>
  <div style="display: flex;flex-direction:column;height: 100vh;background: white">
    <div style="width: 100%;">

      <Title @test="test"></Title>


    </div>
    <!--     最上面设置结束-->
    <div style="display: flex;overflow:hidden;">
      <div style="flex-shrink:0">
        <left-menu></left-menu>
      </div>

      <div style="width: 100%;overflow:hidden;padding: 10px 0 0 10px">
        <router-view :key="this.$route.fullPath"></router-view>
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
import Title from "@/components/Home/title.vue";
import LeftMenu from "@/components/Home/leftMenu.vue";
import axios from "axios";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";


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

</style>
