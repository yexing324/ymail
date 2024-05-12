<template>

  <div class="back" style="float: left">

    <div style="height: 10px;">

    </div>
    <span class="ymail" style="margin-left: 10px;">YMail</span>
    <span class="freeMail">免费邮箱</span>
    | 您的专业电子邮箱

    <!--      界面-->
    <div class="login-container">
      <el-form :model="ruleForm2" :rules="rules2"
               ref="ruleForm2"
               style="background: white"
               class="login-form">
        <h3 class="title">欢迎注册ymail邮箱</h3>

        <el-input v-model="ruleForm2.mail"
                  auto-complete="off"
                  placeholder="邮箱账号"
                  maxlength="10"
                  style="height: 50px;margin-bottom: 10px;"
                  required
                  autocomplete="new-password"
                  show-word-limit >
          <template #append>{{domain}}</template>

        </el-input>

        <el-input v-model="ruleForm2.password"
                  style="margin-top: 20px;height: 50px"
                  placeholder="请输入密码"
                  autocomplete="new-password"
                  show-password/>
        <div>
          <span>
            <el-input v-model="ruleForm2.phone"
                      style="margin-top: 20px;height: 50px;width: 250px"
                      placeholder="请输入手机号"
            />
          </span>
          <span>
        <el-button
            style="display: inline-block;width: 100px;height: 50px;margin-top: 33px;"
            @click="sendCode"
            :disabled="ifDisable"
        >{{buttonText}}</el-button>

          </span>

        </div>

        <el-input v-model="ruleForm2.code"
                  style="margin-top: 20px;height: 50px"
                  placeholder="请输入验证码"
                  />

        <el-form-item style="margin-top: 20px;">
          <el-button type="primary" @click="handleSubmit" :loading="logining"
                     style="height: 50px"
          >注册</el-button>

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
      ifDisable:false,
      buttonText:"发送验证码",
      logining: false,
      autoLogin:false,
      domain:"@ymail.work",
      ruleForm2: {
        mail: '',
        password: '',
        phone: '',
        code: ''
      },
      rules2: {
        mail: [{required: true, message: '请输入账号', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        phone: [{required: true, message: '请输入手机号', trigger: 'blur'}],
        code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
      }
    };
  },
  methods: {
    sendCode() {
      if(this.ruleForm2.phone===""){
        ElMessage.error("请输入手机号");
        return;
      }
      if(this.ruleForm2.phone.length!==11){
        ElMessage.error("请输入正确的手机号");
        return;
      }
      //正则校验手机号
      const reg = /^1[3-9]\d{9}$/;
      if (!reg.test(this.ruleForm2.phone)) {
        ElMessage.error("请输入正确的手机号");
        return;
      }

      ElMessage.success("验证码已发送");
      let time = 3;
      this.buttonText = time+"s后重试";
      this.ifDisable=true;
      let timer = setInterval(() => {
        if (time === 0) {
          this.buttonText = "发送验证码";
          this.ifDisable = false;
          clearInterval(timer);
        } else {
          this.buttonText = time + "s后重试";
          time--;
        }
      }, 1000);

      axios.get("/api/admin/sendCode?phone="+this.ruleForm2.phone)
          .then((response) => {
            const data = response.data;
            if (data.flag === false) {
              ElMessage.error(data.message);
            }
          })
          .catch(() => {
            ElMessage.error("发送失败，请重试");
          });
    },
    handleSubmit() {
      //校验均不能为空
      if(!this.checkparam()){
        return
      }
      this.doRegister()
    },
    doRegister() {
      console.log("123")
      this.logining = true;
      axios.post("/api/admin/register?domain="+this.domain, this.ruleForm2)
          .then((response) => {
            const data = response.data;
            if (data.flag === false) {
              ElMessage.error(data.message);
            } else {
              ElMessage.success("注册成功");
              this.$router.push('/login');
            }
            this.logining = false;
          })
          .catch(() => {
            ElMessage.error("注册失败，请重试");
            this.logining = false;
          });
    },
    checkparam(){
      if(this.ruleForm2.mail===""){
        ElMessage.error("请输入邮箱");
        return false;
      }
      if(this.ruleForm2.password===""){
        ElMessage.error("请输入密码");
        return false;
      }
      if(this.ruleForm2.phone===""){
        ElMessage.error("请输入手机号");
        return false;
      }
      if(this.ruleForm2.phone.length!==11){
        ElMessage.error("请输入正确的手机号");
        return false;
      }
      //正则校验手机号
      const reg = /^1[3-9]\d{9}$/;
      if (!reg.test(this.ruleForm2.phone)) {
        ElMessage.error("请输入正确的手机号");
        return false;
      }
      if(this.ruleForm2.code===""){
        ElMessage.error("请输入验证码");
        return false;
      }
      return true;

    }
  }
};
</script>


<style scoped>
#app {
  background: #e4edfc;
}

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
  background: #ebf3fe;
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  z-index: 0;
}
</style>