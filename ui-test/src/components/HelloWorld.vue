<script setup>
import Card from "../third/Card.vue";
import {ref} from "vue";
import TabRadio from "../third/TabRadio.vue";
import Load1 from "../third/Load1.vue";

//Card props
let title = ref("天气不错")
let content = ref("今天我要好好学习~")
let date = ref("2024.07.12")

//TabRadio props
let tabs = ref(["兴趣驱动", "测试开发", "开发"])

</script>

<template>

  <div class="tab">
    <TabRadio :tabs="tabs" ></TabRadio>
  </div>

  <div class="container-main">

    <div class="container-left">
      <div class="card">
        <Card :title="title" :content="content" :date="date" ></Card>
        <Card :title="title" :content="content" :date="date" ></Card>
      </div>
    </div>

    <div class="container-right">
      <Load1></Load1>
    </div>
  </div>

</template>

<style scoped>

.tab {
  max-width: 800px;
  height: 150px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container-main {
  width: calc(100% - 200px);
  height: calc(100% - 100px);
  margin: 0 auto;
  display: flex;
  justify-content: center;
}


.container-left {
  width: 800px;
  margin-right: 15px;
  .card {
    max-width: 800px;
    margin: 20px auto;
    height: 150px;
  }
}

.container-right {
  width: 400px;
}

</style>
