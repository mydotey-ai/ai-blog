<template>
  <div class="fixed inset-0 pointer-events-none overflow-hidden z-0">
    <div
      class="absolute rounded-full bg-gradient-to-br from-klein-blue/10 to-klein-blue/5 animate-float-slow"
      style="width: 400px; height: 400px; top: 10%; left: 5%; animation-delay: 0s;"
    ></div>
    <div
      class="absolute rounded-full bg-gradient-to-br from-purple-500/10 to-pink-500/5 animate-float-medium"
      style="width: 300px; height: 300px; top: 60%; right: 10%; animation-delay: 2s;"
    ></div>
    <div
      class="absolute bg-gradient-to-br from-blue-400/10 to-cyan-400/5 animate-float-fast"
      style="width: 200px; height: 200px; top: 40%; left: 50%; transform: rotate(45deg); animation-delay: 1s;"
    ></div>
    <div
      class="absolute rounded-full bg-gradient-to-br from-indigo-500/10 to-blue-500/5 animate-float-slow"
      style="width: 250px; height: 250px; bottom: 10%; left: 30%; animation-delay: 3s;"
    ></div>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
@keyframes float-slow {
  0%, 100% {
    transform: translateY(0px) translateX(0px);
  }
  50% {
    transform: translateY(-30px) translateX(20px);
  }
}

@keyframes float-medium {
  0%, 100% {
    transform: translateY(0px) translateX(0px);
  }
  50% {
    transform: translateY(-40px) translateX(-25px);
  }
}

@keyframes float-fast {
  0%, 100% {
    transform: translateY(0px) translateX(0px) rotate(45deg);
  }
  50% {
    transform: translateY(-50px) translateX(15px) rotate(45deg);
  }
}

.animate-float-slow {
  animation: float-slow 20s ease-in-out infinite;
}

.animate-float-medium {
  animation: float-medium 15s ease-in-out infinite;
}

.animate-float-fast {
  animation: float-fast 10s ease-in-out infinite;
}
</style>
