<template>
  <div style="display: flex;flex-direction:column;height: 100vh">
    <div style="width: 100%;">

      <Title @test="test"></Title>


    </div>
<!--     最上面设置结束-->
    <div style="display: flex;overflow:hidden;">
      <div style="flex-shrink:0">
        <left-menu></left-menu>
      </div>

      <div style="width: 100%;overflow:hidden;padding: 10px 0 0 10px">
        <router-view :key="this.$route.fullPath"></router-view>
      </div>

<!--      &lt;!&ndash;内部将变成上下布局&ndash;&gt;-->
<!--      <div style="display: inline-block;>-->
<!--        <router-view></router-view>-->
<!--      </div>-->

    </div>

  </div>


</template>

<script setup>
import router from "@/router";
import {onBeforeMount, toRaw} from "vue";
import store from "../store/index";
import Title from "@/components/Home/title.vue";
import LeftMenu from "@/components/Home/leftMenu.vue";



const test = () => {
  console.log("123")
}


let data;
onBeforeMount(() => {
  //TODO:判断cookie状态
  //初始化菜单
  data = toRaw(store.getters.getData)
  //初始化数据

})

//在这里进行路由跳转
const handleNodeClick = (e) => {
  let label = e.label
  switch (label) {
    case "收件箱":
      router.push('/receiveBox')
      break;
    case "发件箱":
      router.push('/sentBox')
      break;
  }
}

const append = (type, data, node) => {
  console.log(type, 11111);
}

const defaultProps = {
  children: 'children',
  label: 'label',
}
</script>


<style>
div {
  //box-shadow:0px 0px 0px 1px rebeccapurple inset;
}


//el-tree的样式设置
.el-tree-node:focus > .el-tree-node__content {
  background-color: #ebf2ff !important;
}

.el-tree-node__content:hover {
  background-color: #ebf2ff;
}

.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #ebf2ff;
}

</style>





<!--<template>-->
<!--  <div style="height: 98vh; width: 100%;" >-->
<!--&lt;!&ndash;    <div class="div" v-menus:left="menus">指令方式打开菜单</div>&ndash;&gt;-->
<!--&lt;!&ndash;    <div class="div" @click.stop @contextmenu="($event) => $menusEvent($event, menus)">事件方式打开菜单</div>&ndash;&gt;-->
<!--&lt;!&ndash;    <div class="div" @click.stop @contextmenu="rightClick">组件方式打开菜单</div>&ndash;&gt;-->
<!--&lt;!&ndash;    <vue3-menus :open="isOpen" :event="eventVal" :menus="menus.menus">&ndash;&gt;-->
<!--&lt;!&ndash;      <template #icon="{menu, activeIndex, index}">{{activeIndex}}</template>&ndash;&gt;-->
<!--&lt;!&ndash;      <template #label="{ menu, activeIndex, index }">插槽：{{ menu.label }}</template>&ndash;&gt;-->
<!--&lt;!&ndash;    </vue3-menus>&ndash;&gt;-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import { defineComponent, nextTick, ref, shallowRef } from "vue";-->

<!--export default defineComponent({-->
<!--  name: "App",-->
<!--  setup() {-->
<!--    const isOpen = ref(false);-->
<!--    const eventVal = ref({});-->
<!--    function rightClick(event) {-->
<!--      isOpen.value = false;-->
<!--      nextTick(() => {-->
<!--        eventVal.value = event;-->
<!--        isOpen.value = true;-->
<!--      })-->
<!--      event.preventDefault();-->
<!--    }-->
<!--    const menus = shallowRef({-->
<!--      menus: [-->
<!--        {-->
<!--          label: "返回(B)",-->
<!--          tip: 'Alt+向左箭头',-->
<!--          click: () => {-->
<!--            window.history.back(-1);-->
<!--          }-->
<!--        },-->
<!--        {-->
<!--          label: "点击不关闭菜单",-->
<!--          tip: '不关闭菜单',-->
<!--          click: () => {-->
<!--            return false;-->
<!--          }-->
<!--        },-->
<!--        {-->
<!--          label: "前进(F)",-->
<!--          tip: 'Alt+向右箭头',-->
<!--          disabled: true-->
<!--        },-->
<!--        {-->
<!--          label: "重新加载(R)",-->
<!--          tip: 'Ctrl+R',-->
<!--          click: () => location.reload(),-->
<!--          divided: true-->
<!--        },-->
<!--        {-->
<!--          label: "另存为(A)...",-->
<!--          tip: 'Ctrl+S'-->
<!--        },-->
<!--        {-->
<!--          label: "打印(P)...",-->
<!--          tip: 'Ctrl+P',-->
<!--          click: () => window.print(),-->
<!--        },-->
<!--        {-->
<!--          label: "投射(C)...",-->
<!--          divided: true-->
<!--        },-->
<!--        {-->
<!--          label: '发送到你的设备',-->
<!--          children: [-->
<!--            {-->
<!--              label: 'iPhone',-->
<!--            },-->
<!--            {-->
<!--              label: 'iPad'-->
<!--            },-->
<!--            {-->
<!--              label: 'Windows 11'-->
<!--            }-->
<!--          ]-->
<!--        },-->
<!--        {-->
<!--          label: "为此页面创建二维码",-->
<!--          divided: true,-->
<!--        },-->
<!--        {-->
<!--          label: "使用网页翻译(F)",-->
<!--          divided: true,-->
<!--          children: [-->
<!--            { label: "翻译成繁体中文" },-->
<!--            { label: "翻译成繁体中文" },-->
<!--            {-->
<!--              label: "百度翻译", children: [-->
<!--                { label: "翻译成繁体中文" },-->
<!--                { label: "翻译成繁体中文" },]-->
<!--            },-->
<!--            {-->
<!--              label: "搜狗翻译", children: [-->
<!--                { label: "翻译成繁体中文" },-->
<!--                { label: "翻译成繁体中文" },-->
<!--              ]-->
<!--            },-->
<!--            {-->
<!--              label: "有道翻译", children: [-->
<!--                { label: "翻译成繁体中文" },-->
<!--                { label: "翻译成繁体中文" },-->
<!--              ]-->
<!--            },-->
<!--          ]-->
<!--        },-->
<!--        {-->
<!--          label: "截取网页(R)"-->
<!--        },-->
<!--        { label: "查看网页源代码(U)", tip: 'Ctrl+U' },-->
<!--        { label: "检查(N)", tip: 'Ctrl+Shift+I' }-->
<!--      ]-->
<!--    })-->
<!--    return { menus, isOpen, rightClick, eventVal }-->
<!--  },-->
<!--});-->
<!--</script>-->