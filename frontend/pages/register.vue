<template>
    <main>
        <div id="circle"></div>

        <img src="../images/mundo.svg" alt="mundo">
        <div class="containerLogin">
            <h1>Registro</h1>
            <form @submit.prevent="login" class="formLogin">
                <input type="text" v-model="nombre" placeholder="Nombre">
                <input type="text" v-model="numDocumento" placeholder="Número de documento">
                <input type="text" v-model="email" placeholder="Email">
                <input type="text" v-model="address" ref="autocompleteInput" placeholder="Zona de vivienda" />
                <input type="password" v-model="password" placeholder="Contraseña">
                <input type="password" v-model="password" placeholder="Repita contraseña">
                <button @click="registrarUsuario">Registrarse</button>

            </form>
        </div>
    </main>

</template>

<script>
import axios from 'axios';
import { Loader } from '@googlemaps/js-api-loader';

export default {
    data() {
        return {
            nombre: '',
            zonaVivienda: '',
            numDocumento: '',
            email: '',
            password: '',
            equipamientoVoluntario: '',
            address: '',
            latitude: '',
            longitude: ''
        };
    },
    // metodo para que cada 5 segundos el circle cambie de posicion sin salirse de la pantalla
    mounted() {
        setInterval(() => {
            // posicion random en x y y dentro de los limites de la pantalla
            const x = Math.random() * window.innerWidth - 50;
            const y = Math.random() * window.innerHeight - 50;
            // color random entre las opciones de rojo y naranja
            const color = Math.random() > 0.5 ? 'red' : 'orange';
            // crear el circle
            this.createCircles(x, y, color);
        }, 500);
        const loader = new Loader({
            apiKey: 'AIzaSyAX0wJhvShmIIHLgczl44u5Mm_zl9IfboY', // Reemplaza con tu clave
            version: 'weekly',
            libraries: ['places'],
        });

        loader.load().then(() => {
            const input = this.$refs.autocompleteInput;
            const autocomplete = new google.maps.places.Autocomplete(input);

            autocomplete.addListener('place_changed', () => {
                const place = autocomplete.getPlace();
                if (place.geometry) {
                    this.address = place.formatted_address;
                    this.latitude = place.geometry.location.lat();
                    this.longitude = place.geometry.location.lng();
                }
            });
        });
    },
    methods: {
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
        registrarUsuario(event) {
            event.preventDefault();
            axios.post('http://localhost:8080/voluntario/register', {
                nombre: this.nombre,
                contrasena: this.password,
                correo: this.email,
                numeroDocumento: this.numDocumento,
                equipamientoVoluntario: this.equipamientoVoluntario,
                latitud: this.latitude,
                longitud: this.longitude,
                equipamiento: "Ninguno"
            }).then(response => {
                console.log(response);
                this.$router.push('/login');
            }).catch(error => {
                console.log(error);
            });
        }
    }

};

</script>

<style scoped>
body {
    background-color: black;
    color: white;
    margin: 0;

}

img {
    width: 100%;
    height: 100vh;
    position: absolute;
    z-index: -10;
    filter: brightness(0.4);
    object-fit: cover;

}

.containerLogin {
    /* background-color: white; */
    width: 100%;
    height: 90vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-family: 'Astonpoliz', sans-serif;
}

.formLogin {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 10px;
}

form input {
    width: 20vh;
    height: 10px;
    border: none;
    border-bottom: 1px solid white;
    background-color: black;
    color: white;
    border-radius: 0px;
    padding: 10px;
    margin-top: 5px;
    margin-bottom: 5px;
}

form input:focus {
    outline: none;
    border-bottom: 1px solid #347355;
}

::placeholder {
    color: white;
}

form button {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 20vh;
    height: 30px;
    border: 1px solid #9AEBA3;
    border-radius: 5px;
    padding: 10px;
    background-color: black;
    cursor: pointer;
    font-family: 'Astonpoliz', sans-serif;
    transition: all 0.3s;
    color: #9AEBA3;
    margin-top: 15px;
}

form button:hover {
    background-color: #9AEBA3;
    border: 1px solid #9AEBA3;
    color: black;
}

.register {
    color: #0281F6
}

#circle svg {
    z-index: -1;
    filter: blur(0.2px);
    transition: all 3s;
}

.animate-circle {
    transition: r 2.2s ease-in-out, stroke-width 2.2s ease-in-out;
}
</style>