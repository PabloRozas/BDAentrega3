<template>
  <div style="display: flex; width: 100%; height: 100%">
    <div class="containerEmergenciaInfo">
      <h1>Emergencia</h1>
      <div>
        <h2>Información</h2>
        <div>
          <p>Nombre: {{ emergencia.nombre }}</p>
          <p>Condición Física: {{ emergencia.condicionFisica }}</p>
          <p>
            Cantidad de Voluntarios Mínimo:
            {{ emergencia.cantidadVoluntariosMinimo }}
          </p>
          <p>
            Cantidad de Voluntarios Máximo:
            {{ emergencia.cantidadVoluntariosMaximo }}
          </p>
          <p>Institución: {{ emergencia.institucion.nombre }}</p>
          <p>Dirección: {{ emergencia.direccion }}</p>
          <p>Latitud: {{ emergencia.latitud }}</p>
          <p>Longitud: {{ emergencia.longitud }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";

const emergencia = ref({
  nombre: "",
  condicionFisica: "",
  cantidadVoluntariosMinimo: 0,
  cantidadVoluntariosMaximo: 0,
  institucion: {
    nombre: "",
  },
  direccion: "",
  latitud: 0,
  longitud: 0,
});
</script>

<style scoped>
.containerEmergenciaInfo {
  display: flex;
  width: 100%;
  height: 100%;
  color: black;
  flex-direction: column;
  padding: 20px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(2px);
}

.containerEmergenciaInfo h1 {
  font-size: 35px;
  margin-bottom: 20px;
}

.containerEmergenciaInfo h2 {
  font-size: 25px;
  margin-bottom: 10px;
}
</style>
