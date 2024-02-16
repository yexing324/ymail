<template>
  <div style="display: flex">
    <el-checkbox v-model="checked" label="" size="large" style="margin-left: 13px"/>
    <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">删 除</el-button>
    <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">举 报</el-button>

    <el-dropdown ref="dropdown1" trigger="contextmenu" style="margin-left: 12px">
      <el-button @click="showClick(1)"  text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
        标记为
        <el-icon><arrow-down/></el-icon>
      </el-button>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>已读</el-dropdown-item>
          <el-dropdown-item>未读</el-dropdown-item>
          <el-dropdown-item>全部设置为已读</el-dropdown-item>
          <el-dropdown-item disabled>待办邮件</el-dropdown-item>
          <el-dropdown-item disabled>标记邮件</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-dropdown ref="dropdown2" trigger="contextmenu" style="margin-left: 12px">
      <el-button @click="showClick(2)"  text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
        移动到
        <el-icon><arrow-down/></el-icon>
      </el-button>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>草稿箱</el-dropdown-item>
          <el-dropdown-item>已发送</el-dropdown-item>
          <el-dropdown-item>已删除</el-dropdown-item>
          <el-dropdown-item>垃圾邮件</el-dropdown-item>
          <el-dropdown-item divided>新建文件夹并移动</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-button text style=" border: 1px solid #b7bcc7; font-size: 13px;width: 60px;margin-left: 13px">刷新</el-button>

  </div>


  <el-scrollbar
      style="height: 100%;"
      class="scrollbar-for"
  >
    <!-- 内容部分 -->
    <el-table :data="data.tableData" :show-header="false" @rowClick="emailClick">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="nickname" label="发件人" width="150"/>
      <el-table-column prop="subject" label="主题" width="720"/>
      <el-table-column prop="updateTime" label="时间" width="360"/>
    </el-table>
    <div style="height: 100px;margin-top: 50px">你好</div>
  </el-scrollbar>

</template>


<script lang="ts" setup>

import {onBeforeMount, toRaw} from "vue";
import axios from "axios";
import {reactive} from "@vue/reactivity";
import {ArrowDown, Check, CircleCheck, CirclePlus, CirclePlusFilled, Plus} from "@element-plus/icons-vue";
import { ref } from 'vue'
import type { DropdownInstance } from 'element-plus'
import router from "@/router";

const dropdown1 = ref<DropdownInstance>()
const dropdown2 = ref<DropdownInstance>()
function handleVisible1(visible: any) {
  if (!dropdown1.value) return
  if (visible) {
    dropdown1.value.handleClose()
  } else {
    dropdown1.value.handleOpen()
  }
}

function handleVisible2(visible: any) {
  if (!dropdown2.value) return
  if (visible) {
    dropdown2.value.handleClose()
  } else {
    dropdown2.value.handleOpen()
  }
}
function showClick(e:any) {
  if(e==1){
    if (!dropdown1.value) return
    dropdown1.value.handleOpen()
  }else{
    if (!dropdown2.value) return
    dropdown2.value.handleOpen()
  }

}



let checked;
const data = reactive({
  tableData: []
})


onBeforeMount(() => {
  axios.get("/api/email/getMessage").then(e => {
    if (e.data.flag === true) {
      data.tableData = e.data.data
    }
  })

})
const emailClick=(e: any)=>{
  console.log()
  router.push({
    path:'/emailDetail',
    query:{
      id:toRaw(e).id
    }
  })
}


</script>
<style>

//滚动条设置
::-webkit-scrollbar {
  width: 8px;
  height: 16px;
  border-radius: 30px;
}

/*定义滚动条轨道
 内阴影+圆角*/
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
  scrollbar-width: none;
}

/*定义滑块
 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  background-color: #f2f2f2;

}
.el-popper {
  overflow: auto;
}

.el-popper.dropdownMenu {
  background-color: red;
}
</style>