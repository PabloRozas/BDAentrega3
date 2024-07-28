<template>
    <div>
        <main>
            <img src="../assets/images/mundo.svg" alt="mundo" class="imgFondoPrincipal">
            <div id="circle"></div>
            <slot />
        </main>

    </div>
</template>

<script></script>

<style scoped>
.imgFondoPrincipal {
    width: 100%;
    height: 100vh;
    position: absolute;
    z-index: -10;
    filter: brightness(0.2) invert(1);
    object-fit: cover;
}
</style>