<template>
  <div class="email-dashboard">
    <el-row :gutter="20">
      <el-col :span="14">
        <div class="chart-wrapper">
          <h3>最近7天收发邮件统计</h3>
          <div ref="mailChart" style="width: 100%; height: 400px;"></div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="chart-wrapper" style="width: 125%">
          <h3>邮件分组占比</h3>
          <div ref="groupChart" style="width: 100%; height: 400px;"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const mailChart = ref(null)
const groupChart = ref(null)

// 示例数据
const mockData = {
  dates: ['5-12', '5-13', '5-14', '5-15', '5-16', '5-17', '5-18'],
  receivedValues: [120, 132, 101, 134, 90, 230, 210],
  sentValues: [220, 182, 191, 234, 290, 330, 310],
  groups: [
    { name: '收件箱', value: 40 },
    { name: '发件箱', value: 10 },
    { name: '垃圾箱', value: 5 },
    { name: '草稿箱', value: 3 }
  ]
}

onMounted(() => {
  // 初始化 ECharts 实例
  const mailChartInstance = echarts.init(mailChart.value)
  const groupChartInstance = echarts.init(groupChart.value)

  // 配置选项
  const mailOptions = {
    legend: {
      data: ['收到的邮件', '发送的邮件']
    },
    xAxis: {
      type: 'category',
      data: mockData.dates
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '收到的邮件',
        data: mockData.receivedValues,
        type: 'line'
      },
      {
        name: '发送的邮件',
        data: mockData.sentValues,
        type: 'line'
      }
    ]
  }

  const groupOptions = {
    series: [
      {
        name: '邮件分组',
        type: 'pie',
        data: mockData.groups.map(item => ({
          name: item.name,
          value: item.value
        }))
      }
    ]
  }

  // 使用刚指定的配置项和数据显示图表
  mailChartInstance.setOption(mailOptions)
  groupChartInstance.setOption(groupOptions)
})
</script>

<style scoped>
.email-dashboard {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  background: #eee;
}

.chart-wrapper {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  border-radius: 5px;
  background: #fff;
}
</style>