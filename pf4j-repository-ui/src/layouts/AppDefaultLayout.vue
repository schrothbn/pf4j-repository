<template>
  <Header title="PF4J - Repository" />
  <div class="md:flex flex-col md:flex-row md:min-h-screen w-full">
    <SidebarMenu open="false" title="PF4J-Repository" />
    <main class="container mx-2 my-4 rounded-xl shadow-xl px-6 py-2">
      <slot />
    </main>
  </div>
  <footer class="text-center text-xs bg-gray-500 text-white">
    &copy; Benjamin Schroth
  </footer>
</template>

<script setup lang="ts">
import Header from "@/components/Header.vue";
import SidebarMenu from "@/components/SidebarMenu.vue";
import { useAuthStore } from "@/stores/auth-store";
import { useRouter } from "vue-router";
import { ref } from "vue";
const router = useRouter();
const store = useAuthStore();
const logout = () => {
  store.logout();
  router.push("/login");
};
const open = ref(false);
const toggleOpen = () => {
  open.value = !open.value;
};
</script>

<style scoped></style>
