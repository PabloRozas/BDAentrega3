<template>
    <div class="container-admin">
        <header :class="{ 'open': menuOpen }" class="menu-container">

            <img src="../images/x.svg" alt="" @click="toggleMenu">
            <svg width="100%" height="100vh" viewBox="0 0 100 100" preserveAspectRatio="none">
                <path :d="pathMenu" class="svg-path" ref="path"></path>
            </svg>
        </header>

        <nav class="nav-back">
            <img src="../images/menu-hamburguesa.svg" id="toggle-menu" @click="toggleMenu">
            <h1>RES-Q</h1>
            <div class="containerTitleNav">
                <h3>Vista Coordinador</h3>
            </div>
        </nav>

        <main>
            <img src="../images/mundo.svg" alt="mundo" class="imgFondo">
            <section class="section1">
                <div id="map" style="height: 100%; width: 100%;"></div>
            </section>
            <section class="section2">
                <div class="botonEmeregenciaOff" :class="{ 'active': menuEmergencias }">
                    <div v-if="!menuEmergencias" @click="toggleEmergencias"
                        style="height: 60px; width: 100%; display: flex; justify-content: center; align-items: center;">
                        Crear
                        Emergencia
                    </div>
                    <div v-else>

                        <!-- TODO: aqui debe ir el form de Emergencia -->
                    </div>
                </div>
                <!-- TODO: en la segunda fila debe ir la leyenda del mapa con los puntos y su significado, debe cambiar a la lista de las emergencias cuando se seleccione en el mapa -->
                <!-- lista de las tareas -->
                <div class="containerListTareasRegiones">
                    <ul>
                        <li v-for="tarea in tareas" :key="tarea.id">
                            <span class="icon-container">
                                <img src="../images/marcador.svg" alt="Marcador" />
                            </span>
                            {{ tarea[3] }}
                        </li>
                    </ul>
                </div>
            </section>
        </main>
        <div class="prueba">

        </div>


    </div>


</template>

<script>
import axios from 'axios';
import anime from 'animejs';
export default {
    data() {
        return {
            map: null,
            tareas: [],
            markers: [],
            menuOpen: false,
            pathMenu: "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 70 0 100  Z",
            menuEmergencias: false,
        };
    },
    mounted() {
        this.initMap();
    },
    methods: {
        toggleMenu() {
            this.menuOpen = !this.menuOpen;
            const targetPath = this.menuOpen ? "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 100 0 100 Z" : "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 70 0 100  Z";

            anime({
                targets: this.$refs.path,
                d: [
                    { value: targetPath }
                ],
                duration: 1000,
                easing: 'easeInOutQuad'
            });
        },
        toggleEmergencias() {
            this.menuEmergencias = !this.menuEmergencias;
        },
        initMap() {
            const script = document.createElement('script');
            script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyDd1yMKvX4PyaxaVtyauISsGrMvxYi6CgQ&libraries=places`;
            script.async = true;
            script.onload = () => {
                const styles = [
                    {
                        "featureType": "poi",
                        "stylers": [
                            { "visibility": "off" }
                        ]
                    },
                    {
                        "featureType": "transit",
                        "stylers": [
                            { "visibility": "off" }
                        ]
                    }];
                const map = new google.maps.Map(document.getElementById('map'), {
                    center: { lat: -38.447308, lng: -70.664213 },
                    zoom: 4.19,
                    styles: styles,
                });

                this.map = map;

                axios.get('http://localhost:8080/regiones/all',
                    // TODO: cambiar el token por el que se obtenga en el login que debe estar guardado en el local storage
                    { headers: { 'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJudW1lcm9Eb2N1bWVudG9Wb2x1bnRhcmlvIjoiMjA4NDczODgtNSIsIm5vbWJyZVZvbHVudGFyaW8iOiJuaWNvIiwic3ViIjoibmljb0BnbWFpbC5jb20iLCJpYXQiOjE3MjA0MTA4NjEsImV4cCI6MTcyMDg0Mjg2MX0.vLfV6E5MyAwVtJJv_qJ0-cy2LMXALRaLVqZpNktbILQ` } })
                    .then(response => {
                        response.data.forEach(region => {
                            const polygons = this.wktToLatLng(region.geometria);
                            polygons.forEach(polygon => {
                                var poligono = new google.maps.Polygon({
                                    paths: polygon,
                                    strokeColor: '#FF0000',
                                    strokeOpacity: 0.8,
                                    strokeWeight: 2,
                                    fillColor: '#FF0000',
                                    fillOpacity: 0.35
                                });
                                poligono.setMap(this.map);
                                poligono.addListener('click', () => {
                                    this.showPolygonInfo(region.id, region.nombre);
                                });
                            });
                        });
                    })
            };
            document.head.appendChild(script);


        },
        wktToLatLng(wkt) {
            if (!wkt || typeof wkt !== 'string') {
                console.error('Invalid WKT:', wkt);
                return [];
            }

            const multipolygonMatch = wkt.match(/MULTIPOLYGON\s*\(\(\((.+)\)\)\)/);
            // console.log(multipolygonMatch[1]);
            // Si dentro de multipolygonMatch existen )) o (( se debe agregar un ( al inicio y un ) al final para luego reemplazar todos los (( por ( y todos los )) por ) y luego dividir por ),( para obtener los poligonos
            if (multipolygonMatch[1].includes('))') || multipolygonMatch[1].includes('((')) {
                const newString = '(' + multipolygonMatch[1] + ')';
                const newString2 = newString.replace(/\(\(/g, '(').replace(/\)\)/g, ')');
                multipolygonMatch[1] = newString2;
            }

            // console.log(multipolygonMatch[1]);

            // Si contiene ( o ) se debe separar en distintos poligonos
            if (multipolygonMatch[1].includes('(') || multipolygonMatch[1].includes(')')) {
                // Se hace un split por )
                const newString = multipolygonMatch[1].split('),');
                // Se recorre cada poligono y se eliminan los (
                newString.forEach((element, index) => {
                    newString[index] = element.replace('(', '');
                    newString[index] = newString[index].replace(')', '');
                });
                // Se printea el nuevo string
                // console.log(newString);

                // Se crea un array de poligonos
                const polygons = newString;
                // Se crea un array de paths
                const paths = polygons.map(polygon => {
                    return polygon.split(',').map(coord => {
                        const [lng, lat] = coord.trim().split(' ').map(Number);
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
                const polygons = coordinatesString.split('),(');
                // console.log("polygons", polygons);
                const paths = polygons.map(polygon => {
                    return polygon.split(',').map(coord => {
                        const [lng, lat] = coord.trim().split(' ').map(Number);
                        // console.log("lat", lat, "lng", lng);
                        return { lat, lng };
                    });
                });
                return paths;
            }




        },
        showPolygonInfo(id, name) {
            axios.get(`http://localhost:8080/tarea/tareaRegion/${name}`,
                // TODO: cambiar el token por el que se obtenga en el login que debe estar guardado en el local storage
                { headers: { 'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJudW1lcm9Eb2N1bWVudG9Wb2x1bnRhcmlvIjoiMjA4NDczODctNSIsIm5vbWJyZVZvbHVudGFyaW8iOiJQYWJsbyIsInN1YiI6InBhYmxvQGdtYWlsLmNvbSIsImlhdCI6MTcxNjgzMjY1MCwiZXhwIjoxNzE3MjY0NjUwfQ.eDYFXSKcqxxFFD481vS3yGB0rWl3aqbLXOsiWM4wWHY` } })
                .then(response => {
                    this.clearMarkers();
                    this.tareas = response.data;
                    response.data.forEach(tarea => {
                        this.addMarker(tarea[4], tarea[5], tarea[3]);
                    });
                }).catch(error => {
                    console.error(error);
                })
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
            this.markers.forEach(marker => {
                google.maps.event.clearInstanceListeners(marker);
                marker.setMap(null);
            });
            this.markers = [];
        },
    },
};

</script>

<style scoped>
body {
    margin: 0;
    padding: 0;
}

.svg-path {
    position: absolute;
    fill: black;
    z-index: 1;
}



/* Estilos del menu (toggle) */
.menu-container {
    position: absolute;
    top: -100%;
    left: 0;
    width: 100%;
    height: 100vh;
    background-color: none;
    transition: top 0.5s ease;
    z-index: 1000;
}

#toggle-menu {
    width: 30px;
    height: 30px;
    margin: 20px;
    filter: invert(1);
    cursor: pointer;
    z-index: 100;
}

header img {
    position: absolute;
    width: 30px;
    height: 30px;
    margin: 20px;
    filter: invert(1);
    cursor: pointer;
    z-index: 1000;
}

header svg {
    position: absolute;
    transition: 0.5s all ease-in-out;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
}

.menu-container.open {
    top: 0;
}

.nav-back {
    background-color: #000000;
    width: 100%;
    height: 70px;
    display: flex;
    justify-content: left;
    align-items: center;
    z-index: 1;
}


.imgFondo {
    top: 0;
    width: 100%;
    left: 0;
    height: 100vh;
    position: absolute;
    z-index: -10;
    filter: brightness(0.2) invert(1);
    object-fit: cover;
}

.container-admin {
    position: absolute;
    margin: 0;
    padding: 0;
    width: 100%;
    height: 100vh;
    color: white;
    z-index: 1;
}

nav img {
    width: 30px;
    height: 30px;
    margin: 20px;
    filter: invert(1);
    cursor: pointer;
    z-index: 1;
}

nav {
    background-color: #000000;
    width: 100%;
    height: 70px;
    display: flex;
    justify-content: left;
    align-items: center;
    z-index: 1;
}

nav h1 {
    color: #ffffff;
    font-size: 30px;
    font-family: 'Lemon Milk', sans-serif;
}

nav h3 {
    color: #ffffff;
    font-size: 25px;
    margin: 0%;
    margin-left: 20px;
    font-family: 'Reboto', sans-serif;
}

.containerTitleNav {
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;

}

main {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 20px;
    margin: 20px;
    width: 97%;
    height: 84vh;
}

.section2 {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 1fr;
}

.botonEmeregenciaOff {
    transition: 0.3s all;
    background-color: #000000;
    color: #ffffff;
    font-size: 20px;
    font-family: 'Roboto-Regular', sans-serif;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    margin: 20px;
    padding: 10px;
    width: 230px;
    height: 50px;
    justify-self: center;
    align-self: center;
    justify-content: center;
    align-items: center;
    display: flex;
}

.botonEmeregenciaOff.active {
    transition: 0.3s all;
    width: 100%;
    height: 85%;
    padding: 0;
    background-color: #ffffff;
    color: #000000;
    border: 1px solid #000000;
    border-radius: 5px;
    display: flex;
    cursor: default;
}

.botonEmeregenciaOff:hover {
    background-color: #ffffff;
    color: #000000;
    border: 1px solid #000000;
}


.containerListTareasRegiones {
    width: 100%;
    height: 300px;
    border: 1px solid black;
    border-radius: 5px;
    overflow: auto;
    background-color: #ffffff;
    font-family: 'Roboto-Regular', sans-serif;
}

.containerListTareasRegiones ul {
    padding: 0;
}

.containerListTareasRegiones li {
    padding: 5px;
    padding-left: 10px;
    color: black;
    cursor: pointer;
}

.containerListTareasRegiones span img {
    width: 20px;
    height: 20px;
    margin-right: 10px;
    position: relative;
}

.icon-container {
    display: inline-block;
    width: 20px;
    height: 20px;
    margin-right: 10px;
    position: relative;
    top: 5px;
}

.containerListTareasRegiones li:hover {
    background-color: #f2f2f2;
}
</style>
