<script setup lang="ts">
defineProps<{ title: string; color: string }>()
</script>
<template>
  <div class="state-hint">
    <div class="color-block" :style="{ backgroundColor: color }"></div>
    <span>{{ title }}</span>
  </div>
</template>
<style scoped lang="scss">
.state-hint {
  display: flex;
  align-items: center;
  gap: 8px;
  .color-block {
    border-radius: 4px;
    width: 48px;
    height: 100%;
    min-height: 24px;
  }
}
</style>
