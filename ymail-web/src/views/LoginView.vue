<template>
  <div class="login-container">
    <el-form :model="ruleForm2" :rules="rules2"
             status-icon
             ref="ruleForm2"
             label-position="left"
             label-width="0px"
             class="demo-ruleForm login-page">
      <h3 class="title">系统登录</h3>
      <el-form-item prop="username">
        <el-input type="text"
                  v-model="ruleForm2.mail"
                  auto-complete="off"
                  placeholder="用户名"
                  maxlength="10"
                  show-word-limit
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" v-model="ruleForm2.password" show-password></el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;" @click="handleSubmit" :loading="logining">登录</el-button>
        <router-link to="/register">register</router-link>

      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
import { ElMessage } from 'element-plus'
export default {
  data () {
    return {
      logining: false,
      ruleForm2: {
        mail: 'admin',
        password: '123456'
      },
      rules2: {
        mail: [{required: true, message: '请输入账号', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
      checked: false
    }
  },
  methods: {
    handleSubmit (event) {
      this.$refs.ruleForm2.validate((valid) => {
        if (valid) {
          axios.post("/api/ymail/user/login",this.ruleForm2).then(
              e=>{
                let data=e.data
                if(data.flag===false){
                  ElMessage.error(data.message)
                }else{
                  ElMessage.success("登录成功")
                  this.$router.push('/home')
                }
              }
          )
          // axios.post("/api/ymail/admin/login",this.ruleForm2,{});
        } else {
          console.log('error submit!')
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
}
.login-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
label.el-checkbox.rememberme {
  margin: 0px 0px 15px;
  text-align: left;
}
</style>