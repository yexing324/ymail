import { createStore } from 'vuex'

export default createStore({
  state: {
    menu1 : [
      {
        label: '收件箱',
      },
      {
        label: '星标邮件',
      },
      {
        label: '草稿箱',
      },
      {
        label: '已发送',
      },

      {
        label: '待办邮件',
      },
    ],
    menu2:[
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
  },
  getters: {
    getMenu1: state => state.menu1,
    getMenu2: state => state.menu2
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
