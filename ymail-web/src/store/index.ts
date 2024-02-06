import { createStore } from 'vuex'

export default createStore({
  state: {
    data : [
      {
        label: '收件箱',
        categoryId: '1111111',
        type: 'parent',
        parentId: '0',
      },
      {
        label: '发件箱',
        categoryId: '22222222'
      },
      {
        label: '其他文件夹',
        categoryId: '3333333333',
        type: 'parent',
        parentId: '0',
        children: [
          {
            label: '草稿箱',
            categoryId: '3333333333-1',
          }, {
            label: '已删除',
            categoryId: '3333333333-1',
          }
        ],
      },
    ]
  },
  getters: {
    getData: state => state.data
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
