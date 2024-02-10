<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "title",
  methods:{
    test(){
      this.$emit('test', true)
    }
  }
})
</script>

<template>
  <div style="display: flex;margin: 10px 0 10px 10px">
    <span class="ymail">YMail</span>
    <span class="freeMail">免费邮箱</span>
    <button @click="test">按钮</button>


  </div>
  <el-divider class="line"></el-divider>
<!--  <div class="line"></div>-->
</template>

<style scoped>
.ymail {
  font-size: 50px;
  color: red;
  font-family: "华文彩云", serif;
}

.freeMail {
  font-size: 40rpx;
  margin: auto 10px;
}

/* 横线 */
.line {
  width: 100%;
  height: 2px;
  background: #b0c5ee;
  margin: 0;
}

</style>