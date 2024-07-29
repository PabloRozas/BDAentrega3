// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  // modules: [ '@pinia/nuxt'],
  css: ["@/assets/css/main.css", "@/assets/scss/styles.scss"],
  vite: {
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: '@import "@/assets/scss/variables.scss";',
        },
      },
    },
  },
  plugins: ["~/plugins/cursor.js"],
  app: {
    head: {
      title: "RES-Q",
      meta: [{ charset: "utf-8" }],
      link: [
        {
          rel: "icon",
          type: "image/svg+xml",
          href: "/icon.svg",
        },
      ],
    },
  },
  imports: {
    dirs: ["stores"],
  },
  modules: ["@pinia/nuxt"],
});
