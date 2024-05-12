<script lang="ts" setup>
import {onBeforeMount, ref, toRaw} from "vue";
import store from "@/store";
import router from "@/router";
import {EditPen, MessageBox} from "@element-plus/icons-vue";
import axios from "axios";
import _ from 'lodash'
import eventBus from "@/assets/util/eventBus"

const data = ref({
  menu1: [
    {
      label: '个人信息',
    },
    {
      label: '群组管理',
    },
    {
      label: '数据统计',
    }
  ]
})

onBeforeMount(() => {


})

const handleNodeClick = (e: { label: any; }) => {
  let label = e.label
  console.log(label)
  switch (label) {
    case "个人信息":
      router.push({
        path: '/userInfo',
      })
      break;
    case "群组管理":
      router.push({
        path: '/groupManage',
      })
      break;
    case "数据统计":
      router.push({
        path: '/dataStatistics',
      })
      break;
  }
}
const defaultProps = {
  children: 'children',
  label: 'label',
}
</script>

<template>
  <div>
    <div style="height: 100vh;border-right: #c2d3f4 1px solid;text-align: left;float: left">

      <el-tree
          class="topItem"
          style="margin-top:20px ;"
          highlight-current
          :data="data.menu1"
          :props="defaultProps"
          @node-click="handleNodeClick">
      </el-tree>


    </div>

  </div>


</template>

<style scoped>
.topItem {
  width: 200px;
  height: 80px;
  color: grey;
  font-size: 16px;
  margin: 10px 0 10px 0;
}
</style>