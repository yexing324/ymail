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
      <el-radio-group v-model="report">
        <div class="report">
          <el-radio label="仿冒邮件（仿冒各类通知）"/>
          <el-radio label="色情邮件"/>
        </div>
        <div class="report">
          <el-radio label="钓鱼邮件（诱导骗取信息）"/>
          <el-radio label="违法邮件"/>
        </div>
        <div class="report">
          <el-radio label="代开发票"/>
          <el-radio label="广告推广"/>
          <el-radio label="其他邮件"/>
        </div>
      </el-radio-group>
    </template>


    <template #footer>
      <div class="dialog-footer">
        <el-button @click="reportVisible = false">取消</el-button>
        <el-button type="primary" @click="reportSubmit()">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>


  <div style="overflow:hidden;">
    <!--    左边的按钮簇-->
    <div style="float: left">
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
            <el-dropdown-item @click="moveEmailGroup('草稿箱')">草稿箱</el-dropdown-item>
            <el-dropdown-item @click="moveEmailGroup('已发送')">已发送</el-dropdown-item>
            <el-dropdown-item @click="moveEmailGroup('已删除')">已删除</el-dropdown-item>
            <el-dropdown-item @click="moveEmailGroup('垃圾邮件')">垃圾邮件</el-dropdown-item>
            <el-dropdown-item @click="moveEmailGroup('')" divided>新建文件夹并移动</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <el-button @click="refresh" text
                 style=" border: 1px solid #b7bcc7; font-size: 13px;width: 60px;margin-left: 13px">
        刷新
      </el-button>
    </div>
    <!--    右边的按钮簇-->
    <div style="float: right">
      <el-dropdown ref="dropdown3" trigger="contextmenu" style="margin-left: 12px;">
        <el-button @click="showClick(3)" text class="btn"
                   style="  border: 0 solid #b7bcc7; font-size: 13px;width: 80px;margin-right: 5px;">
          {{ currentPage }}/{{ pages }}&nbsp;&nbsp;
          <el-icon>
            <arrow-down/>
          </el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu v-for="index in pages" style="width: 150px">
            <el-dropdown-item @click="getMessageList(false,index,size)">{{ index }}/{{ pages }}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <el-button-group style="margin-top: 0px;">
        <el-button style="border-radius:20px 0 0 20px" @click="pageChange(-1)" :disabled="currentPage==1"
                   :icon="ArrowLeft"></el-button>
        <el-button style="border-radius:0 20px 20px 0" @click="pageChange(+1)" :disabled="currentPage==pages">
          <el-icon class="el-icon--right">
            <ArrowRight/>
          </el-icon>
        </el-button>
      </el-button-group>
      &nbsp;

      <el-button style="border-radius: 60px;border: 1px solid grey;width: 20px;margin-right: 5px;">
        <el-icon size="20px">
          <Setting/>
        </el-icon>
      </el-button>

    </div>


  </div>


  <el-scrollbar
      style="height: 100%;text-align: center"
  >
    <!-- 内容部分 -->
    <el-table
        ref="table"
        :data="data.tableData"
        @rowClick="emailClick"
        @selection-change="select"
        @row-contextmenu="row_rightClick($event)"
        @contextmenu="rightClick($event)"
        style="min-height: 500px;text-align: left"

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
      <el-table-column prop="subject" label="主题" width="150"/>
      <el-table-column prop="plainText" label="内容" style="overflow: hidden;height: 200px" class="notEnter" />
      <el-table-column prop="updateTime" width="360"/>

    </el-table>
    <div style="height: 100px;margin-top: 20px">
      <el-button-group>
        <el-button @click="pageChange(-1)" :disabled="currentPage==1" :icon="ArrowLeft">上一页</el-button>
        <el-button @click="pageChange(+1)" :disabled="currentPage==pages">
          下一页
          <el-icon class="el-icon--right">
            <ArrowRight/>
          </el-icon>
        </el-button>
      </el-button-group>
    </div>


  </el-scrollbar>


</template>


<script lang="ts" setup>

import {onBeforeMount, toRaw} from "vue";
import axios from "axios";
import {reactive} from "@vue/reactivity";
import {ArrowDown, ArrowLeft, ArrowRight, Message, Setting} from "@element-plus/icons-vue";
import {ref} from 'vue'
import {DropdownInstance, ElMessage} from 'element-plus'
import router from "@/router";
import {shallowRef} from "vue";
import {menusEvent} from 'vue3-menus';
import route from "@/router";

const dropdown1 = ref<DropdownInstance>()
const dropdown2 = ref<DropdownInstance>()
const dropdown3 = ref<DropdownInstance>()
const table = ref()
let reportVisible = ref()
const report = ref()
const currentRightClick = ref()
let group: any;
let isRightReport=ref(false)

function reportSubmit() {
  if (report.value == null || report.value == "") {

    ElMessage.warning("您没有选择原因")
    return
  }
  //默认选择列表
  reportVisible.value = false
  let formData = []
  if (isRightReport.value) {
    isRightReport.value = false
    formData.push(currentRightClick.value)
  } else {
    formData = selectList
  }
  axios.post("/api/email/reportEmail?reason=" + report.value, formData).then(res => {
    if (res.data.flag == true) {
      ElMessage.success("举报成功，我们将尽快给您反馈")
    }
  })
}

function refresh() {
  getMessageList(true)
}

function pageChange(num: any) {
  getMessageList(false, currentPage.value + num, size.value)
}

function showClick(e: any) {
  if (e == 1) {
    if (!dropdown1.value) return
    dropdown1.value.handleOpen()
  } else if (e == 2) {
    if (!dropdown2.value) return
    dropdown2.value.handleOpen()
  } else if (e == 3) {
    if (!dropdown3.value) return
    dropdown3.value.handleOpen()
  }


}


let selectList: any;
const data = reactive({
  tableData: [],
})
let currentPage = ref()
let totalPages = ref()
let pages = ref()
let size = ref(20)


function getMessageList(flag = false, page = 1, size = 20) {
  axios.get("/api/email/getEmailByGroup?group=" + group + "&page=" + page + "&size=" + size).then(res => {
    if (res.data.flag === true) {
      ({records: data.tableData} = res.data.data);
      pages.value = res.data.data.pages == 0 ? 1 : res.data.data.pages
      currentPage.value = res.data.data.current
      if (flag) {
        ElMessage.success("刷新成功")
      }
    }
  })
}

const moveEmailGroup = (group: any) => {
  if (ifNotSelect()) {
    return
  }
  axios.post("/api/email/moveEmailGroup?group=" + group, selectList).then(res => {
    if (res.data.flag == true) {
      ElMessage.success("移动成功")
      getMessageList();
    }
  })
}


onBeforeMount(() => {
  group = route.currentRoute.value.query.group
  getMessageList()
})

const emailClick = (e: any) => {
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

function ifNotSelect() {
  if (selectList == null || selectList.length == 0) {
    ElMessage.warning("您还没有选中")
    return true
  }
  return false
}

function markRead() {
  if (ifNotSelect()) {
    return
  }
  for (let i = 0; i < selectList.length; i++) {
    if (selectList[i].statusText != "已读") {
      axios.post("/api/email/markRead", selectList).then(res => {
        ElMessage.success("标记成功")
        getMessageList()
      })
      return
    }
  }
  ElMessage.warning("您选择的都是已读，请重新选择")

}

function markAllRead() {
  axios.post("/api/email/markAllRead").then(res => {
    ElMessage.success("标记成功")
    getMessageList()
  })

}

function markNotRead() {
  if (ifNotSelect()) {
    return
  }
  for (let i = 0; i < selectList.length; i++) {
    if (selectList[i].statusText != "未读") {
      axios.post("/api/email/markNotRead", selectList).then(res => {
        ElMessage.success("标记成功")
        getMessageList()
      })
      return
    }
  }
  ElMessage.warning("您选择的都是未读，请重新选择")

}

function row_rightClick(event: any) {
  currentRightClick.value = toRaw(event)
}


function rightClick(event: any) {
  const target = event.target;
  const header = target.closest('.el-table__header');
  if (header) {
    //点击的表格头部
    return;
  }
  event.preventDefault();
  menusEvent(event, menus.value, 1);
}

const menus = shallowRef({
  menus: [
    {
      label: "设置待办",
      click: () => {
        if (currentRightClick.value == null) {
          ElMessage.warning("您还没有选择哦")
          return
        }
        let dataList = []
        dataList.push(toRaw(currentRightClick.value))
        axios.post("/api/email/moveEmailGroup?group=" + "待办邮件", dataList).then(res => {
          if (res.data.flag == true) {
            ElMessage.success("移动成功")
            getMessageList();
          }
        })
      }
    },
    {
      label: "设为未读",
      click: () => {
        if (currentRightClick.value == null) {
          ElMessage.warning("您还没有选择哦")
          return
        }
        let dataList = []
        dataList.push(toRaw(currentRightClick.value))
        axios.post("/api/email/markNotRead", dataList).then(res => {
          ElMessage.success("标记成功")
          getMessageList()
        })
      }
    },
    {
      label: "置顶邮件",
      disabled: true
    },
    {
      label: "添加备注",
      click: () => location.reload(),
      divided: true
    },
    {
      label: "删除邮件",
      click: () => {
        if (currentRightClick.value == null) {
          ElMessage.warning("您还没有选择哦")
          return
        }
        let dataList = []
        dataList.push(toRaw(currentRightClick.value))
        axios.post("/api/email/deleteEmail", dataList).then(e => {
          if (e.data.flag == true) {
            ElMessage.success("删除成功")
            getMessageList();
          } else {
            ElMessage.error(e.data.message)
          }
        })
      }
    },
    {
      label: "举报垃圾邮件",
      click: () => {
        if (currentRightClick.value == null) {
          ElMessage.warning("您还没有选择哦")
          return
        }
        isRightReport.value = true;
        reportVisible.value = true
      }
    }
  ]
})


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
.myCell .el-checkbox__input {
  display: none !important;
}
.notEnter{
  white-space: nowrap; display: inline-block
}
</style>