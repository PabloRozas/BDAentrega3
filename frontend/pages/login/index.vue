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

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { defineComponent, ref } from 'vue';
import { RoleTypes } from "../../models/auth/role_types.model";
import { useRouter } from 'vue-router';
import axios from 'axios';

const auth = useAuthStore();

const router = useRouter();

const email = ref('');
const password = ref('');
const error = ref('');

const logearUser = (event: Event) => {
    event.preventDefault();
    axios.post('http://localhost:8080/gateway/login', {
        correo: email.value,
        contrasena: password.value
    })
        .then((response) => {
            console.log(response);
            if (response.data.token) {
                if (response.data.role === RoleTypes.ADMIN) {
                    auth.login(response.data.token, RoleTypes.ADMIN);
                    router.push('/admin');
                } else if (response.data.role === RoleTypes.VOLUNTARIO) {
                    auth.login(response.data.token, RoleTypes.VOLUNTARIO);
                    router.push('/voluntario');
                }
            }
        })
        .catch((error) => {
            error.value = 'Usuario o contraseña incorrectos';
            console.log(error);
        });
};
</script>