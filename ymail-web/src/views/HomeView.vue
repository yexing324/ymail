<template>
  <div style="display: flex;flex-direction:column;height: 100vh">
    <div style="width: 100%;">

      <Title @test="test"></Title>

    </div>
<!--     最上面设置结束-->
    <div style="display: flex;overflow:hidden;">
      <div style="flex-shrink:0">
        <left-menu></left-menu>
      </div>

      <div style="width: 100%;overflow:hidden;">
        <router-view></router-view>
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

const test = () => {
  console.log("123")
}

let data;
onBeforeMount(() => {
  //TODO:判断cookie状态
  //初始化菜单
  data = toRaw(store.getters.getData)
  //初始化数据

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