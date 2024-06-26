import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import ReceiverBox from "@/components/Home/ReceiverBox.vue";
import RegisterView from "@/views/RegisterView.vue";
import HomeDefault from "@/components/HomeDefault.vue";
import WriteMessage from "@/views/WriteMessage.vue";
import ReadEmail from "@/components/Home/readEmail.vue";
import SendBox from "@/components/Home/SendBox.vue";
import CommonBox from "@/components/Home/CommonBox.vue";
import Temp from "@/components/Home/temp.vue";
import Setting from "@/views/setting.vue";
import UserInfo from "@/components/setting/UserInfo.vue";
import GroupManage from "@/components/setting/GroupManage.vue";
import EchartsTable from "@/components/setting/EChartsTable.vue";
import EChartsTable from "@/components/setting/EChartsTable.vue";
import ManageEChatsTable from "@/components/setting/ManageEChatsTable.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },
    {
        path: '/write',
        component: WriteMessage
    },
    {
        path: '/setting',
        name:"setting",
        component: Setting,
        children: [
            {
                path: '/userInfo',
                component: UserInfo
            },
            {
                path: '/groupManage',
                component: GroupManage
            },
            {
                path: '/dataAnalyse',
                component: EChartsTable
            },
            {
                path: '/adminDataAnalyse',
                component: ManageEChatsTable
            }
        ]
    },
    {
        path: '/',
        name: 'home',
        component: HomeView,
        children: [
            {
                path: '/',
                component: HomeDefault
            },
            {
                path: '/receiveBox',
                component: ReceiverBox
            },
            {
                path: '/sendBox',
                component: SendBox
            },
            {
                path:'/emailDetail',
                component:ReadEmail
            },
            {
                path:'/commonBox',
                component:CommonBox
            }
        ]
    },
    {
        path: '/register',
        name: 'register',
        component: RegisterView
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        // component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
