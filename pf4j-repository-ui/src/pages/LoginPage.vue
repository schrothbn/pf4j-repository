<template>
  <div class="w-full w-max-xs mx-auto mt-10 bg-red-400 text-black border-red-700" v-if="message">
      {{ message }} 
  </div>
  <div class="w-full max-w-xs mx-auto mt-10" v-if="!loading">
      <form @submit.prevent="doLogin" class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <div class="mb-4">
          <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
            Username
          </label>
          <input type="text" name="username" placeholder="Username" v-model="username"/><br/>
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 text-sm font-bold mb-2" for="password">Password</label>
          <input type="password" name="password" placeholder="Password" v-model="password" /><br/>
        </div>
        <div class="flex items-center justify-between">
          <button type="submit" class="py-2 px-3 shadow rounded bg-blue-500 hover:bg-blue-800 text-white" >Sign In</button>
          <a class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800" href="#">
            Forgot Password?
          </a>
        </div>
      </form>
  </div>
  <div class="flex items-center justify-center" v-if="loading">
    <div class="w-16 h-16 border-b-2 border-green-900 rounded-full animate-spin"></div>
  </div>


  <p class="text-gray-500 text-xs text-center">
  &copy; 2021 Benjamin Schroth. All rights reserved.
  </p>
</template>

<script lang="ts">
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {useAuthStore} from '@/stores/auth.module'

export default {
  name: "LoginPage",
  setup() {
    const loading = ref(false)
    const message = ref('')
    const store = useAuthStore()
    const router = useRouter()

    const username = ref('')
    const password = ref('')

    const doLogin = () => {
      loading.value = true
      store.login(username.value, password.value).then(() => {
            router.push('/')
        },
        (error) => {
            loading.value = false
            message.value= (error.response && error.response.data && error.response.data.message) ||
            error.message ||
            error.toString()

        })
     }



    return { 
      loading,
      doLogin,
      username,
      password,
      message
     }

  },
}
</script>

<style scoped>

</style>