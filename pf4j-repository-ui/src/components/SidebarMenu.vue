<template>
  <div class="flex flex-col w-full md:w-64 text-white bg-gray-500 h-screen">
    <div
      class="flex-shrink-0 px-8 py-4 flex flex-row items-center justify-between"
    >
      <button
        class="rounded-lg md:hidden rounded-lg focus:outline-none focus:shadow-outline"
        @click="toggleOpen"
      >
        <svg fill="currentColor" viewBox="0 0 20 20" class="w-6 h-6">
          <path
            v-if="!open"
            fill-rule="evenodd"
            d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM9 15a1 1 0 011-1h6a1 1 0 110 2h-6a1 1 0 01-1-1z"
            clip-rule="evenodd"
          ></path>
          <path
            v-if="open"
            fill-rule="evenodd"
            d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
            clip-rule="evenodd"
          ></path>
        </svg>
      </button>
    </div>
    <nav
      :class="{ block: open, hidden: !open }"
      class="flex-grow md:block pb-4 md:pb-0 md:overflow-y-auto"
    >
      <router-link
        :to="{ name: 'Home' }"
        class="block px-4 py-2 text-sm font-semibold hover:bg-gray-800"
        active-class="bg-gray-700"
        ><i class="fas fa-folder"></i> Dashboard</router-link
      >
      <router-link
        :to="{ name: '' }"
        active-class="bg-gray-700"
        class="block px-4 py-2 text-sm font-semibold w-full hover:bg-gray-800"
      >
        <i class="fas fa-list"></i> Repositories</router-link
      >
      <router-link
        :to="{ name: '' }"
        active-class="bg-gray-700"
        class="block px-4 py-2 text-sm font-semibold w-full hover:bg-gray-800"
      >
        <i class="fas fa-cogs"></i> Settings</router-link
      >

      <a
        href="#"
        @click="logout"
        class="block px-4 py-2 text-sm font-semibold hover:bg-gray-800"
        ><i class="fas fa-sign-out-alt"></i> Logout</a
      >
    </nav>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref } from "vue";
import { useAuthStore } from "@/stores/auth-store";

const store = useAuthStore();

const logout = () => store.logout();

const props = defineProps({
  initOpen: Boolean,
});

const { initOpen, title } = props;

const open = ref(initOpen);

const toggleOpen = () => (open.value = !open.value);
</script>
