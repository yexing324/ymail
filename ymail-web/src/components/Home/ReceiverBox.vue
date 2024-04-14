<template>
  <button @click="show">点击</button>
  <el-checkbox :indeterminate="isIndeterminate"  @change="btn1"   v-model="allCheck" size="large" />

    <el-checkbox-group v-model="checkList">


      <el-table
          ref="multipleTableRef"
          :data="tableData"
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >

          <el-table-column width="100" >
            <template #default="{row}">
              <el-checkbox @change="btn"  :value="row['date']" size="large" />
            </template>

          </el-table-column>



          <el-table-column label="Date" width="120">
            <template #default="scope">{{ scope.row.date }}</template>
          </el-table-column>
          <el-table-column property="name" label="Name" width="120" />
          <el-table-column property="address" label="Address" show-overflow-tooltip />



      </el-table>


      <el-table
          ref="multipleTableRef"
          :data="tableData"
          style="width: 100%"
          @selection-change="handleSelectionChange"
          :show-header="false"
      >




        <el-table-column width="100">
          <template #default="{row}">
            <el-checkbox @change="btn"  :value="row['name']" size="large" />
          </template>
        </el-table-column>

        <el-table-column label="Date" width="120">
          <template #default="scope">{{ scope.row.date }}</template>
        </el-table-column>
        <el-table-column property="name" label="Name" width="120" />
        <el-table-column property="address" label="Address" show-overflow-tooltip />
      </el-table>




    </el-checkbox-group>










  <div style="margin-top: 20px">
    <el-button @click="toggleSelection([tableData[1], tableData[2]])"
    >Toggle selection status of second and third rows</el-button
    >
    <el-button @click="toggleSelection()">Clear selection</el-button>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { ElTable } from 'element-plus'
let checked1: any;
function show(){
  console.log(allCheck)
  console.log(checkList)
}
interface User {
  date: string
  name: string
  address: string
}

const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<User[]>([])
const toggleSelection = (rows?: User[]) => {
  if (rows) {
    rows.forEach((row) => {
      // @ts-expect-error
      multipleTableRef.value!.toggleRowSelection(row, undefined)
    })
  } else {
    multipleTableRef.value!.clearSelection()
  }
}
const handleSelectionChange = (val: User[]) => {
  multipleSelection.value = val
}

const tableData: User[] = [
  {
    date: '2016-05-03',
    name: 'Tom1',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-07',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
function btn(e:any){
  if(checkList.value.length == 4){
    isIndeterminate.value=false;
    allCheck.value=true;
    return;
  }
  if(checkList.value.length!=0){
    allCheck.value=false;
    isIndeterminate.value=true;
    return;
  }
  if(checkList.value.length==0){
    allCheck.value=false;
    isIndeterminate.value=false;
  }
}

function btn1(e:any){
  if(checkList.value.length==0){
    checkList.value=table
  }else{
    checkList.value=[]
    allCheck.value=false;
    isIndeterminate.value=false;
  }
}

const checkList = ref([] as any[])
const allCheck = ref(false)
const isIndeterminate=ref(false)
const table=["2016-05-07","2016-05-03","Tom","Tom1"]
</script>
