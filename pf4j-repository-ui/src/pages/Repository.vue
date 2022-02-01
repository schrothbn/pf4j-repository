<template></template>

<script setup lang="ts">
import { ref } from "vue";
import { useRoute } from "vue-router";
import RepositoryService from "@/services/repository-service";

const repository = ref(null);
const route = useRoute();
const repoName = route.params.name;

const loadData = async () => {
  RepositoryService.getRepository(<string>repoName).then((data) =>
    data.json().then((json) => (repository = json))
  );
};
</script>
