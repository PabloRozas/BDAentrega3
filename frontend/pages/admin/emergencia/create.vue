<template>
  <div class="containerForm">
    <form>
      <h1>Crear Emergencia</h1>
      <input
        type="text"
        placeholder="Tipo de Emergencia"
        v-model="tipoEmergencia"
      />
      <input
        type="text"
        placeholder="Condición Física"
        v-model="condicionFisica"
      />
      <input
        type="number"
        placeholder="Cantidad de Voluntarios Mínimo"
        v-model="cantidadVoluntariosMinimo"
      />
      <input
        type="number"
        placeholder="Cantidad de Voluntarios Máximo"
        v-model="cantidadVoluntariosMaximo"
      />
      <select v-model="idInstitucion" class="selectInstituciones">
        <option value="" disabled selected>Seleccione una institución</option>
        <option
          v-for="institucion in instituciones"
          :key="institucion.id"
          :value="institucion.id"
        >
          {{ institucion.nombre }}
        </option>
      </select>
      <!-- <Select
        v-model="idInstitucion"
        :options="instituciones"
        optionLabel="nombre"
        optionValue="id"
        placeholder="Seleccione una institución"
        style="
          width: 100%;
          padding: 5px;
          margin: 5px 0;
          border: 1px solid black;
          border-radius: 5px;
        "
      /> -->
      <input
        type="text"
        placeholder="Dirección"
        v-model="direccion"
        id="autocomplete"
      />
      <button @click="crearEmergencia">Crear Emergencia</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import { ref, toRef } from "vue";

const auth = useAuthStore();
const tipoEmergencia = ref("");
const condicionFisica = ref("");
const cantidadVoluntariosMinimo = ref(0);
const cantidadVoluntariosMaximo = ref(0);
const idInstitucion = ref("");
const direccion = ref("");
const latitud = ref<number | null>(null);
const longitud = ref<number | null>(null);

const autocomplete = ref<google.maps.places.Autocomplete | null>(null);

const instituciones = ref([
  {
    id: "",
    nombre: "",
    emergencia: [],
  },
]);

async function getInstituciones() {
  try {
    const response = await axios.get(`http://localhost:8080/institucion/all`, {
      headers: {
        Authorization: `Bearer ${auth.token}`,
      },
    });
    console.log(response.data);

    instituciones.value = response.data;
    console.log(instituciones);
  } catch (error) {
    console.error(error);
  }
}

onMounted(() => {
  getInstituciones();
  initAutocomplete();
});

const initAutocomplete = () => {
  // Asegúrate de que la API de Google Maps esté cargada antes de ejecutar esto
  const script = document.createElement("script");
  script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyDd1yMKvX4PyaxaVtyauISsGrMvxYi6CgQ&libraries=places`;
  document.head.appendChild(script);

  script.onload = () => {
    const google = (window as any).google; // Evita el error de TS al tipar google como any
    const inputElement = document.getElementById(
      "autocomplete"
    ) as HTMLInputElement;

    autocomplete.value = new google.maps.places.Autocomplete(inputElement, {
      types: ["geocode"],
    });

    autocomplete.value.addListener("place_changed", onPlaceChanged);
  };
};

const onPlaceChanged = () => {
  if (autocomplete.value) {
    const place = autocomplete.value.getPlace();
    if (place.geometry) {
      latitud.value = place.geometry.location.lat();
      longitud.value = place.geometry.location.lng();
    }
  }
};

const crearEmergencia = async (event: { preventDefault: () => void }) => {
  event.preventDefault();
  try {
    const response = await axios.post(
      `http://localhost:8080/emergencia/add`,
      {
        tipoEmergencia: tipoEmergencia.value,
        condicionFisica: condicionFisica.value,
        cantidadVoluntariosMinimo: cantidadVoluntariosMinimo.value,
        cantidadVoluntariosMaximo: cantidadVoluntariosMaximo.value,
        idInstitucion: idInstitucion.value,
        latitud: latitud.value,
        longitud: longitud.value,
      },
      {
        headers: {
          Authorization: `Bearer ${auth.token}`,
        },
      }
    );
    console.log(response.data);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style scoped>
.selectInstituciones {
  width: 100%;
  padding: 5px;
  margin: 5px 0;
  border: 1px solid black;
  border-radius: 5px;
}

.selectInstituciones option {
  padding: 5px;
}
</style>
