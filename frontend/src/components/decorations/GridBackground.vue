<template>
  <div class="fixed inset-0 pointer-events-none z-0" :class="{ 'has-gradient': withGradient }">
    <div class="grid-pattern"></div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  withGradient?: boolean
}>()
</script>

<style scoped>
.grid-pattern {
  width: 100%;
  height: 100%;
  background-image:
    linear-gradient(to right, rgba(0, 34, 255, 0.05) 1px, transparent 1px),
    linear-gradient(to bottom, rgba(0, 34, 255, 0.05) 1px, transparent 1px);
  background-size: 50px 50px;
  opacity: 0.6;
}

.has-gradient {
  background: radial-gradient(
    ellipse at top,
    rgba(0, 34, 255, 0.05) 0%,
    transparent 50%
  );
}

.has-gradient .grid-pattern {
  opacity: 0.4;
}
</style>
