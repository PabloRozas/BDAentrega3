<template>
    <div class="custom-cursor" :style="cursorStyle"></div>
</template>

<script>
export default {
    data() {
        return {
            x: 0,
            y: 0,
        };
    },
    computed: {
        cursorStyle() {
            return {
                left: `${this.x}px`,
                top: `${this.y}px`,
            };
        },
    },
    mounted() {
        window.addEventListener('mousemove', this.updateCursorPosition);
    },
    beforeDestroy() {
        window.removeEventListener('mousemove', this.updateCursorPosition);
    },
    methods: {
        updateCursorPosition(event) {
            this.x = event.clientX;
            this.y = event.clientY;
        },
    },
};
</script>

<style scoped>
.custom-cursor {
    position: fixed;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    pointer-events: none;
    mix-blend-mode: difference;
    background-color: white;
    transform: translate(-50%, -50%);
    z-index: 9999;
}
</style>