<template>
  <div class="background">
    <div class="container">
      <div class="options">
        <el-radio-group v-model="registrationType">
          <el-radio label="normal">普通注册</el-radio>
          <el-radio label="vip">VIP注册</el-radio>
        </el-radio-group>
      </div>
      <div class="panel">
        <div v-if="registrationType === 'normal'">
          <h2>普通注册</h2>
          <el-form :model="form" :rules="rules" ref="registrationForm" label-width="80px">

            <el-form-item label="邮箱" prop="mail">
              <el-input v-model="form.mail" placeholder="请输入邮箱"></el-input>
            </el-form-item>


            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
            </el-form-item>

            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>

            <el-button type="primary" @click="submitForm">注册</el-button>
          </el-form>
        </div>
        <div v-else>
          <h2>VIP注册</h2>
          <!-- VIP 注册表单 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      registrationType: 'normal',
      form: {
        phone: '',
        password: '',
        mail: ''
      },
      rules: {
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        mail: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'mail', message: '请输入正确的邮箱格式', trigger: ['blur', 'change']}
        ]
      }
    };
  },
  methods: {
    submitForm() {
      this.$refs.registrationForm.validate((valid) => {
        if (valid) {
          // 表单验证通过，进行注册逻辑
          axios.post("/api/ymail/user/register", this.form).then(e => {
            if(e.data.flag===true){
              ElMessage.success("注册成功");
              //开始跳转
            }else{
              ElMessage.error(e.data.message)
            }
          });

        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style>
.background {
  background-color: #f0f0f0;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  width: 50%;
  margin: 25%;
  background-color: white;
  padding: 20px;
}

.options {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.panel {
  display: flex;
  flex-direction: column;
}

</style>