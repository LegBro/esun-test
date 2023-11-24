<script setup lang="ts"></script>
<template>
  <div class="outer">
    <header>
      <h1>ESUN TEST - 員工座位系統</h1>
    </header>
    <slot></slot>
  </div>
</template>
<style scoped lang="scss">
.outer {
  min-height: 100svh;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: stretch;
}
header {
  display: flex;
  background: linear-gradient(to left, white, aquamarine);
  h1 {
    padding: 1rem;
    font-size: x-large;
    font-weight: bold;
  }
}
</style>
