<template>
  <div class="home">
    <h1>Lennurakendus oma järgmise lennu planeerimiseks!️</h1>
    <p>Alusta oma järgmist reisi juba täna! Vali, broneeri, lenda!</p>

    <router-link to="/booking">
      <button class="start-btn">Leia endale sobiv lend</button>
    </router-link>

    <p>Kui soovid lisada uusi lennuaegu, vajuta siia:</p>
    <router-link to="/add-flight">
      <button class="start-btn">Lisa lennuaegu</button>
    </router-link>
  </div>
</template>

<script setup>

</script>

