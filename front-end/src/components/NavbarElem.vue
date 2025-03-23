<template>
  <!-- Added to make navigation in app much easier -->

  <nav class="navbar">
    <router-link to="/" class="nav-button">Algusesse</router-link>
  </nav>
</template>

<style scoped>
.navbar {
  padding: 1rem;
  text-align: left;
}

.nav-button {
  background-color: #4caf50;
  color: white;
  padding: 0.6rem 1.2rem;
  text-decoration: none;
  border-radius: 6px;
  font-weight: bold;
  transition: background-color 0.2s;
}

.nav-button:hover {
  background-color: #45a049;
}
</style>
