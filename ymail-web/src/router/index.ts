import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import ReceiverBox from "@/components/ReceiverBox.vue";
import RegisterView from "@/views/RegisterView.vue";
import SentBox from "@/components/SentBox.vue";
import HomeDefault from "@/components/HomeDefault.vue";
import WriteMessage from "@/views/WriteMessage.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },
    {
        path: '/',
        name: '',
        component: HomeView
    },
    {
        path: '/write',
        component: WriteMessage
    },
    {
        path: '/home',
        name: 'home',
        component: HomeView,
        children: [
            {
                path: '',
                component: HomeDefault
            },
            {
                path: '/receiveBox',
                component: ReceiverBox
            },
            {
                path: '/sentBox',
                component: SentBox
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
