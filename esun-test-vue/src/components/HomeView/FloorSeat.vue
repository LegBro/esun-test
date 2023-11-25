<script setup lang="ts">
import type Seat from '@/types/Seat'

defineProps<{ anchor?: string; seat: Seat; selectedSeatId?: string }>()
defineEmits<{ (event: 'click'): void }>()
</script>
<template>
  <a
    :href="anchor"
    class="container"
    :class="{
      'bg-normal': seat.seatBy === null,
      'bg-selected': selectedSeatId && seat.id === selectedSeatId && seat.seatBy === null,
      'bg-disabled': seat.seatBy !== null
    }"
    @click="$emit('click')"
  >
    <p>{{ seat.floorNumber }}樓：座位{{ seat.seatNumber }}</p>
    <p>
      所屬員工編號：<span>{{ seat.seatBy ? `[${seat.seatBy.id}]${seat.seatBy.name}` : '無' }}</span>
    </p>
  </a>
</template>
<style scoped lang="scss">
.bg-selected {
  background-color: #7fffd4;
}
.bg-disabled {
  background-color: #ff4d4d;
}
.bg-normal {
  background-color: #f1f1f1;
  &:hover {
    background-color: aquamarine;
  }
}

.container {
  padding: 1rem 2rem;
  border-radius: 8px;
  box-shadow: 2px 2px 2px 2px rgb(0, 0, 0, 0.5);
  transition-property: background-color;
  transition-duration: 150ms;
  &:hover {
    cursor: pointer;
  }
  p {
    font-weight: bold;
  }
}
</style>
