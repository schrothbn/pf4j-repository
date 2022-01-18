<template>
    <div class="md:flex flex-col md:flex-row md:min-h-screen w-full">
      <SidebarMenu open="false" title="PF4J-Repository"/>
      <main class="mx-2 my-4">
      <slot/>
    </main>
  </div>
  <footer class="text-center text-xs">
  &copy; Benjamin Schroth
  </footer>

</template>

<script setup lang="ts">
import SidebarMenu from '@/components/SidebarMenu.vue'
import { useAuthStore } from '@/stores/auth.module';
import { useRouter } from 'vue-router'
import { ref } from 'vue'
const router = useRouter()
const store = useAuthStore()
const logout = () => {
    store.logout()
    router.push('/login')
}
const open = ref(false)
const toggleOpen = () => {
  open.value = !open.value
}
</script>

<style scoped>

</style>