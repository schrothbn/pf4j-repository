import {createRouter, createWebHistory, RouteRecordRaw, RouterOptions} from 'vue-router'
import Index from '@/pages/Index.vue'
import PluginUpload from '@/pages/PluginUpload.vue'
import NewRepository from '@/pages/NewRepository.vue';
import LoginPage from '@/pages/LoginPage.vue'


const routes : Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'Login',
        component: LoginPage,
        meta: {
            layout: 'LoginLayout'
        }
    } as RouteRecordRaw,
    {
        path: '/',
        name: 'Home',
        component: Index,
    } as RouteRecordRaw,
    {
        path: '/upload',
        name: 'PluginUpload',
        component: PluginUpload,
    } as RouteRecordRaw,
{
        path: '/repositories/new',
        name: 'NewRepository',
        component: NewRepository,
    } as RouteRecordRaw,
]

const options = {
    history: createWebHistory(),
    routes
} as RouterOptions
const router = createRouter(options)
router.beforeEach((from, to, next) => {
    const loggedIn = localStorage.getItem('user')
    if (from.name !== 'Login' && !loggedIn) {
        console.log('redirecting')
        next({ path: '/login' })
    } else {
        next()
    }
})


export default router