<template>
  <nav class="nav-back">
    <svg
      viewBox="0 0 100 100"
      xmlns="http://www.w3.org/2000/svg"
      id="toggle-menu"
      style="cursor: pointer"
      @click="toggleMenu"
    >
      <path fill="" :d="toggleMenuPath" ref="iconMenu" id="iconMenu" />
    </svg>
    <h1>RES-Q</h1>
    <h1
      style="cursor: pointer; margin-left: 30px; font-size: 25px; z-index: 100"
      @click="router.push('/' + route.path.split('/')[1])"
    >
      Inicio
    </h1>
    <div class="containerTitleNav">
      <h3 v-if="role === 'COORDINADOR'">Vista Coordinador</h3>
      <h3 v-else-if="role === 'VOLUNTARIO'">Vista Voluntario</h3>
    </div>
  </nav>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { defineComponent, ref } from "vue";
import anime from "animejs";

const params = useParameterStore();
const { toggleMenuPath, openHeader, pathMenu } = storeToRefs(params);

const auth = useAuthStore();
const router = useRouter();
const route = useRoute();
const { role } = storeToRefs(auth);

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
