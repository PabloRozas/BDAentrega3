import Vue from 'vue';
import CustomCursor from '../components/Cursor.vue';

Vue.component('CustomCursor', CustomCursor);

if (process.client) {
  window.addEventListener('mousemove', (event) => {
    const cursor = document.querySelector('.custom-cursor');
    if (cursor) {
      cursor.style.left = `${event.clientX}px`;
      cursor.style.top = `${event.clientY}px`;
    }
  });
}
