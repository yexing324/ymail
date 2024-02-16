<script lang="ts">
import {defineComponent, onBeforeMount, onMounted} from 'vue'
import {ArrowDown} from "@element-plus/icons-vue";
import route from "@/router";
import axios from "axios";

export default defineComponent({
  name: "emailDetail",
  components: {ArrowDown},
  setup(){
    let id;
    let email;
    onBeforeMount(()=>{
        id=route.currentRoute.value.query.id
        axios.post("/api/email/readEMail",id).then(res=>{
          console.log(res)
        })
    })
  }
})

</script>

<template>
  <div style="display: flex">
    <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">删 除</el-button>
    <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">举 报</el-button>

    <el-dropdown ref="dropdown1" trigger="contextmenu" style="margin-left: 12px">
      <el-button  text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
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
</template>

<style scoped>

</style>