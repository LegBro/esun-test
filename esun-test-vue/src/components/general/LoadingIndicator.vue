<script setup lang="ts"></script>
<template>
  <div class="indicator"></div>
</template>
<style scoped>
@keyframes spin {
  100% {
    transform: rotate(360deg);
  }
}
.indicator {
  width: 36px;
  height: 36px;
  border-radius: 100%;
  border: 4px solid #7fffd480;
  border-top-color: aquamarine;
  animation: spin 1s ease-in-out infinite;
}
</style>
