<template>
  <header :class="{ open: openHeader }" class="menu-container">
    <!-- <img src="../images/x.svg" alt="" @click="toggleMenu"> -->
    <svg
      width="100%"
      height="100vh"
      viewBox="0 0 100 100"
      preserveAspectRatio="none"
    >
      <path :d="pathMenu" class="svg-path" ref="path" id="path"></path>
    </svg>
    <section class="menuHeader">
      <section class="menu1-container">
        <h1 @click="opcionMenu(0)" :class="{ active: opcionMenuNav === 0 }">
          RES-Q
        </h1>
        <h1 @click="opcionMenu(1)" :class="{ active: opcionMenuNav === 1 }">
          Voluntarios
        </h1>
        <h1 @click="opcionMenu(2)" :class="{ active: opcionMenuNav === 2 }">
          Coordinadores
        </h1>
        <h1 @click="opcionMenu(3)" :class="{ active: opcionMenuNav === 3 }">
          Emergencias
        </h1>
        <h1 @click="opcionMenu(4)" :class="{ active: opcionMenuNav === 4 }">
          Instituciones
        </h1>
      </section>
      <section v-if="opcionMenuNav === 0" class="menu2-container mainMenu">
        <div>
          <h1>Perfil</h1>
          <h1 @click="logout">Cerrar Sesión</h1>
        </div>
        <div>
          <h1>Pablo Macuada Rozas</h1>
          <h1>Nicolás Muñoz Reyes</h1>
          <h1>Nicolás Saavedra Charlin</h1>
          <h1>Nicolás Salinas Rodrigues</h1>
          <h1>Macarena Soto Herrera</h1>
        </div>
      </section>
      <section v-if="opcionMenuNav === 1" class="menu2-container">
        <h1
          @click="
            router.push('/admin/voluntarios');
            toggleMenu();
          "
        >
          Lista de Voluntarios
        </h1>
        <h1>Crear Voluntario</h1>
        <h1>Eliminar Voluntario</h1>
      </section>
      <section v-if="opcionMenuNav === 2" class="menu2-container">
        <h1>Lista de Coordinadores</h1>
        <h1>Crear Coordinador</h1>
        <h1>Eliminar Coordinador</h1>
      </section>
      <section v-if="opcionMenuNav === 3" class="menu2-container">
        <h1
          @click="
            router.push('/admin/emergencia');
            toggleMenu();
          "
        >
          Lista de Emergencias
        </h1>
        <h1
          @click="
            router.push('/admin/emergencia/create');
            toggleMenu();
          "
        >
          Crear Emergencia
        </h1>
        <h1>Eliminar Emergencia</h1>
      </section>
      <section v-if="opcionMenuNav === 4" class="menu2-container">
        <h1
          @click="
            router.push('/admin/instituciones');
            toggleMenu();
          "
        >
          Lista de Instituciones
        </h1>
        <h1
          @click="
            router.push('/admin/instituciones/create');
            toggleMenu();
          "
        >
          Crear Institución
        </h1>
        <h1>Eliminar Institución</h1>
      </section>
    </section>
  </header>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import anime from "animejs";

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
  router.push("/");
};

const toggleMenu = () => {
  params.toggleHeader();
  const pathToogleMenu = openHeader.value
    ? "M30 30L70 70zM13 50C15 100 85 100 87 50C85 0 15 0 13 50zM30 70L70 30z"
    : "M10 25L90 25zM10 50C50 50 50 50 90 50C50 50 50 50 10 50zM10 75L90 75z";

  const pathMenuHeader = openHeader.value
    ? "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 100 0 100 Z"
    : "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 70 0 100  Z";

  anime({
    targets: "#iconMenu",
    d: [{ value: pathToogleMenu }],
    duration: 1000,
    easing: "easeInOutQuad",
  });

  anime({
    targets: "#path",
    d: [{ value: pathMenuHeader }],
    duration: 900,
    easing: "easeInOutQuad",
  });
};
</script>

<style scoped>
h1 {
  cursor: pointer;
}
</style>
