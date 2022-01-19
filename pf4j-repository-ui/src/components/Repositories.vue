<template>
    <h3 class="text-2xl font-semibold italic">Repositories</h3>
    <div class="flex gap-10 mb-10">
        <div v-for="repo in store.repositories" class="bg-green-300 shadow-xl rounded py-2 px-2 hover:bg-green-500 ">
        <h2 class="text-xl font-bold">{{repo.name}} </h2> 
        <p>
            {{repo.pluginCount}} plugins uploaded
            </p>
        </div>
    </div>
    <button class="bg-green-300 rounded shadow text-black font-semibold py-2 px-2 hover:bg-green-500">Create new repository</button>
</template>

<script setup lang="ts">
import useRepositoryStore from '@/stores/repository-store'

const store = useRepositoryStore()

store.fetch()

</script>