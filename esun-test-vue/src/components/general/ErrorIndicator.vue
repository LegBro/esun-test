<script setup lang="ts">
import ErrorIcon from '@/components/icons/ErrorIcon.vue'

defineProps<{ errorMessage?: string }>()
</script>
<template>
  <div class="wrapper">
    <ErrorIcon class="icon" :size="40"></ErrorIcon>
    <p>
      {{ errorMessage ?? '發生錯誤' }}
    </p>
  </div>
</template>
<style scoped>
.wrapper {
  width: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
}
.icon {
  font-size: 16px;
}
p {
  color: red;
  display: inline-block;
  max-width: 200px;
  text-align: center;
}
</style>
