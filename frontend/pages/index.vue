<template>

  <main>
    <!-- <div id="circle">
      <div class="circle"></div>
    </div> -->
    <!-- svg decirculo -->
    <!-- <svg width="50px" height="50px" viewBox="0 0 100 100">
      <circle cx="50" cy="50" r="10" fill="none" stroke="red" stroke-width="4" />
    </svg> -->

    <div id="circle"></div>


    <img src="../images/mundo.svg" alt="mundo" class="imgFondo">
    <div class="containerIndex">
      <!-- <img src="../images/RESQ.svg" alt="logo" class="imgLogo"> -->
      <h1 class="titleRESQ">RES-Q</h1>
      <div class="containerText" @mousemove="updateBlurCiclePosition">
        <div class="containerTextP">
          <p>{{ texto }}</p>
        </div>
      </div>

      <div class="containerButton">
        <div class="login" @click="$router.push(`/login`);">Iniciar Sesión
        </div>

        <div class="logup" @click="$router.push(`/register`);">Registrarse
        </div>
      </div>

    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      texto: "¡Únete a la fuerza del voluntariado en emergencias! Nuestra plataforma te conecta con oportunidades para marcar la diferencia cuando más se necesita. Coordinar esfuerzos, asignar tareas y mantener el progreso bajo control: todo en un solo lugar. ¡Haz tu parte y ayuda a construir un mundo más resiliente hoy mismo!"
    };
  },
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
    async login() {
      try {
        const res = await this.$axios.post('http://localhost:3000/api/auth/login', {
          email: this.email,
          password: this.password
        });
        console.log(res.data);
        this.$router.push('/dashboard');
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style>
body {
  background-color: black;
  color: white;
  margin: 0;

}

.imgFondo {
  width: 100%;
  height: 100vh;
  position: absolute;
  z-index: -10;
  filter: brightness(0.4);
  object-fit: cover;

}

.imgLogo {
  width: 40%;
  height: 15%;
  z-index: 1;
  filter: invert(1);

}

.containerIndex {
  display: flex;
  position: relative;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  font-family: 'Astonpoliz', sans-serif;
}

.containerText {
  width: 70%;
  display: flex;
  text-align: center;
  margin-top: 20px;
  border-bottom: 1px solid white;
  border-radius: 10px;
}

.containerText::after {
  content: "";
  position: absolute;
  width: 70%;
  height: 12vh;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: -1;
  filter: blur(6px);
}

#circle svg {
  z-index: -1;
  filter: blur(0.2px);
  transition: all 3s;
}

.animate-circle {
  transition: r 2.2s ease-in-out, stroke-width 2.2s ease-in-out;
}

.containerButton {
  /* grid de una fila con dos columnas */
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  margin-top: 20px;
  width: 70%;
  justify-content: center;
  align-items: center;
  text-align: center;
}

/* login en la columna 1 */
.login {
  transition: all 0.5s;
  border: 1px solid white;
  padding: 10px;
  border-radius: 10px;
  text-align: center;
  cursor: pointer;
  order: 1;
}

.login:hover {
  background-color: #9AEBA3;
  border: 1px solid #9AEBA3;
  color: black;
}

/* logup en la columna 2 */
.logup {
  transition: all 0.5s;
  border: 1px solid white;
  padding: 10px;
  border-radius: 10px;
  text-align: center;
  cursor: pointer;
  order: 2;
}

.logup:hover {
  background-color: #9AEBA3;
  border: 1px solid #9AEBA3;
  color: black;
}

.titleRESQ {
  font-size: 6rem;
  margin: 0;
  font-family: 'Lemon Milk', sans-serif;
}
</style>
