<script setup lang="ts">
defineProps<{ anchor?: string }>()
</script>
<template>
  <a :href="anchor" class="container">
    <p>1樓：座位2</p>
    <p>所屬員工編號：</p>
  </a>
</template>
<style scoped lang="scss">
.container {
  padding: 1rem 2rem;
  border-radius: 8px;
  box-shadow: 2px 2px 2px 2px rgb(0, 0, 0, 0.5);
  transition-property: background-color;
  transition-duration: 150ms;
  &:hover {
    cursor: pointer;
    background-color: aquamarine;
  }
  p {
    font-weight: bold;
  }
}
</style>
