<template>
  <el-row>
    <div style="float:left;margin: 20px 0 0  20px">
      <el-upload
          class="avatar-uploader"
          action="api/upload/setAvaTarImg"
          :show-file-list="false"
          :auto-upload="true"
          :on-success="handleAvatarUpload">
        <img v-if="data.avatarUrl" :src="data.avatarUrl" class="avatar">
        <el-icon v-else class="avatar-uploader-icon">
          <Plus/>
        </el-icon>
      </el-upload>
    </div>


    <div class="account-info" style="float:left;margin-top: 50px;color: grey">
      <div class="setting-item">
        <label>昵称</label>
        <el-tag>{{ data.nickname }}</el-tag>
      </div>
      <div class="setting-item">
        <label>手机号码</label>
        <el-tag>{{ data.mobileNumber }}</el-tag>
      </div>
      <div class="setting-item">
        <label>邮箱</label>
        <el-tag>{{ data.email }}</el-tag>
      </div>
    </div>
  </el-row>

  <el-row>

    <div class="poetry-container" style="margin: 50px 0 0 50px;font-family: '华文行楷', serif;font-size: 26px">
      <div class="poetry-lines">
        <p v-for="(line, index) in poetryLines" :key="index">{{ line }}</p>
      </div>
    </div>
  </el-row>
</template>

<script setup>
import {Plus} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import axios from "axios";
import {onBeforeMount, ref} from "vue";
import Cookies from "js-cookie";
import setting from "@/views/setting.vue";
import eventBus from "@/assets/util/eventBus";
let poetryLines= [
  '邮箱留白诗意浓',
  '字里行间藏万钧',
  '珍惜当下美好时',
  '翰墨飞扬抒遥想',
  '心灵的窗总敞亮',
  '让诗意在邮箱中',
  '为你生活添姿彩'
]
let data = ref(
    {
      avatarUrl: '',
      email: '',
      nickname: '',
      mobileNumber: '',
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
      emailNotifications: true,
      mobileNotifications: true,
      vipStatus: true,
      personalSignature: '',
      showMobileDialog: false,
      mobileForm: {}
    }
)

onBeforeMount(() => {
  axios.get('/api/admin/getUserInfo')
      .then(response => {
        data.value.avatarUrl = response.data.data.avatarName;
        data.value.email = response.data.data.mail;
        data.value.nickname = response.data.data.nickname;
        data.value.mobileNumber = response.data.data.phone;
      });

})


function handleAvatarUpload(res, file) {
  data.value.avatarUrl = res.url;
  Cookies.set('avatarName', res.url, {expires: 7})
  eventBus.emit("updateImg")
}

</script>

<style scoped>
.account-settings {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.profile-section {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e5e5e5;
}

.avatar-uploader {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 20px;
  border: 1px dashed #ccc;
  cursor: pointer;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.account-info h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.setting-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.setting-item label {
  flex: 0 0 120px;
  font-weight: bold;
  margin-right: 10px;
}

.additional-settings,
.signature-section {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e5e5e5;
}

h3 {
  font-size: 20px;
  margin-bottom: 15px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
}
</style>