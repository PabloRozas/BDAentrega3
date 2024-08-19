<script>
import axios from "axios";
import anime from "animejs";
import { useAuthStore } from "../../stores/useAuthStore";

export default {
  data() {
    return {
      map: null,
      tareas: [],
      markers: [],
      opcionMenuNav: 0,
      listTareas: false,
      emergencias: [
        { id: 1, nombre: "Incendio" },
        { id: 2, nombre: "Inundación" },
        { id: 3, nombre: "Terremoto" },
        { id: 4, nombre: "Tsunami" },
        { id: 5, nombre: "Erupción volcánica" },
        { id: 6, nombre: "Deslizamiento" },
        { id: 7, nombre: "Tormenta" },
        { id: 8, nombre: "Tornado" },
        { id: 9, nombre: "Huracán" },
        { id: 10, nombre: "Ciclón" },
        { id: 11, nombre: "Tifón" },
        { id: 12, nombre: "Ola de calor" },
        { id: 13, nombre: "Ola de frío" },
        { id: 14, nombre: "Ola de viento" },
        { id: 15, nombre: "Ola de nieve" },
        { id: 16, nombre: "Ola de lluvia" },
        { id: 17, nombre: "Ola de granizo" },
        { id: 18, nombre: "Ola de rayos" },
        { id: 19, nombre: "Ola de viento" },
        { id: 20, nombre: "Ola de nieve" },
        { id: 21, nombre: "Ola de lluvia" },
        { id: 22, nombre: "Ola de granizo" },
        { id: 23, nombre: "Ola de rayos" },
        { id: 24, nombre: "Ola de viento" },
        { id: 25, nombre: "Ola de nieve" },
        { id: 26, nombre: "Ola de lluvia" },
        { id: 27, nombre: "Ola de granizo" },
        { id: 28, nombre: "Ola de rayos" },
        { id: 29, nombre: "Ola de viento" },
        { id: 30, nombre: "Ola de nieve" },
      ],
      voluntarios: [],
    };
  },
  mounted() {
    this.auth = useAuthStore();
    this.initMap();
  },
  methods: {
    initMap() {
      const script = document.createElement("script");
      script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyDd1yMKvX4PyaxaVtyauISsGrMvxYi6CgQ&libraries=places`;
      script.async = true;
      script.onload = () => {
        const styles = [
          {
            featureType: "poi",
            stylers: [{ visibility: "off" }],
          },
          {
            featureType: "transit",
            stylers: [{ visibility: "off" }],
          },
        ];
        const map = new google.maps.Map(document.getElementById("map"), {
          center: { lat: -38.447308, lng: -85.664213 },
          zoom: 4.19,
          styles: styles,
        });

        this.map = map;
        axios
          .get(
            "http://localhost:8080/regiones/all",
            // TODO: cambiar el token por el que se obtenga en el login que debe estar guardado en el session storage
            {
              headers: {
                Authorization: `Bearer ${this.auth.token}`,
              },
            }
          )
          .then((response) => {
            response.data.forEach((region) => {
              const polygons = this.wktToLatLng(region.geometria);
              polygons.forEach((polygon) => {
                var poligono = new google.maps.Polygon({
                  paths: polygon,
                  strokeColor: "#FF0000",
                  strokeOpacity: 0.8,
                  strokeWeight: 2,
                  fillColor: "#FF0000",
                  fillOpacity: 0.35,
                });
                poligono.setMap(this.map);
                poligono.addListener("click", () => {
                  this.showPolygonInfo(region.id, region.nombre);
                });
              });
            });
          });
      };
      document.head.appendChild(script);
    },
    wktToLatLng(wkt) {
      if (!wkt || typeof wkt !== "string") {
        console.error("Invalid WKT:", wkt);
        return [];
      }

      const multipolygonMatch = wkt.match(/MULTIPOLYGON\s*\(\(\((.+)\)\)\)/);
      // console.log(multipolygonMatch[1]);
      // Si dentro de multipolygonMatch existen )) o (( se debe agregar un ( al inicio y un ) al final para luego reemplazar todos los (( por ( y todos los )) por ) y luego dividir por ),( para obtener los poligonos
      if (
        multipolygonMatch[1].includes("))") ||
        multipolygonMatch[1].includes("((")
      ) {
        const newString = "(" + multipolygonMatch[1] + ")";
        const newString2 = newString
          .replace(/\(\(/g, "(")
          .replace(/\)\)/g, ")");
        multipolygonMatch[1] = newString2;
      }

      // console.log(multipolygonMatch[1]);

      // Si contiene ( o ) se debe separar en distintos poligonos
      if (
        multipolygonMatch[1].includes("(") ||
        multipolygonMatch[1].includes(")")
      ) {
        // Se hace un split por )
        const newString = multipolygonMatch[1].split("),");
        // Se recorre cada poligono y se eliminan los (
        newString.forEach((element, index) => {
          newString[index] = element.replace("(", "");
          newString[index] = newString[index].replace(")", "");
        });
        // Se printea el nuevo string
        // console.log(newString);

        // Se crea un array de poligonos
        const polygons = newString;
        // Se crea un array de paths
        const paths = polygons.map((polygon) => {
          return polygon.split(",").map((coord) => {
            const [lng, lat] = coord.trim().split(" ").map(Number);
            return { lat, lng };
          });
        });
        return paths;
      } else {
        if (!multipolygonMatch) {
          // console.error('WKT is not a valid MULTIPOLYGON:', wkt);
          return [];
        }

        const coordinatesString = multipolygonMatch[1];
        const polygons = coordinatesString.split("),(");
        // console.log("polygons", polygons);
        const paths = polygons.map((polygon) => {
          return polygon.split(",").map((coord) => {
            const [lng, lat] = coord.trim().split(" ").map(Number);
            // console.log("lat", lat, "lng", lng);
            return { lat, lng };
          });
        });
        return paths;
      }
    },
    showPolygonInfo(id, name) {
      axios
        .get(
          `http://localhost:8080/tarea/tareaRegion/${name}`,
          // TODO: cambiar el token por el que se obtenga en el login que debe estar guardado en el local storage
          {
            headers: {
              Authorization: `Bearer ${this.auth.token}`,
            },
          }
        )
        .then((response) => {
          console.log(response.data);
          this.listTareas = true;
          this.clearMarkers();
          this.tareas = response.data;
          response.data.forEach((tarea) => {
            console.log(tarea.zona.y, tarea.zona.x, tarea.nombre);
            this.addMarker(tarea.zona.y, tarea.zona.x, tarea.nombre);
          });
        })
        .catch((error) => {
          console.error(error);
        });
    },
    addMarker(lat, lng, title) {
      const marker = new google.maps.Marker({
        position: { lat, lng },
        map: this.map,
        title: title,
      });
      this.markers.push(marker);
    },
    clearMarkers() {
      this.markers.forEach((marker) => {
        google.maps.event.clearInstanceListeners(marker);
        marker.setMap(null);
      });
      this.markers = [];
    },
    async getEmergencias() {
      try {
        const response = await axios.get(
          "http://localhost:8080/emergencia/all",
          {
            headers: {
              Authorization: `Bearer ${this.auth.token}`,
            },
          }
        );
        console.log(response.data);
        this.emergencias = response.data;
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>

<template>
  <div class="container-admin">
    <section class="section1">
      <div id="map" style="height: 100%; width: 100%"></div>
    </section>
    <section class="section2">
      <!-- lista de las emergencias -->
      <div class="containerListTareasRegiones">
        <h1 style="width: calc(100% - 20px); color: black; margin: 10px">
          Emergencias
        </h1>
        <div
          style="
            width: calc(100% - 20px);
            margin: 10px 0;
            max-height: calc(100% - 60px);
            overflow-y: auto;
          "
        >
          <ul class="listaTareasRegiones">
            <li
              v-for="emergencia in emergencias"
              :key="emergencia.id"
              class="listaTareas"
              style="padding-left: 20px"
            >
              <span>
                <img
                  src="../../assets/images/sirena-encendida.svg"
                  alt="Sirena"
                />
              </span>
              {{ emergencia.nombre }}
            </li>
          </ul>
        </div>
      </div>
      <div class="containerListTareasRegiones">
        <div v-if="!listTareas" class="containerTareasVacio">
          <h1>Tareas de la región</h1>
          <p>Seleccione una región para ver las tareas</p>
        </div>
        <ul v-if="listTareas && tareas.length > 0" class="listaTareasRegiones">
          <li v-for="tarea in tareas" :key="tarea.id" class="listaTareas">
            <span>
              <img src="../../assets/images/marcador.svg" alt="Marcador" />
            </span>
            {{ tarea.nombre }}
          </li>
        </ul>
        <div
          v-if="listTareas && tareas.length === 0"
          class="containerTareasVacio"
        >
          <h1>No hay tareas en esta región</h1>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.listaTareas {
  display: flex;
  align-items: center;
  padding: 5px 10px;
}

.containerTareasVacio {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  color: black;
}

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
