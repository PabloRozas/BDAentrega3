<template>
  <div class="containerIndex">
    <h1 class="titleRESQ">RES-Q</h1>
    <div class="containerText" @mousemove="updateBlurCiclePosition">
      <div class="containerTextP">
        <p>{{ texto }}</p>
      </div>
    </div>

    <div class="containerButton">
      <div class="login" @click="$router.push(`/login`)">Iniciar Sesión</div>

      <div class="logup" @click="$router.push(`/register`)">Registrarse</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      texto:
        "¡Únete a la fuerza del voluntariado en emergencias! Nuestra plataforma te conecta con oportunidades para marcar la diferencia cuando más se necesita. Coordinar esfuerzos, asignar tareas y mantener el progreso bajo control: todo en un solo lugar. ¡Haz tu parte y ayuda a construir un mundo más resiliente hoy mismo!",
    };
  },
};
</script>
