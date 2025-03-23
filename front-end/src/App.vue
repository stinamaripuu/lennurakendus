<template>
  <NotificationPopup />
  <h1>Lennurakendus</h1>
  <router-view />
</template>

<script setup>
import { ref, provide } from 'vue'
import NotificationPopup from './components/NotificationPopup.vue'

const notificationMessage = ref('')
const notificationVisible = ref(false)

const showNotification = (msg) => {
  notificationMessage.value = msg
  notificationVisible.value = true
  setTimeout(() => {
    notificationVisible.value = false
  }, 3000)
}

provide('notificationMessage', notificationMessage)
provide('notificationVisible', notificationVisible)
provide('showNotification', showNotification)
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
