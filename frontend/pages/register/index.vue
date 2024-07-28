<template>
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