<template>
  <div
    v-if="errorMsg"
    class="bg-red-300 text-red-800 rounded shadow px-4 mb-10"
  >
    {{ errorMsg }}
  </div>

  <form @submit.prevent="submit" class="flex flex-col gap-3">
    <label for="repository-name" class="text-gray-800 text-sm">Name:</label>
    <input
      type="text"
      class="w-full border-0 focus:outline-none border-b-2"
      placeholder="Repository Name"
      v-model="name"
    />
    <button
      type="submit"
      class="py-2 px-4 self-end bg-gray-800 text-white rounded-xl shadow hover:bg-gray-300 hover:text-black"
    >
      Create Repository
    </button>
  </form>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
import useRepositoryStore from "@/stores/repository-store";

export default defineComponent({
  name: "NewRepositoryPage",
  setup() {
    const store = useRepositoryStore();
    const name = ref<string>("");
    const errorMsg = ref<string | null>(null);

    const router = useRouter();

    const submit = async () => {
      if (name.value !== "") {
        store
          .create(name.value)
          .then(() => {
            router.push({ name: "Home" });
          })
          .catch((err) => (errorMsg.value = `Error: ${err}`));
      }
    };

    return {
      name,
      errorMsg,
      submit,
    };
  },
});
</script>
