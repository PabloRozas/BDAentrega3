<template>
  <section class="sectrion-lismodel">
    <div class="container-lismodels">
      <h1 class="title-component">{{ title }}</h1>
      <ul>
        <li v-for="model in models" :key="model.id">
          <!-- agregar un punto a cada uno -->
          <h3>
            •
            {{ model.nombre }}
          </h3>
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
export default {
  name: "ListModels",
  props: {
    models: Array,
    title: String,
  },
};
</script>

<style scoped>
.title-component {
  font-size: 2rem;
}

.sectrion-lismodel {
  color: black;
  display: flex;
  flex-direction: column;
  padding: 1rem;
  border-radius: 0.5rem;
}

.container-lismodels {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  border-radius: 0.5rem;
  padding: 1rem;
  border: 1px solid black;
  max-height: 86vh;
  /* fondo difuminado */
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(4px);
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  overflow-y: auto;
}

li {
  padding: 0.5rem;
  border-radius: 0.5rem;
  cursor: pointer;
}

li:hover {
  background-color: #f0f0f0;
}
</style>
