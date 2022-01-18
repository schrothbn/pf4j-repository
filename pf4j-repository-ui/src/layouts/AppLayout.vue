<template>
  <component :is="layout">
    <router-view />
  </component>
</template>

<script setup lang="ts">
import AppDefaultLayout from './AppDefaultLayout.vue'
import {markRaw, ref, watch } from 'vue'
import {useRoute} from 'vue-router'

const layout = ref()
const route = useRoute()

watch(
  () => route.meta?.layout as string | undefined,
  async (metaLayout) => {
    console.log('Layout function')
    try {
      const component = metaLayout && await import(/* @vite-ignore */ `./${metaLayout}.vue`)
      console.log(component)
      layout.value = markRaw(component?.default || AppDefaultLayout)
    } catch(e) {
      console.log(e)
      layout.value = markRaw(AppDefaultLayout)
    }
  },
  { immediate: true }
)
</script>
/style>