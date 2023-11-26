<script setup lang="ts">
defineProps<{ size: number }>()
</script>
<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="size" :height="size" viewBox="0 0 100 100">
    <!-- 圓圈 -->
    <circle cx="50" cy="50" r="40" stroke="red" stroke-width="6" fill="transparent" />

    <!-- 加粗的驚嘆號 -->
    <line x1="50" y1="35" x2="50" y2="65" stroke="red" :stroke-width="size / 5" />
    <line x1="50" y1="70" x2="50" y2="75" stroke="red" :stroke-width="size / 5" />
  </svg>
</template>
