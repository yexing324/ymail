<template>
<button >你好</button>
  <div class="demo-image__preview">
    <el-image
        style="width: 100px; height: 100px"
        :src="url"
        :zoom-rate="1.2"
        :max-scale="7"
        :min-scale="0.2"
        :preview-src="srcList"
        :initial-index="4"
        fit="cover"
    />
  </div>
</template>

<script lang="ts" setup>
import Cookies from 'js-cookie'
import {onBeforeMount} from "vue";
const url =
    'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'
const srcList = [
  'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'
]
onBeforeMount(()=>{

})
</script>