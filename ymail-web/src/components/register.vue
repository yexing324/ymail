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
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
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
export default {
  data() {
    return {
      registrationType: 'normal',
      form: {
        username: '',
        password: '',
        email: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
        ]
      }
    };
  },
  methods: {
    submitForm() {
      this.$refs.registrationForm.validate((valid) => {
        if (valid) {
          // 表单验证通过，进行注册逻辑
          console.log('表单验证通过');
        } else {
          console.log('表单验证失败');
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