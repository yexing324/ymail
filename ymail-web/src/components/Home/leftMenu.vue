<script lang="ts" setup>
import {onBeforeMount, ref, toRaw} from "vue";
import store from "@/store";
import router from "@/router";
import {EditPen, MessageBox} from "@element-plus/icons-vue";
import axios from "axios";
import {ElMessage} from "element-plus";

const data=ref({
  menu1:[],
  menu2:[{
    "children":[] as any
  }]
})
let originMenu2=[
  {
    label: '其他文件夹',
    children: [
      {
        label: '已删除',
      },
      {
        label: '垃圾箱',
      },
    ],
  },
  {
    label: '附件管理',
    children: [
      {
        label: '超大附件',
      },
      {
        label: '邮箱附件',
      },
    ],
  },
]
onBeforeMount(() => {
  //TODO:判断cookie状态
  //初始化菜单
  data.value.menu1 = toRaw(store.getters.getMenu1)
  data.value.menu2 = originMenu2
  //初始化数据
  getGroupList()


})
function getGroupList(){
  axios.get("/api/email/getGroupList").then(e => {
    data.value.menu2=originMenu2
    Object.values(toRaw(e.data.data)).forEach((indexItem:any)=>{
      let item={"label":""}
      item.label=indexItem.name
      data.value.menu2[0]['children'].push(item)
    })
  })
}
const write=()=>{
  router.push('/write')
}
const handleNodeClick = (e: { label: any; }) => {
  let label = e.label
  console.log(label)
  switch (label) {
    case "收件箱":
      router.push({
        path:'/commonBox',
        query:{
          group:"收件箱"
        }
      })
      break;
    case "已发送":
      router.push({
        path:'/commonBox',
        query:{
          group:"已发送"
        }
      })
      break;
    case "星标邮件":
      router.push({
        path:'/commonBox',
        query:{
          group:"星标邮件"
        }
      })
      break;
    case "草稿箱":
      router.push({
        path:'/commonBox',
        query:{
          group:"草稿箱"
        }
      })
      break;
    case "待办邮件":
      router.push({
        path:'/commonBox',
        query:{
          group:"待办邮件"
        }
      })
      break;
    case "已删除":
      router.push({
        path:'/commonBox',
        query:{
          group:"已删除"
        }
      })
      break;
    case "垃圾箱":
      router.push({
        path:'/commonBox',
        query:{
          group:"垃圾邮件"
        }
      })
      break;
    default:
      router.push({
        path:'/commonBox',
        query:{
          group:label
        }
      })
      break;
  }
}
const defaultProps = {
  children: 'children',
  label: 'label',
}
</script>

<template>
  <div style="height: 100vh;border-right: #c2d3f4 1px solid;text-align: left">
      <el-button type="primary" class="topItem">
        <el-icon><MessageBox /></el-icon>
        收信
      </el-button>
      <el-button type="primary" class="topItem" @click="write">
        <el-icon><EditPen /></el-icon>
        写信
      </el-button>
    <br/>


    <el-tree
        style="margin-top:10px "
        highlight-current
        :data="data.menu1"
        :props="defaultProps"
        @node-click="handleNodeClick">
    </el-tree>

    <el-divider style="margin:10px 10% 10px 10%;width: 80%"></el-divider>

    <el-tree
        style="margin-top:10px "
        :data="data.menu2"
        :props="defaultProps"
        @node-click="handleNodeClick">
    </el-tree>


  </div>

</template>

<style scoped>
.topItem {
  width: 100px;
  height: 40px;
  background: #ebf2ff;
  color: #3370ff;
  border-radius: 0;
  font-size: 18px;
  font-family: "等线",serif;
  font-weight: bold;
  border-right: 0;
  border-top:0;
  margin: 0;

}

.el-tree-node__label {
  line-height:106px;
}
.el-tree-node{
  margin-top: 60px;
}


.el-button--primary:hover {
  background: #d9e7ff;
}


/* 横线 */
.line {
  width: 150px;
  float: right;
  height: 100vh;
  margin-top: -0.5em;
  background: #d4c4c4;
  text-align: center;
}

</style>