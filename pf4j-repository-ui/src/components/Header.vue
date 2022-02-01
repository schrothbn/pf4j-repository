<template>
  <div
    class="flex bg-gradient-to-b from-gray-800 to-gray-500 py-2 px-2 justify-between items-center"
  >
    <a
      href="#"
      class="text-white hover:text-black ext-lg font-semibold tracking-widest uppercase rounded-lg dark-mode:text-white focus:outline-none focus:shadow-outline"
      ><i class="fas fa-warehouse"></i>
      <span class="invisible w-0 md:visible md:w-10">{{ title }}</span></a
    >

    <div
      class="border-l-2 border-black text-sm font-semibold text-white hover:text-black cursor-pointer px-2 py-2"
    >
      <i class="fas fa-user"></i> Welcome, {{ username }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, toRefs, defineProps } from "vue";
import { useAuthStore } from "@/stores/auth-store";

const props = defineProps<{
  title: string;
}>();
const store = useAuthStore();
const username = computed(() => store.user?.username);

const { title } = toRefs(props);
</script>
