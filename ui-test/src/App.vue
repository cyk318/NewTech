<script setup>
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <HelloWorld msg="Vite + Vue" />
</template>

<style>
* {
  /* 防止边框将元素撑大 */
  box-sizing: border-box;
  /* 去除浏览器默认样式 */
  padding: 0;
  margin: 0;
}


html {
  height: 100%;
}

body {
  height: 100%;
  background-color: rgb(245, 245, 245);
}

</style>
