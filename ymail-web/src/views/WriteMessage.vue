<script setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import {Promotion} from "@element-plus/icons-vue";
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import {onBeforeUnmount, onMounted, ref, shallowRef, toRaw} from "vue";
import axios from "axios";


const fileList = ref([])

const upload = (param) => {
  const formData = new FormData()
  formData.append('file', param.file)
  const url = '/api/upload/attach'
  axios.post(url, formData).then(data => {

  }).catch(response => {
    console.log("上传失败"+response)
  })
}

const showFileList = () => {
  console.log(toRaw(fileList.value))
}

let components = {Promotion, Editor, Toolbar}
let editor;
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

const toolbarConfig = {}

const editorConfig = {MENU_CONF: {}}
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editorV) => {
  editorRef.value = editorV // 记录 editor 实例，重要！
  editor = editorRef.value
  editor.setHtml("<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p>")
}

// 图片配置
editorConfig.MENU_CONF['uploadImage'] = {
  timeout: 5 * 1000, // 5s

  fieldName: "image",
  headers: {
    "Content-Type": "multipart/form-data",
    Authorization: "Bearer " + localStorage.getItem("token")
  },
  maxFileSize: 10 * 1024 * 1024, // 10M

  base64LimitSize: 0 * 1024, // 暂不通过base64形式
  onBeforeUpload(files) {
    return files; // 返回哪些文件可以上传
    // return false 会阻止上传
  },
  onProgress(progress) {
    console.log("onProgress", progress);
  },
  onSuccess(file, res) {
    console.log("onSuccess", file, res);
  },
  onFailed(file, res) {
    alert(res.message);
    console.log("onFailed", file, res);
  },
  onError(file, err, res) {
    alert(err.message);
    console.error("onError", file, err, res);
  },

  // 用户自定义上传图片
  customUpload(file, insertFn) {
    var axios = require("axios");
    var FormData = require("form-data");
    var data = new FormData();
    data.append("file", file); // file 即选中的文件
    axios.post("/api/upload//setImg", data).then(e => {
      let url = e.data.url
      insertFn(url)
    })
  }


}


let data = ref({
  nickname: 'yexing',
  to: 'yexing195@163.com',
  from: '123',
  subject: '你好',
  plainText: '',
  htmlText: '',
  attachments:[]
})

let from = "yexing@pmail.slovety.top"
const send = () => {
  data.value.from = from
  data.value.htmlText = editor.getHtml()
  data.value.plainText = editor.getText()
  data.value.attachments=toRaw(fileList.value)
  let req = toRaw(data.value);
  axios.post("/core/sender/sendEmail", req).then(e => {
    console.log(e.data)
  })
}


</script>
<template slot-scope="scope">
  <div>
    <Title></Title>
  </div>
  <div style="padding: 30px 60px 0 60px;display: flex">
    <el-button type="primary">
      <el-icon>
        <Promotion/>
      </el-icon>
      发送
    </el-button>
    <el-button plain>预览</el-button>
    <el-button plain>存草稿</el-button>
    <el-button plain>取消</el-button>
    <el-button plain @click="showFileList">show</el-button>
  </div>
  <br/>
  <div style="display: flex">

    <span style="width: 80px">收件人</span>
    <el-input
        v-model="data.to"
        placeholder="Please input"
        show-word-limit
    />
  </div>
  <br>
  <div style="display: flex">

    <span style="width: 80px">主题</span>
    <el-input
        v-model="data.subject"
        maxlength="10"
        placeholder="Please input"
        show-word-limit
    />
  </div>
  <br>


  <!--  附件部分-->
  <div style="display: flex;margin-left: 15px">
    <el-upload
        v-model:file-list="fileList"
        multiple
        action=""
        class="upload-demo"
        :http-request="upload"
    >
      <el-button type="primary">Click to upload</el-button>
      <template #tip>
        <div class="el-upload__tip">
          jpg/png files with a size less than 500kb
        </div>
      </template>
    </el-upload>
  </div>


  <!--  附件分割线-->


  <div style="border: 1px solid #ccc">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
  </div>
  <div style="padding: 30px 60px 0 60px;display: flex">
    <el-button type="primary" @click="send">
      <el-icon>
        <Promotion/>
      </el-icon>
      发送
    </el-button>
    <el-button plain>取消</el-button>
  </div>

</template>

<style scoped>

</style>

