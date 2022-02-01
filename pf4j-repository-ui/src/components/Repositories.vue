<template>
  <div class="flex flex-col items-center justify-center gap-4">
    <h3 class="text-2xl text-gray-700 font-bold self-start">Repositories</h3>
    <table
      class="table-auto border-collapse border-slate-400 w-full text-gray-600"
    >
      <thead class="bg-slate-200">
        <tr>
          <th class="border-b border-slate-300">Repository Name</th>
          <th class="border-b border-slate-300">Plugins</th>
          <th class="border-b border-slate-300">Delete</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="repo in store.repositories" class="border-b-2">
          <td class="border-b border-slate-300">{{ repo.name }}</td>
          <td class="border-b border-slate-300">{{ repo.pluginCount }}</td>
          <td class="border-b border-slate-300">
            <button
              class="bg-red-500 text-white rounded-lg shadow-lg p-2 hover:bg-red-800 hover:scale-110 transition ease-in-out"
            >
              <i class="fas fa-trash"></i>
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <router-link :to="{ name: 'NewRepository' }">
      <button
        class="self-start bg-gray-500 rounded shadow text-white font-semibold py-2 px-2 hover:bg-gray-300 hover:text-black"
      >
        New Repository
      </button>
    </router-link>
  </div>
</template>

<script setup lang="ts">
import useRepositoryStore from "@/stores/repository-store";

const store = useRepositoryStore();

store.fetch();
</script>
