<template>

  <el-dialog
      v-model="reportVisible"
      title="举报邮件"
      width="400"
  >


    <template #title>
      <h3 class="report">您举报的邮件类型为:</h3>
    </template>
    <template #default>
      <el-checkbox-group v-model="reportList">
        <div class="report">
          <el-checkbox label="仿冒邮件（仿冒各类通知）"/>
          <el-checkbox label="色情邮件"/>
        </div>
        <div class="report">
          <el-checkbox label="钓鱼邮件（诱导骗取信息）"/>
          <el-checkbox label="违法邮件"/>
        </div>
        <div class="report">
          <el-checkbox label="代开发票"/>
          <el-checkbox label="广告推广"/>
          <el-checkbox label="其他邮件"/>
        </div>
      </el-checkbox-group>
    </template>


    <template #footer>
      <div class="dialog-footer">
        <el-button @click="reportVisible = false">取消</el-button>
        <el-button type="primary" @click="reportVisible = false">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>


  <div style="display: flex">
    <el-button @click="deleteEmail" text class="btn"
               style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">删 除
    </el-button>
    <el-button @click="reportEmail" text class="btn"
               style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">举 报
    </el-button>

    <el-dropdown ref="dropdown1" trigger="contextmenu" style="margin-left: 12px">
      <el-button @click="showClick(1)" text class="btn"
                 style="  border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
        标记为
        <el-icon>
          <arrow-down/>
        </el-icon>
      </el-button>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="markRead">已读</el-dropdown-item>
          <el-dropdown-item @click="markNotRead">未读</el-dropdown-item>
          <el-dropdown-item @click="markAllRead">全部设置为已读</el-dropdown-item>
          <el-dropdown-item disabled>待办邮件</el-dropdown-item>
          <el-dropdown-item disabled>标记邮件</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-dropdown ref="dropdown2" trigger="contextmenu" style="margin-left: 12px">
      <el-button @click="showClick(2)" text class="btn"
                 style="  border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
        移动到
        <el-icon>
          <arrow-down/>
        </el-icon>
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
      style="height: 100%;text-align: left"
      class="scrollbar-for"
  >
    <!-- 内容部分 -->
    <el-table
        ref="table"
        :data="data.tableData"
        @rowClick="emailClick"
        @selection-change="select"
    >
      <el-table-column type="selection" width="30"/>
      <el-table-column width="50">
        <template #default="{ row }">
          <span v-if="row.statusText == '未读'">
            <el-icon style="margin-top: 6px;"><Message/></el-icon>
          </span>
        </template>
      </el-table-column>

      <el-table-column prop="nickname" label="发件人" width="150"/>
      <el-table-column prop="subject" label="主题" width="720"/>
      <el-table-column prop="updateTime" width="360"/>
    </el-table>
    <div style="height: 100px;margin-top: 50px">你好</div>
  </el-scrollbar>

</template>


<script lang="ts" setup>

import {onBeforeMount, toRaw} from "vue";
import axios from "axios";
import {reactive} from "@vue/reactivity";
import {ArrowDown, Message} from "@element-plus/icons-vue";
import {ref} from 'vue'
import {DropdownInstance, ElMessage} from 'element-plus'
import router from "@/router";

const dropdown1 = ref<DropdownInstance>()
const dropdown2 = ref<DropdownInstance>()
const table = ref()
let reportVisible = ref()
const reportList = ref(['', ''])


function showClick(e: any) {
  if (e == 1) {
    if (!dropdown1.value) return
    dropdown1.value.handleOpen()
  } else {
    if (!dropdown2.value) return
    dropdown2.value.handleOpen()
  }

}


let selectList: any;
const data = reactive({
  tableData: []
})
const getMessageList = () => {
  axios.get("/api/email/getSendBox").then(e => {
    if (e.data.flag === true) {
      data.tableData = e.data.data
    }
  })
}


onBeforeMount(() => {
  getMessageList();
})
const emailClick = (e: any) => {
  console.log()
  router.push({
    path: '/emailDetail',
    query: {
      id: toRaw(e).id
    }
  })
}

function reportEmail() {
  if (selectList == null || selectList.length == 0) {
    ElMessage.warning("您还没有选中")
    return
  }
  reportVisible.value = true;


}

function deleteEmail() {
  if (selectList == null || selectList.length == 0) {
    ElMessage.warning("您还没有选中")
    return
  }
  axios.post("/api/email/deleteEmail", selectList).then(e => {
    if (e.data.flag == true) {
      ElMessage.success("删除成功")
      //更新事件
      getMessageList();

    } else {
      ElMessage.error(e.data.message)
    }
  });
}

function select(e: any) {
  selectList = toRaw(e)
}
function ifNotSelect(){
  if (selectList == null || selectList.length == 0) {
    ElMessage.warning("您还没有选中")
    return true
  }
  return false
}

function markRead(){
  if(ifNotSelect()){
    return
  }
  for (let i = 0; i < selectList.length; i++) {
    if(selectList[i].statusText!="已读"){
      axios.post("/api/email/markRead",selectList).then(res=>{
        ElMessage.success("标记成功")
        getMessageList()
      })
      return
    }
  }
  ElMessage.warning("您选择的都是已读，请重新选择")

}
function markAllRead(){
  axios.post("/api/email/markAllRead").then(res=>{
    ElMessage.success("标记成功")
    getMessageList()
  })

}
function markNotRead(){
  if(ifNotSelect()){
    return
  }
  for (let i = 0; i < selectList.length; i++) {
    if(selectList[i].statusText!="未读"){
      axios.post("/api/email/markNotRead",selectList).then(res=>{
        ElMessage.success("标记成功")
        getMessageList()
      })
      return
    }
  }
  ElMessage.warning("您选择的都是未读，请重新选择")

}


</script>
<style>


.el-popper {
  overflow: auto;
}

.el-popper.dropdownMenu {
  background-color: red;
}

.report {
  text-align: left
}
</style>