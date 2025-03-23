<template>
  <!-- Welcom page for either flight selection or admin action like adding a flight for testing purposes -->

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

<style setup>
button {
  padding: 0.4rem 1rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #43a047;
}
</style>
