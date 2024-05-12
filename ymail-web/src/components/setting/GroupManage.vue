<template>
  <div class="group-management">
    <h2>分组管理</h2>
    <el-table :data="systemGroups" style="width: 100%; margin-bottom: 20px;">
      <el-table-column prop="group" label="分组名称"/>
      <el-table-column prop="unread" label="未读"/>
      <el-table-column prop="total" label="总数"/>
    </el-table>

    <div class="non-system-groups">
      <h3>非系统分组</h3>
      <el-checkbox v-model="showNonSystemGroups" label="显示非系统分组"/>
      <el-table v-if="showNonSystemGroups" :data="nonSystemGroups" style="width: 100%;">
        <el-table-column prop="group" label="分组名称"/>
        <el-table-column prop="unread" label="未读"/>
        <el-table-column prop="total" label="总数"/>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditDialog(scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="openDeleteDialog(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 修改分组名称对话框 -->
    <el-dialog v-model="editDialogVisible" title="修改分组名称">
      <el-input v-model="newGroupName" placeholder="请输入新的分组名称"></el-input>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmEdit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 删除分组对话框 -->
    <el-dialog v-model="deleteDialogVisible" title="删除分组" width="300px">
      <span>确定要删除分组 "{{ groupToDelete.group }}" 吗？</span>
      <template #footer>
        <el-button @click="deleteDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDelete">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import axios from 'axios'
import {ElMessage} from "element-plus";

const groupData = ref([])
const systemGroups = ref([])
const nonSystemGroups = ref([])
const showNonSystemGroups = ref(true)
const editDialogVisible = ref(false)
const deleteDialogVisible = ref(false)
const newGroupName = ref('')
const groupToDelete = ref({})
const oldGrouPName= ref('')
onMounted(() => {
  fetchGroupData()
})

const fetchGroupData = () => {
  axios.get('/api/email/getGroupInfo')
      .then(response => {
        groupData.value = response.data.data
        console.log(groupData.value)
        systemGroups.value = groupData.value.filter(group => group.ifSystemGroup)
        nonSystemGroups.value = groupData.value.filter(group => !group.ifSystemGroup)
      })
      .catch(error => {
        console.error('获取分组信息失败:', error)
      })
}

const openEditDialog = (group) => {
  oldGrouPName.value = group.group
  editDialogVisible.value = true
}

const confirmEdit = () => {
  axios.get('/api/email/changeGroupName?oldGroupName=' + oldGrouPName.value + '&newGroupName=' + newGroupName.value)
      .then(res => {
        console.log(res)
        if (res.data.flag) {
          ElMessage.success('修改成功')
          editDialogVisible.value = false
          fetchGroupData()
        } else {
          ElMessage.error(res.data.message)
        }


      })

}

const openDeleteDialog = (group) => {
  groupToDelete.value = group
  deleteDialogVisible.value = true
}

const confirmDelete = () => {
  axios.get('/api/email/deleteGroup?groupName=' + groupToDelete.value.group)
      .then(res => {
        if (res.data.flag){
          fetchGroupData()
        }else{
          ElMessage.error(res.data.message)
        }
        deleteDialogVisible.value = false

      })
}
</script>

<style scoped>
.group-management {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2, h3 {
  margin-bottom: 20px;
}

.non-system-groups {
  margin-top: 30px;
}
</style>
