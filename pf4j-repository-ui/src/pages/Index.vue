<template>
  <Repositories />
</template>

<script setup lang="ts">
import { useAuthStore } from "@/stores/auth-store";
import Repositories from "@/components/Repositories.vue";
const store = useAuthStore();
const user = store.user;
</script>
