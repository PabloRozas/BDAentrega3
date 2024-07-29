<template>
    <header :class="{ 'open': openHeader }" class="menu-container">

        <!-- <img src="../images/x.svg" alt="" @click="toggleMenu"> -->
        <svg width="100%" height="100vh" viewBox="0 0 100 100" preserveAspectRatio="none">
            <path :d="pathMenu" class="svg-path" ref="path" id="path">
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
                    <h1>Pablo Macuada Rozas</h1>
                    <h1>Macarena Soto Herrera</h1>
                    <h1>Nicolas Salinas</h1>
                    <h1>Nicolas Savedra</h1>
                    <h1>Nicolas Muñoz</h1>
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
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

const auth = useAuthStore();

const router = useRouter();

const params = useParameterStore();
const { openHeader, pathMenu, toggleMenuPath } = storeToRefs(params);

const opcionMenuNav = ref(0);

const opcionMenu = (opcion: number) => {
    opcionMenuNav.value = opcion;
};

const logout = () => {
    auth.logout();
    params.toggleHeader();
    router.push('/');
};
</script>