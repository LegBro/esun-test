<script setup lang="ts">
import { ref } from 'vue'

const { showModal } = defineProps<{ showModal: boolean }>()
defineEmits<{ (event: 'close'): void }>()

const lazyShowModal = ref(showModal)
</script>
<template>
  <div v-if="showModal" class="screen" @click.stop="lazyShowModal = false">
    <Transition appear @after-leave="$emit('close')">
      <div v-if="lazyShowModal" class="modal" @click.stop="() => ''">
        <slot></slot>
      </div>
    </Transition>
  </div>
</template>
<style scoped lang="scss">
.v-enter-active,
.v-leave-active {
  transition: transform 0.5s ease-in-out;
}
.v-enter-from,
.v-leave-to {
  transform: translateY(100vh);
}
.screen {
  position: fixed;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  z-index: 100;
  background: rgb(0 0 0 / 0.3);
}
.modal {
  width: fit-content;
  max-width: 85%;
  min-width: 300px;
  max-height: 85%;
  min-height: 150px;
  padding: 16px;
  background: white;
  border-radius: var(--border-radius-md);
  box-shadow: 2px 2px 2px 2px rgb(0 0 0 / 0.3);
  overflow-y: auto;
}
</style>
