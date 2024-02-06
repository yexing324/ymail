<template>
  <div style="display: flex;flex-direction:column;height: 100vh">
    <div style="width: 100%;flex: 1">
      <!--    上方设置-->
      <div>
        你好吗

      </div>
      <!--    任务栏-->
      <div>
        <el-menu
            ellipsis
            class="el-menu-popper-demo"
            mode="horizontal"
            :popper-offset="16"
            style="width: 100%"
        >
          <el-menu-item index="1">Processing Center</el-menu-item>
          <el-sub-menu index="2">
            <template #title>Workspace</template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
              <template #title>item four</template>
              <el-menu-item index="2-4-1">item one</el-menu-item>
              <el-menu-item index="2-4-2">item two</el-menu-item>
              <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="3" :popper-offset="8">
            <template #title>Override Popper Offset</template>
            <el-menu-item index="3-1">item one</el-menu-item>
            <el-menu-item index="3-2">item two</el-menu-item>
            <el-menu-item index="3-3">item three</el-menu-item>
            <el-sub-menu index="3-4" :popper-offset="20">
              <template #title>override child</template>
              <el-menu-item index="3-4-1">item one</el-menu-item>
              <el-menu-item index="3-4-2">item two</el-menu-item>
              <el-menu-item index="3-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="4" disabled>Info</el-menu-item>
          <el-menu-item index="5">Orders</el-menu-item>
        </el-menu>

      </div>

    </div>
    <br>
    <!--  最上面设置结束-->
    <div style="width: 100%;height: 100%;display: flex;flex: 9;overflow: hidden">
      <!--    设置左边部分-->
      <div style="width: 20%;height: 100%;float:left;flex: 1.3">

        <div>
          <el-tree
              highlight-current
              :data="data"
              :props="defaultProps"
              @node-click="handleNodeClick">
          </el-tree>
        </div>

      </div>
      <!--    左边设置完成，设置右边-->
      <div style="width: 80%;height: 100%;float:left;flex: 8.7;">
        <router-view></router-view>
      </div>

    </div>

  </div>


</template>

<script setup>
import router from "@/router";
import {onBeforeMount, toRaw} from "vue";
import store from "../store/index";

let data;
onBeforeMount(()=>{
  data=toRaw(store.getters.getData)
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


<style>
div {
  //box-shadow:0px 0px 0px 1px rebeccapurple inset;
}


//el-tree的样式设置
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