<template>

    <div class="back" style="float: left">

      <div style="height: 10px;">

      </div>
      <span class="ymail" style="margin-left: 10px;">YMail</span>
      <span class="freeMail">免费邮箱</span>
      | 您的专业电子邮箱

<!--      界面-->
      <div class="login-container" style="float: right;margin-right: 100px;">
        <el-form :model="ruleForm2" :rules="rules2"
                 ref="ruleForm2"
                 style="background: white"
                 class="login-form">
          <h3 class="title">账号登录</h3>
            <el-input v-model="ruleForm2.mail"
                      auto-complete="off"
                      placeholder="邮箱账号"
                      maxlength="10"
                      style="height: 50px"

                      show-word-limit />
            <el-input v-model="ruleForm2.password"
                      style="margin-top: 20px;height: 50px"
                      placeholder="请输入密码"
                      show-password/>
          <div>
            <span>
               <el-checkbox v-model="autoLogin" label="30天免密登录" size="large" />
              <span style="float: right;color: grey;margin-top: 10px;">忘记密码</span>
            </span>
          </div>
          <el-form-item style="margin-top: 20px;">
            <el-button type="primary" @click="handleSubmit" :loading="logining"
            style="height: 50px"
            >登录</el-button>
            <div style="margin-left: 220px;">
              <span>还没有账号？</span>
              <router-link to="/register">去注册</router-link>

            </div>
            <div style="margin:30px 0 -20px 60px;color: grey">
              <span>阅读并接受</span>
              <a style="color: blue">《服务条款》</a>
              和
              <a style="color: blue">《隐私政策》</a>
            </div>

          </el-form-item>
        </el-form>
      </div>
    </div>










</template>

<script>
import axios from "axios";
import {ElMessage} from 'element-plus';
import Cookies from "js-cookie";
import {onBeforeMount, ref} from "vue";


export default {
  setup() {
    const autoLogin = ref(false);
    onBeforeMount(() => {
      document
          .querySelector('body')
          .setAttribute('style', 'background-color:#f7f7f7')
    })
  },
  data() {
    return {
      logining: false,
      autoLogin:false,
      domain:"@ymail.work",
      ruleForm2: {
        mail: 'admin',
        password: '123456'
      },
      rules2: {
        mail: [{required: true, message: '请输入账号', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      }
    };
  },
  methods: {
    handleSubmit() {
      this.$refs.ruleForm2.validate((valid) => {
        if (valid) {
          this.doLogin();
        } else {
          console.error('error submit!');
          return false;
        }
      });
    },
    doLogin() {
      this.logining = true;
      axios.post("/api/admin/login?domain="+this.domain, this.ruleForm2)
          .then((response) => {
            const data = response.data;
            if (data.flag === false) {
              ElMessage.error(data.message);
            } else {
              ElMessage.success("登录成功");
              this.$router.push('/home');
              Cookies.set('cookie', data.data.cookie, {expires: 7});
              Cookies.set('mail', data.data.mail, {expires: 7});
            }
            this.logining = false;
          })
          .catch(() => {
            ElMessage.error("登录失败，请重试");
            this.logining = false;
          });
    }
  }
};
</script>


<style scoped>


.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-form {
  width: 350px;
  padding: 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.title {
  text-align: center;
  margin-bottom: 20px;
}

.el-button {
  width: 100%;
  margin-bottom: 15px;
}

router-link {
  color: #409eff;
  text-decoration: none;
}

.ymail {
  font-size: 50px;
  color: red;
  font-family: "华文彩云", serif;
}

.freeMail {
  font-size: 40rpx;
  margin: auto 10px;
}

.back {
  background: url("../assets/back.png");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  z-index: 0;
}
</style>