<template>
  <h1 class="text-3xl text-green-700 text-center">Welcome, {{ user.username }}</h1>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth.module'
const store = useAuthStore()
const user = store.user
</script>