<template>
  <div>
    <el-row :gutter="10" style="height: 300px;margin-top: -20px;">
      <el-col :span="12">
        <div class="chart-wrapper">
          <h3>带附件/图片邮件占比</h3>
          <div ref="attachmentChart" style="width: 100%; height: 300px;"></div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="chart-wrapper">
          <h3>近7天发送/接收邮件总数</h3>
          <div ref="mailChart" style="width: 100%; height: 300px;"></div>
        </div>
      </el-col>
    </el-row>
    <br>

    <el-row :gutter="10" style="height: 300px">
      <el-col :span="12">
        <div class="chart-wrapper">
          <h3>近7天新用户注册情况</h3>
          <div ref="registrationChart" style="width: 100%; height: 300px;"></div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="chart-wrapper">
          <h3>被举报邮件占比</h3>
          <div ref="reportedChart" style="width: 100%; height: 300px;"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

export default {
  setup() {
    const attachmentChart = ref(null)
    const mailChart = ref(null)
    const registrationChart = ref(null)
    const reportedChart = ref(null)

    // 模拟数据
    const mockData = {
      attachmentRatio: 0.3, // 带附件/图片邮件占比
      mailData: { // 近7天发送/接收邮件总数
        dates: ['5-6', '5-7', '5-8', '5-9', '5-10', '5-11', '5-12'],
        sent: [120, 132, 101, 134, 90, 230, 210],
        received: [220, 182, 191, 234, 290, 330, 310]
      },
      registrationData: [50, 60, 40, 80, 30, 70, 90], // 近7天新用户注册情况
      reportedRatio: 0.05 // 被举报邮件占比
    }

    onMounted(() => {
      // 初始化图表实例
      const attachmentChartInstance = echarts.init(attachmentChart.value)
      const mailChartInstance = echarts.init(mailChart.value)
      const registrationChartInstance = echarts.init(registrationChart.value)
      const reportedChartInstance = echarts.init(reportedChart.value)

      // 配置选项
      const attachmentOptions = {
        series: [
          {
            name: '带附件/图片邮件',
            type: 'pie',
            radius: '70%',
            data: [
              { value: mockData.attachmentRatio, name: '带附件/图片' },
              { value: 1 - mockData.attachmentRatio, name: '其他' }
            ]
          }
        ]
      }

      const mailOptions = {
        legend: {
          data: ['发送邮件', '接收邮件']
        },
        xAxis: {
          type: 'category',
          data: mockData.mailData.dates
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '发送邮件',
            type: 'line',
            data: mockData.mailData.sent
          },
          {
            name: '接收邮件',
            type: 'line',
            data: mockData.mailData.received
          }
        ]
      }

      const registrationOptions = {
        xAxis: {
          type: 'category',
          data: mockData.mailData.dates
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '新用户注册',
            type: 'bar',
            data: mockData.registrationData
          }
        ]
      }

      const reportedOptions = {
        series: [
          {
            name: '被举报邮件',
            type: 'pie',
            radius: '70%',
            data: [
              { value: mockData.reportedRatio, name: '被举报' },
              { value: 1 - mockData.reportedRatio, name: '其他' }
            ]
          }
        ]
      }

      // 使用配置项显示图表
      attachmentChartInstance.setOption(attachmentOptions)
      mailChartInstance.setOption(mailOptions)
      registrationChartInstance.setOption(registrationOptions)
      reportedChartInstance.setOption(reportedOptions)
    })

    return {
      attachmentChart,
      mailChart,
      registrationChart,
      reportedChart
    }
  }
}
</script>

<style scoped>

</style>
