<template>
  <ListModels :models="instituciones" title="Instituciones" />
</template>

<script setup lang="ts">
import ListModels from "@/components/ListModels.vue";
import axios from "axios";

const auth = useAuthStore();

useHead({
  title: "RES-Q - Instituciones",
});

const instituciones = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:8080/institucion/all`, {
      headers: {
        Authorization: `Bearer ${auth.token}`,
      },
    });
    console.log(response.data);
    instituciones.value = response.data;
  } catch (error) {
    console.error(error);
  }
});
</script>
