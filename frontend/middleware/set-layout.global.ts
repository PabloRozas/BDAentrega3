export default defineNuxtRouteMiddleware((to, from) => {
    if (to.path.startsWith('/admin') || to.path.startsWith('/voluntario')) {
      to.meta.layout = 'entry-layout'
    } else {
      to.meta.layout = 'default'
    }
  })
  