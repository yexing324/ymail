<template>
  <div class="account-settings">
    <div class="profile-section">

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

      <div class="account-info">
        <div class="setting-item">
          <label>邮箱</label>
          <el-tag>{{ data.email }}</el-tag>
        </div>
        <div class="setting-item">
          <label>昵称</label>
          <el-tag>{{ data.nickname }}</el-tag>
        </div>
        <div class="setting-item">
          <label>手机号码</label>
          <span>{{ data.mobileNumber }}</span>
          <el-button @click="changeMobile" size="small" style="color: skyblue">修改</el-button>
        </div>
      </div>
    </div>

    <div class="additional-settings">
      <h3>其他设置</h3>
      <div class="setting-item">
        <label>邮箱通知</label>
        <el-switch v-model="data.emailNotifications"/>
      </div>
      <div class="setting-item">
        <label>ai体验</label>
        <el-switch v-model="data.mobileNotifications"/>
      </div>

    </div>

    <div class="signature-section">
      <h3>修改密码</h3>
      <div class="setting-item">
        <label>旧密码</label>
        <el-input v-model="data.oldPassword" type="password" placeholder="请输入旧密码"></el-input>
      </div>
      <div class="setting-item">
        <label>新密码</label>
        <el-input v-model="data.newPassword" type="password" placeholder="请输入新密码"></el-input>
      </div>
      <div class="setting-item">
        <label>确认密码</label>
        <el-input v-model="data.confirmPassword" type="password" placeholder="请确认新密码"></el-input>
      </div>
      <el-button type="primary" @click="changePassword">提交</el-button>
    </div>

    <el-dialog title="修改手机号码" v-model="data.showMobileDialog">
      <el-form :model="data.mobileForm" label-width="120px">
        <el-form-item label="新手机号码">
          <el-input v-model="data.mobileForm.newMobile"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="data.showMobileDialog = false">取消</el-button>
        <el-button type="primary" @click="changeMobile">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {Plus} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import axios from "axios";
import {onBeforeMount, ref} from "vue";

let data = ref(
    {
      avatarUrl: 'https://nos.netease.com/mail-online/b18734c8037dc640278097c83cfa427b/mail180x180.jpg?imageView&thumbnail=100x100&timestamp=1715505332095',
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


function changePassword() {
  // 验证新密码和确认密码是否一致
  if (data.value.newPassword !== data.value.confirmPassword) {
    ElMessage.error('新密码和确认密码不一致');
    return;
  }


  //axios
  axios.get('/api/admin/changePassWord?oldP=' + data.value.oldPassword + "&newP=" + data.value.newPassword)
      .then(response => {
        if (response.data.flag) {
          ElMessage.success('密码修改成功,下次登录生效');

          data.value.oldPassword = '';
          data.value.newPassword = '';
          data.value.confirmPassword = '';
        } else {
          ElMessage.error(response.data.message)
        }
      })

}


function handleAvatarUpload(res, file) {
  data.value.avatarUrl = res.url;
}

function changeMobile() {
  data.value.showMobileDialog = true;
  data.value.mobileForm.newMobile = '';
}

function saveChanges() {
  // 在这里处理保存更改的逻辑
  // 例如发送请求到服务器进行更新
  console.log('保存修改');
}

function cancelChanges() {
  // 在这里处理取消更改的逻辑
  // 例如重置表单数据
  console.log('取消修改');
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
  width: 100px;
  height: 100px;
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