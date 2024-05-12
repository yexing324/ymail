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
  <el-dialog
      v-model="createFolderAndMoveEmail"
      title="创建文件夹并移动"
      width="400"
  >


    <template #title>
      <h3 class="report">新建文件夹:</h3>
    </template>
    <template #default>
      <span>输入文件夹名称:
      <el-input v-model="commonData.createEmailFolder" style="width: 250px"/>
      </span>
    </template>


    <template #footer>
      <div class="dialog-footer">
        <el-button @click="createFolderAndMoveEmail = false">取消</el-button>
        <el-button type="primary" @click="createFolderAndMoveEmailSubmit()">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>


  <div style="overflow:hidden;">
    <!--    左边的按钮簇-->
    <div style="float: left">
      <div style="float: left;margin: 0 10px 0 10px">
        <el-checkbox
            :indeterminate="isIndeterminate"
            @click="allCheckChange"
            v-model="allCheck"
            size="large"/>
      </div>


      <el-button @click="deleteEmail" text class="btn"
                 style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">删 除
      </el-button>
      <el-button @click="reportEmail" text class="btn"
                 style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">举 报
      </el-button>

      <el-dropdown ref="dropdown1" trigger="contextmenu" style="margin-left: 12px">
        <el-button @click="showClick(1)" text class="btn"
                   style="border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
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
            <el-dropdown-item>
              <el-dropdown placement="right-start">
          <span>
            标记为&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>
          </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="markEmailColor('green')" class="green_class">绿色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('orange')" class="orange_class">橙色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('blue')" class="blue_class">蓝色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('pink')" class="pink_class">粉色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('indigo')" class="indigo_class">青色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('yellow')" class="yellow_class">黄色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('purple')" class="purple_class">紫色</el-dropdown-item>
                    <el-dropdown-item @click="markEmailColor('grey')" class="grey_class">灰色</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </el-dropdown-item>
            <el-dropdown-item @click="markEmailColor('black')">取消标记</el-dropdown-item>
            <el-dropdown-item @click="setEmailsPinned">置顶邮件</el-dropdown-item>
            <el-dropdown-item @click="cancelSetEmailsPinned">取消置顶</el-dropdown-item>
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
            <el-dropdown-item v-if="group!='收件箱'" @click="moveEmailGroup('收件箱')">收件箱</el-dropdown-item>
            <el-dropdown-item v-if="group!='草稿箱'" @click="moveEmailGroup('草稿箱')">草稿箱</el-dropdown-item>
            <el-dropdown-item v-if="group!='已发送'" @click="moveEmailGroup('已发送')">已发送</el-dropdown-item>
            <el-dropdown-item v-if="group!='已删除'" @click="moveEmailGroup('已删除')">已删除</el-dropdown-item>
            <el-dropdown-item v-if="group!='垃圾邮件'" @click="moveEmailGroup('垃圾邮件')">垃圾邮件</el-dropdown-item>
            <el-dropdown-item>
              <el-dropdown placement="right-start">
          <span>
            我的文件夹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>
          </span>
                <template #dropdown>
                  <el-dropdown-menu v-for="(item) in commonData.groupList">
                    <el-dropdown-item v-if="group!=item.name" @click="moveEmailGroup(item.name)">{{ item.name }}</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </el-dropdown-item>
            <el-dropdown-item @click="createFolderAndMoveEmail=true">新建文件夹并移动</el-dropdown-item>
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

      <el-button-group style="margin-top: 0;">
        <el-button style="border-radius:20px 0 0 20px" @click="pageChange(-1)" :disabled="currentPage==1"
                   :icon="ArrowLeft"></el-button>
        <el-button style="border-radius:0 20px 20px 0" @click="pageChange(+1)" :disabled="currentPage==pages">
          <el-icon class="el-icon--right">
            <ArrowRight/>
          </el-icon>
        </el-button>
      </el-button-group>
      &nbsp;

      <el-button @click="btn" style="border-radius: 60px;border: 1px solid grey;width: 20px;margin-right: 5px;">
        <el-icon size="20px">
          <Setting/>
        </el-icon>
      </el-button>

    </div>


  </div>


  <el-scrollbar
      style="height: 100%;text-align: center"
  >
    <div :hidden="!ifNullHidden" style="min-height: 400px">
      <template v-for="(item ,index) in data">
        <div v-if="item.length!=0">
          <div v-if="group=='收件箱'" style="text-align: left;margin: 9px 0 3px 9px;font-size: 13px;">
            <span v-if="index==0">置顶</span>
            <span v-if="index==1">今天</span>
            <span v-if="index==2">昨天</span>
            <span v-if="index==3">更早</span>
            <el-divider class="line" style="margin-top: 3px;"></el-divider>
          </div>


          <el-table
              :ref="setRef(index)"
              :data="item"
              @rowClick="emailClick"
              @row-contextmenu="row_rightClick($event)"
              @contextmenu="rightClick($event)"
              style="text-align: left"
              :show-header="false"
              @selectionChange="selectionChange(index,$event)"
              :row-class-name="rowStyle"
          >
            <el-table-column type="selection" width="30"/>

            <el-table-column width="50">
              <template #default="{ row }">
                <!--                          <span v-if="row.statusText == '未读'">-->
                <!--                            <el-icon style="margin-top: 6px;"><Message/></el-icon>-->
                <!--                          </span>-->
              </template>
            </el-table-column>

            <el-table-column prop="nickname" label="发件人" width="150"/>
            <el-table-column prop="subject" label="主题" width="150"/>
            <el-table-column prop="plainText" label="内容" style="overflow: hidden;height: 200px" class="notEnter"/>
            <el-table-column prop="createTime" width="360"/>

          </el-table>
        </div>
      </template>

    </div>

    <div
      :hidden="ifNullHidden"
    >
      <img src="../../assets/null.png" alt="还没有邮件哦">
      <div style="color: grey">还没有邮件哦</div>
    </div>


    <div style="height: 100px;margin-top: 20px;">
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


    <!-- 内容部分 -->


  </el-scrollbar>


</template>


<script lang="ts" setup>

import {onBeforeMount, toRaw} from "vue";
import axios from "axios";
import {ArrowDown, ArrowLeft, ArrowRight, Setting} from "@element-plus/icons-vue";
import {ref} from 'vue'
import {DropdownInstance, ElMessage, ElTable, Table} from 'element-plus'
import router from "@/router";
import {shallowRef} from "vue";
import {menusEvent} from 'vue3-menus';
import route from "@/router";

//存放几个table的实例
const refsMap = ref({} as any);

//没有邮件图片是否隐藏
const ifNullHidden = ref(true)
import eventBus from '@/assets/util/eventBus'
import _ from "lodash";

/**
 * 设置table的实例
 * @param index 索引
 */
function setRef(index: any) {
  return (el: any) => {
    if (el) {
      refsMap.value[`name${index}`] = el;
    }
  };
}

function btn() {
  console.log(selectOriginList)
}

/**
 * 未知的变量
 */
const dropdown1 = ref<DropdownInstance>()
const dropdown2 = ref<DropdownInstance>()
const dropdown3 = ref<DropdownInstance>()
/**
 * 下面三个是选择框相关的
 */
const allCheck = ref(false)
const isIndeterminate = ref(false)
/**
 * 单个邮件复选框被点击的逻辑
 * @param index 当前table索引
 * @param item 当前数据
 * 解释一下为什么不使用一个list
 * 因为每一个table都会返回一个当前的选中数据，直接赋值是比较方便的，如果要进行增删，需要遍历判断
 * 如果节省性能的话，也可以使用之前调用getSelectList()
 */
let selectOriginList = [[], [], [], []];
let selectList: any[] = []

function selectionChange(index: Number | any, item: any) {
  selectOriginList[index] = toRaw(item)
  let sum = 0;
  Object.values(selectOriginList).forEach(list => {
    sum += list.length
  });
  if (sum == currentTotal.value) {
    isIndeterminate.value = false;
    allCheck.value = true;
    return;
  }
  if (sum == 0) {
    isIndeterminate.value = false;
    allCheck.value = false
    return;
  }
  isIndeterminate.value = true;
  allCheck.value = false
  getSelectList()
}

/**
 * 从selectOriginList更新selectList列表
 */
function getSelectList() {
  selectList = []
  Object.values(selectOriginList).forEach(list => {
    //时间复杂度为4*n,O(n)=n
    list.forEach(item => {
      selectList.push(item);
    })
  });
}

/**
 * 全选复选框的点击事件
 */
function allCheckChange() {
  if (allCheck.value == false) {
    //非全选变全选
    isIndeterminate.value = false;
    allCheck.value = true
    Object.values(refsMap.value).forEach((list: any) => {
      if (list) {
        list.data.forEach((row: any) => {
          list.toggleRowSelection(row, true); // 全选
        })
      }
    });
  } else if (allCheck.value == true) {
    //全选变不选
    allCheck.value = false;
    isIndeterminate.value = false;
    Object.values(refsMap.value).forEach((list: any) => {
      if (list) {
        list.clearSelection(); // 清空输入框
      }
    });
  }
  allCheck.value = !allCheck.value
  getSelectList()
}


//表格数据
const data = ref([] as any)
const checkList = ref([] as any[])

let reportVisible = ref()
let commonData = ref(
    {
      createEmailFolder: "",
      groupList:[]
    }
)
let createFolderAndMoveEmail = ref()
const report = ref()
const currentRightClick = ref()
let group: any;
let isRightReport = ref(false)

/**
 * 举报邮件提交函数
 */
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


/**
 * 页码相关的
 */
let currentPage = ref()
let totalPages = ref()
let pages = ref()
let size = ref(20)
let currentTotal = ref(0)

function getMessageList(flag = false, page = 1, size = 20) {
  axios.get("/api/email/getEmailByGroup?group=" + group + "&page=" + page + "&size=" + size).then(res => {
    if (res.data.flag === true) {
      if (group == "收件箱") {
        data.value[0] = (res.data.data.data.pinnedEmailList)
        data.value[1] = (res.data.data.data.todayEmailList)
        data.value[2] = (res.data.data.data.yesterdayEmailLis)
        data.value[3] = (res.data.data.data.previousEmailList)
        currentTotal.value = res.data.data.currentTotal
      } else {
        data.value[0] = (res.data.data.records)
        currentTotal.value = res.data.data.total
      }
      if(currentTotal.value!=0){
        ifNullHidden.value=true
      }else{
        ifNullHidden.value=false
      }
      pages.value = res.data.data.pages == 0 ? 1 : res.data.data.pages
      currentPage.value = res.data.data.current

      if (flag) {
        ElMessage.success("刷新成功")
      }
    }else{
      router.push({
        path: '/login',
      })
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
  data.value = [[], [], [], []]
  getMessageList()
  getGroupList()
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
      axios.post("/api/email/markRead", selectList).then(() => {
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
      axios.post("/api/email/markNotRead", selectList).then(() => {
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
        axios.post("/api/email/markNotRead", dataList).then(() => {
          ElMessage.success("标记成功")
          getMessageList()
        })
      }
    },
    {
      label: "置顶邮件",
      click: () => {
        selectList.push(toRaw(currentRightClick.value))
        setEmailsPinned()
      }
    },
    {
      label: "取消置顶",
      click: () => {
        selectList.push(toRaw(currentRightClick.value))
        cancelSetEmailsPinned()
      }
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

/**
 * 设置邮件置顶
 */
function setEmailsPinned() {
  if (ifNotSelect()) return
  axios.post("/api/email/setEmailPinned?group=" + group, selectList).then(e => {
    if (e.data.flag == true) {
      ElMessage.success("置顶成功")
      getMessageList();
    } else {
      ElMessage.error(e.data.message)
    }
  })
}

function cancelSetEmailsPinned() {
  if (ifNotSelect()) return
  axios.post("/api/email/cancelSetEmailPinned?group=" + group, selectList).then(e => {
    if (e.data.flag == true) {
      ElMessage.success("取消成功")
      getMessageList();
    } else {
      ElMessage.error(e.data.message)
    }
  })
}

const options = [
  {
    value: 'guide',
    label: 'Guide',
    children: [
      {
        value: 'disciplines',
        label: 'Disciplines',
      },
    ],
  },

]

/**
 * 标记邮件颜色
 * @param color
 */
function markEmailColor(color: string) {
  if (ifNotSelect()) return
  axios.post("/api/email/markEmailColor?color=" + color, selectList).then(e => {
    if (e.data.flag == true) {
      if (color == "black")
        ElMessage.success("取消成功")
      else
        ElMessage.success("标记成功")
      getMessageList();
    } else {
      ElMessage.error(e.data.message)
    }
  })
}

/**
 * 判断当前的颜色
 * @param row
 * @param rowIndex
 */

function rowStyle(row: any) {
  let color = toRaw(row.row).color
  if (typeof (color) == "undefined") {
    return "black_class";
  }
  return color + "_class";
}

/**
 * 创建文件夹并移动
 */
function createFolderAndMoveEmailSubmit() {
  createFolderAndMoveEmail.value = false
  if (commonData.value.createEmailFolder == "") {
    ElMessage.warning("请输入要创建的文件夹哦")
    return
  }
  axios.post("/api/email/createEmailFolder?group=" + commonData.value.createEmailFolder, selectList).then(e => {
    if (e.data.flag == true) {
      ElMessage.success("创建成功")
      getMessageList();
      eventBus.emit('updateGroup');
    } else {
      ElMessage.error(e.data.message)
    }
  })
}

function getGroupList() {
  axios.get("/api/email/getGroupList").then(e => {
    commonData.value.groupList=e.data.data
  })
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

.myCell .el-checkbox__input {
  display: none !important;
}

.notEnter {
  white-space: nowrap;
  display: inline-block
}

.title {
  margin: 20px 0 20px 0;
  text-align: left;
  display: inline-block;
  font-size: 20px;
  width: 100%;
}

.line {
  width: 98%;
  height: 0.5px;
  background: #c2d3f4;
  margin: 0;
}

.black_class {
  color: black;
}

.green_class {
  color: green;
}

.orange_class {
  color: orange;
}

.blue_class {
  color: blue;
}

.pink_class {
  color: pink;
}

.indigo_class {
  color: indigo;
}

.yellow_class {
  color: #b7b73e;
}

.purple_class {
  color: purple;
}

.grey_class {
  color: grey;
}
</style>