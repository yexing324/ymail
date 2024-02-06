<script lang="ts" setup>
import {onBeforeMount, toRaw} from "vue";
import store from "@/store";
import router from "@/router";

let data
onBeforeMount(() => {
  //TODO:判断cookie状态
  //初始化菜单
  data = toRaw(store.getters.getData)
  //初始化数据

})
const handleNodeClick = (e: { label: any; }) => {
  let label = e.label
  switch (label) {
    case "收件箱":
      router.push('/receiveBox')
      break;
    case "已发送":
      router.push('/sentBox')
      break;
  }
}
const defaultProps = {
  children: 'children',
  label: 'label',
}
</script>

<template>
  <div style="height: 100vh;border-right: #c2d3f4 1px solid">
      <el-button type="primary" class="topItem">收信</el-button>
      <el-button type="primary" class="topItem">写信</el-button>
    <br/>


    <!--    <span class="topItem">收信</span>-->
    <!--    <span class="topItem">写信</span>-->


    <el-tree
        highlight-current
        :data="data"
        :props="defaultProps"
        @node-click="handleNodeClick">
    </el-tree>


  </div>

</template>

<style scoped>
.topItem {
  width: 100px;
  height: 40px;
  background: #ebf2ff;
  color: #3370ff;
  border-radius: 0;
  font-size: 18px;
  font-family: "等线",serif;
  border-right: 0;
  margin: 0;
}

.el-button--primary:hover {
  background: #d9e7ff;
}


/* 横线 */
.line {
  width: 150px;
  float: right;
  height: 100vh;
  margin-top: -0.5em;
  background: #d4c4c4;
  text-align: center;
}

</style>