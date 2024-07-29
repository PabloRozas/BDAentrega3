<template>
    <div>
        <Header />
        <Navbar />
        <main>
            <img src="../assets/images/mundo.svg" alt="mundo" class="imgFondoPrincipal">
            <slot />
        </main>

    </div>
</template>


<style scoped>
main {
    width: 100%;
    height: calc(100vh - 70px);
}

.imgFondoPrincipal {
    width: 100%;
    height: calc(100vh - 70px);
    position: absolute;
    z-index: -10;
    filter: brightness(0.2) invert(1);
    object-fit: cover;
}
</style>