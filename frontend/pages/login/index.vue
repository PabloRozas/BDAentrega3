<template>
    <div class="containerLogin">
        <h1>Iniciar Sesión</h1>
        <form class="formLogin">
            <input type="text" v-model="email" placeholder="Email">
            <input type="password" v-model="password" placeholder="Contraseña">

            <button @click="logearUser">Ingresar</button>
            <div class="register" @click="$router.push(`/register`);">
                <a>Registrarse</a>
            </div>

        </form>
        <p>{{ error }}</p>
    </div>

</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            email: '',
            password: '',
            error: ''
        };
    },
    methods: {
        logearUser(event) {
            event.preventDefault();
            axios.post('http://localhost:8080/coordinador/login', {
                correo: this.email,
                contrasena: this.password
            })
                .then((response) => {
                    console.log(response);
                    if (response.data.token) {
                        localStorage.setItem('token', response.data.token);
                        localStorage.setItem('id', response.data.id);
                        localStorage.setItem('nombre', response.data.nombre);
                        if (response.data.role === 'COORDINADOR') {
                            this.$router.push('/admin');
                        } else if (response.data.role === 'VOLUNTARIO') {
                            this.$router.push('/voluntario');
                        }
                    }
                })
                .catch((error) => {
                    this.error = 'Usuario o contraseña incorrectos';
                    console.log(error);
                });
        }
    }

};
</script>
