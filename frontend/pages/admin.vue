<template>
    <div class="container-admin">
        <div id="circle"></div>
        <header :class="{ 'open': menuOpen }" class="menu-container">

            <!-- <img src="../images/x.svg" alt="" @click="toggleMenu"> -->
            <svg width="100%" height="100vh" viewBox="0 0 100 100" preserveAspectRatio="none">
                <path :d="pathMenu" class="svg-path" ref="path">
                </path>
            </svg>
            <section class="menuHeader">
                <section class="menu1-container">
                    <h1 @click="opcionMenu(0)" :class="{ 'active': opcionMenuNav === 0 }">RES-Q</h1>
                    <h1 @click="opcionMenu(1)" :class="{ 'active': opcionMenuNav === 1 }">Voluntarios</h1>
                    <h1 @click="opcionMenu(2)" :class="{ 'active': opcionMenuNav === 2 }">Coordinadores</h1>
                    <h1 @click="opcionMenu(3)" :class="{ 'active': opcionMenuNav === 3 }">Emergencias</h1>
                </section>
                <section v-if="opcionMenuNav === 0" class="menu2-container mainMenu">
                    <div>
                        <h1>Perfil</h1>
                        <h1 @click="logout">Cerrar Sesión</h1>
                    </div>
                    <div>
                        <h1 style="font-size: 20px;">Pablo Macuada Rozas</h1>
                        <h1 style="font-size: 20px;">Macarena Soto Herrera</h1>
                        <h1 style="font-size: 20px;">Nicolas Salinas</h1>
                        <h1 style="font-size: 20px;">Nicolas Savedra</h1>
                        <h1 style="font-size: 20px;">Nicolas Muñoz</h1>
                    </div>
                </section>
                <section v-if="opcionMenuNav === 1" class="menu2-container">
                    <h1>Lista de Voluntarios</h1>
                    <h1>Crear Voluntario</h1>
                    <h1>Eliminar Voluntario</h1>
                </section>
                <section v-if="opcionMenuNav === 2" class="menu2-container">
                    <h1>Lista de Coordinadores</h1>
                    <h1>Crear Coordinador</h1>
                    <h1>Eliminar Coordinador</h1>
                </section>
                <section v-if="opcionMenuNav === 3" class="menu2-container">
                    <h1>Emergencias</h1>
                    <h1>Crear Emergencia</h1>
                    <h1>Eliminar Emergencia</h1>
                </section>
            </section>
        </header>

        <nav class="nav-back">
            <svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg" id="toggle-menu" @click="toggleMenu">
                <path fill="" :d="toggleMenuPath" ref="iconMenu"></path>
            </svg>
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
                    <div v-if="menuEmergencias">
                        <button class="emergenciaBack" @click="toggleEmergencias">Salir</button>

                        <!-- TODO: aqui debe ir el form de Emergencia -->
                    </div>
                    <div v-if="menuEmergencias">
                        <form class="formaEmergencias">
                            <h1>Crear Emergencia</h1>
                            <input type="text" placeholder="Tipo de Emergencia">
                            <input type="text" placeholder="Condición Fisica">
                            <input type="number" placeholder="Cantidad de voluntarios">
                            <input type="text" placeholder="Institución">
                            <input type="text" placeholder="Ubicación">
                            <button class="botonEnviarEmergencia">Crear Emergencia</button>
                        </form>
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
            toggleMenuPath: "M10 25L90 25zM10 50C50 50 50 50 90 50C50 50 50 50 10 50zM10 75L90 75z",
            menuEmergencias: false,
            opcionMenuNav: 0,
        };
    },
    mounted() {
        this.initMap(),
            setInterval(() => {
                // posicion random en x y y dentro de los limites de la pantalla
                const x = Math.random() * window.innerWidth - 50;
                const y = Math.random() * window.innerHeight - 50;
                // color random entre las opciones de rojo y naranja
                const color = Math.random() > 0.5 ? 'red' : 'orange';
                // crear el circle
                this.createCircles(x, y, color);
            }, 500);
    },
    methods: {
        opcionMenu(opcion) {
            this.opcionMenuNav = opcion;
        },
        createCircles(x, y, color) {
            const svgNS = "http://www.w3.org/2000/svg"; // Namespace for SVG elements

            // Create SVG container
            const svg = document.createElementNS(svgNS, "svg");
            svg.setAttribute("width", "50px");
            svg.setAttribute("height", "50px");
            svg.setAttribute("viewBox", "0 0 100 100");
            svg.style.position = "absolute";
            svg.style.left = x + 'px';
            svg.style.top = y + 'px';
            svg.style.transform = "translate(-50%, -50%)";
            svg.style.transition = "all 3s";

            // Create circle element
            const circle = document.createElementNS(svgNS, "circle");
            circle.setAttribute("cx", "50");
            circle.setAttribute("cy", "50");
            circle.setAttribute("r", "0");
            circle.setAttribute("fill", "none");
            circle.setAttribute("stroke", color);
            circle.setAttribute("stroke-width", "4");
            circle.classList.add('animate-circle');

            // Append circle to SVG container
            svg.appendChild(circle);

            // Append SVG to the container
            document.getElementById('circle').appendChild(svg);


            // Trigger the animation
            setTimeout(() => {
                circle.setAttribute("r", "20"); // Change radius to 10
                circle.setAttribute("stroke-width", "0"); // Change stroke width to 0
            }, 0);


            // Remove the SVG after 4 seconds
            setTimeout(() => {
                svg.remove();
            }, 2000);
        },
        toggleMenu() {
            this.menuOpen = !this.menuOpen;
            const targetPath = this.menuOpen ? "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 100 0 100 Z" : "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 70 0 100  Z";
            const pathToogleMenu = this.menuOpen ? "M30 30L70 70zM13 50C15 100 85 100 87 50C85 0 15 0 13 50zM30 70L70 30z" : "M10 25L90 25zM10 50C50 50 50 50 90 50C50 50 50 50 10 50zM10 75L90 75z";

            anime({
                targets: this.$refs.path,
                d: [
                    { value: targetPath }
                ],
                duration: 1000,
                easing: 'easeInOutQuad'
            });
            anime({
                targets: this.$refs.iconMenu,
                d: [
                    { value: pathToogleMenu }
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
                    center: { lat: -38.447308, lng: -85.664213 },
                    zoom: 4.19,
                    styles: styles,
                });

                this.map = map;

                axios.get('http://localhost:8080/regiones/all',
                    // TODO: cambiar el token por el que se obtenga en el login que debe estar guardado en el local storage
                    { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } })
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
                { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } })
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
        logout() {
            localStorage.removeItem('token');
            this.$router.push('/');
        }
    },
};

</script>

<style scoped>
body {
    margin: 0;
    padding: 0;
}

.mainMenu {
    display: grid;
    grid-template-rows: 1fr 1fr;
    grid-template-columns: 1fr;
    gap: 10%;

}

#circle svg {
    z-index: -1;
    filter: blur(0.2px);
    transition: all 3s;
}


.svg-path {
    position: absolute;
    fill: black;
    z-index: 1;
}

.formaEmergencias {
    display: grid;
    grid-template-rows: 15% 1fr 1fr 1fr 1fr 1fr 1fr;
    grid-template-columns: 1fr;
    grid-template-areas: "nombre" "descripcion" "fecha" "hora" "ubicacion" "boton";
    gap: 10px;
    padding: 10px;
    padding-top: 0;
}

.formaEmergencias h1 {
    grid-area: nombre;
    margin: 0;
    font-size: 25px;
    margin-bottom: 5px;
    font-family: 'Astonpoliz', sans-serif;
    /* justify-self: center; */
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

.botonEnviarEmergencia {
    background-color: #000000;
    color: #ffffff;
    font-size: 16px;
    font-family: 'Astonpoliz', sans-serif;
    border: none;
    border-radius: 4px;

}

#toggle-menu {
    width: 30px;
    height: 30px;
    margin: 20px;
    stroke: #ffffff;
    stroke-width: 9;
    cursor: pointer;
    z-index: 2000;
}

header {
    display: flex;
    width: 100%;
    height: 100vh;
    z-index: 1;
}

header img {
    /* position: absolute; */
    width: 30px;
    height: 30px;
    margin: 20px;
    filter: invert(1);
    cursor: pointer;
    z-index: 1000;
}

header h1 {
    /* position: absolute; */
    color: #ffffff;
    font-size: 30px;
    font-family: 'Lemon Milk', sans-serif;
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

.menuHeader {
    position: absolute;
    display: grid;
    /* La primera columna usa el 70% y la segunda el 30% */
    grid-template-columns: 70% 30%;
    grid-template-rows: 1fr;
    width: 100%;
    height: 100%;
    z-index: 1;

}

.menu1-container {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    justify-content: center;
    z-index: 1;
}

.menu1-container h1 {
    color: #ffffff;
    filter: brightness(0.5);
    transition: 0.3s all;
    margin: 0;
    margin-left: 5%;
    font-size: 100px;
    z-index: 1;
}

.menu1-container h1:hover {
    color: #9AEBA3;
    filter: brightness(1);
    cursor: pointer;
}

.menu1-container h1.active {
    color: #DAFDBA;
    filter: brightness(1);
}

.menu2-container {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    justify-content: center;
    z-index: 1;
    transition: 0.3s all;
    animation: fadeIn 1s;
}

.menu2-container h1 {
    cursor: pointer;
    transition: 0.3s all;
}

.menu2-container h1:hover {
    color: #9AEBA3;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
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
    font-family: 'Astonpoliz', sans-serif;
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
    font-family: 'Astonpoliz', sans-serif;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    margin: 20px;
    width: 230px;
    height: 60px;
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
    display: grid;
    grid-template-rows: 15% 85%;
    grid-template-columns: 100%;
    /* posiciones de las filas */
    grid-template-areas: "titulo" "form";
    cursor: default;
}

.emergenciaBack {
    background-color: #000000;
    color: #ffffff;
    font-size: 15px;
    font-family: 'Astonpoliz', sans-serif;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 7px;
    width: 55px;
    height: 30px;
    justify-self: center;
    align-self: center;
    justify-content: center;
    align-items: center;
    display: flex;
    transition: 0.3s all;
}

.emergenciaBack:hover {
    transition: 0.3s all;
    background-color: #ffffff;
    color: #000000;
    border: 1px solid #000000;
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
    font-family: 'Astonpoliz', sans-serif;
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
