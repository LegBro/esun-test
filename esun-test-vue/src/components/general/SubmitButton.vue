<script setup lang="ts">
defineProps<{ type: 'submit' | 'button' }>()
defineEmits<{ (event: 'click'): void }>()
</script>
<template>
  <button :type="type" @click="$emit('click')">
    <slot></slot>
  </button>
</template>
<style scoped lang="scss">
button {
  width: fit-content;
  padding: 4px 12px;
  border-radius: 2px;
  background: rgb(70, 70, 255);
  color: white;
  &:hover {
    cursor: pointer;
  }
}
</style>
