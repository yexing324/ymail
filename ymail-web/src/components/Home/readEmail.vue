<script lang="ts" setup>
import {defineComponent, onBeforeMount, onMounted, ref} from 'vue'
import {ArrowDown} from "@element-plus/icons-vue";
import route from "@/router";
import axios from "axios";
import {reactive} from "@vue/reactivity";
import 'deep-chat'
let drawer = ref(false)

let id;
let data = reactive({
  email: {
    createTime: "2024-02-17 11:12:31",
    updateTime: "2024-02-17 11:12:31",
    delFlag: 0,
    id: "1758690859827875841",
    nickname: "yexing",
    from: "yexing@pmail.slovety.top",
    to: "yexing195@163.com",
    subject: "你好",
    plainText: "hello world",
    htmlText: "",
    status: -4,
    group: null,
    attachmentId: "6b3967c7-719c-4807-a409-bbf15a187e6c",
    statusText: "正在发送",
    attachments: []
  }
})
onBeforeMount(() => {
  id = route.currentRoute.value.query.id
  axios.get("/api/email/readEmail?emailId=" + id).then(res => {
    data.email = res.data.data
  })

});
import Chat from "@/components/chat/Chat.vue";


</script>

<template>

  <el-drawer v-model="drawer" title="I am the title" :with-header="false" :size="540">
    <Chat></Chat>

  </el-drawer>




    <div style="height: 100%;width: 100%">

      <el-scrollbar
          class="scrollbar-for"
      >
            <div style="display: flex;margin-bottom: 10px; position:fixed;width: 100%;background: #fbfcfd">
              <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">删 除</el-button>
              <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 60px">举 报</el-button>

              <el-dropdown ref="dropdown1" trigger="contextmenu" style="margin-left: 12px">
                <el-button text class="btn" style="  border: 1px solid #b7bcc7; font-size: 13px;width: 80px">
                  标记为
                  <el-icon>
                    <arrow-down/>
                  </el-icon>
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
              <el-button text style=" border: 1px solid #b7bcc7; font-size: 13px;width: 60px;margin-left: 13px"  @click="drawer = true">ai问答</el-button>

            </div>
        <br>
        <br>


        <div style="background: #eff5fb;">
          <div style="text-align:left;margin-bottom: 10px;">
            <span></span><strong>{{ data.email.subject }}</strong>
          </div>

          <div class="topLine">
            <span class="topLeft">发件人:</span>
            <span style="color: #42b983">{{ data.email.nickname }}</span>&nbsp;
            <span class="topRight"><{{ data.email.from }}></span>
          </div>

          <div class="topLine">
            <span class="topLeft">收件人:</span>
            <span class="topRight">{{ data.email.to }}</span>
          </div>

          <div class="topLine">
            <span class="topLeft">时&nbsp;&nbsp;&nbsp;间:</span>
            <span class="topRight">{{ data.email.updateTime }}</span>
          </div>
        </div>

        <div v-html="data.email.htmlText" style="width: auto;min-height: 300px"></div>


        <el-input placeholder="快捷回复"/>
        <br>
        <br>
        <br>
        <br>

      </el-scrollbar>
    </div>



</template>

<style scoped>
.topLine {
  text-align: left;
  margin-bottom: 5px;
}

.topLeft {
  font-size: 14px;
  color: #8e9aaa;
  margin-right: 6px;
}

.topRight {
  font-size: 14px;
  color: #8e9aaa;
}




</style>