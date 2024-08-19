<template>
  <div>
    <main>
      <img
        src="../assets/images/mundo.svg"
        alt="mundo"
        class="imgFondoPrincipal"
      />
      <div id="circle"></div>
      <slot />
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      contador: 0,
    };
  },
  mounted() {
    setInterval(() => {
      // posicion random en x y y dentro de los limites de la pantalla
      const x = Math.random() * window.innerWidth - 50;
      const y = Math.random() * window.innerHeight - 50;
      // color random entre las opciones de rojo y naranja
      const color = Math.random() > 0.5 ? "red" : "orange";
      // crear el circle
      this.createCircles(x, y, color);

      // Si el contador es mayor a 100, limpiar el intervalo
      if (this.contador > 100) {
        clearInterval();
      }
    }, 500);
  },
  methods: {
    createCircles(x, y, color) {
      // Aumentar el contador
      this.contador++;

      // Namespace para elementos SVG
      const svgNS = "http://www.w3.org/2000/svg"; // Namespace for SVG elements

      // Create SVG container
      const svg = document.createElementNS(svgNS, "svg");
      svg.setAttribute("width", "50px");
      svg.setAttribute("height", "50px");
      svg.setAttribute("viewBox", "0 0 100 100");
      svg.style.position = "absolute";
      svg.style.left = x + "px";
      svg.style.top = y + "px";
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
      circle.classList.add("animate-circle");

      // Append circle to SVG container
      svg.appendChild(circle);

      // Append SVG to the container
      document.getElementById("circle").appendChild(svg);

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
  },
};
</script>

<style>
#circle svg {
  z-index: -1;
  filter: blur(0.2px);
  transition: all 3s;
}

.animate-circle {
  transition: r 2.2s ease-in-out, stroke-width 2.2s ease-in-out;
}
</style>
