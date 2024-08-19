<template>
  <ListModels :models="voluntarios" title="Voluntarios" />
</template>

<script setup lang="ts">
import ListModels from "@/components/ListModels.vue";
import axios from "axios";

const auth = useAuthStore();

useHead({
  title: "RES-Q - Voluntarios",
});

const voluntarios = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:8080/voluntario/all`, {
      headers: {
        Authorization: `Bearer ${auth.token}`,
      },
    });
    console.log(response.data);
    voluntarios.value = response.data;
  } catch (error) {
    console.error(error);
  }
});
</script>
