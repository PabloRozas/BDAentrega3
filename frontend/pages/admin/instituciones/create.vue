<template>
  <!-- Formulario creacion de institución -->
  <div class="containerForm">
    <form>
      <h1>Crear Institución</h1>
      <input type="text" placeholder="Nombre" v-model="institucion" />
      <button @click="crearInstitucion">Crear Institución</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";

const auth = useAuthStore();
const institucion = ref("");

const crearInstitucion = async (event: { preventDefault: () => void }) => {
  event.preventDefault();
  try {
    const response = await axios.post(
      `http://localhost:8080/institucion/add`,
      {
        nombreInstitucion: institucion.value,
      },
      {
        headers: {
          Authorization: `Bearer ${auth.token}`,
        },
      }
    );
    console.log(response.data);
  } catch (error) {}
};
</script>
