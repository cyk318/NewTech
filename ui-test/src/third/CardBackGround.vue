<template>
  <div class="card-background"></div>
</template>

<script setup>

</script>

<style lang="scss" scoped>

.card-background {
  width: 200px;
  height: 200px;
  background-color: transparent;
  box-shadow: inset 10px 10px 10px 10px rgba(0, 0, 0, 0.158),
  10px 10px 20px rgba(0, 0, 0, 0.466),
  inset -10px -10px 10px 10px rgba(255, 255, 255, 0.897);
  border-radius: 51% 49% 62% 38% / 25% 57% 43% 75%;
  position: relative;
}

.card-background::after {
  content: "";
  display: inline-block;
  width: 10px;
  height: 10px;
  position: absolute;
  background-color: rgba(255, 255, 255, 0.842);
  border-radius: 80% 20% 91% 9% / 48% 67% 33% 52%;
  top: 40px;
  left: 40px;
}

.card-background::before {
  content: "";
  display: inline-block;
  width: 20px;
  height: 20px;
  position: absolute;
  background-color: rgba(255, 255, 255, 0.842);
  border-radius: 48% 52% 45% 55% / 48% 67% 33% 52%;
  top: 35px;
  left: 60px;
}
</style>