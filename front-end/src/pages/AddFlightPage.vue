<template>
  <!-- Form for structured insertion of data -->

  <div class="form-container">
    <h1>Lisa uus lend</h1>
    <form @submit.prevent="submitFlight">
      <label>
        Lähtekoht:
        <input v-model="flight.origin" required />
      </label>

      <label>
        Sihtkoht:
        <input v-model="flight.destination" required />
      </label>

      <label>
        Väljumise kuupäev:
        <input type="date" v-model="flight.departureDate" required />
      </label>

      <label>
        Väljumise kellaaeg:
        <input type="time" v-model="flight.departureTime" required />
      </label>

      <label>
        Saabumise kuupäev:
        <input type="date" v-model="flight.arrivalDate" required />
      </label>

      <label>
        Saabumise kellaaeg:
        <input type="time" v-model="flight.arrivalTime" required />
      </label>

      <label>
        Hind:
        <input type="number" step="0.01" v-model="flight.price" required />
      </label>

      <button type="submit">Lisa lend</button>
    </form>

  </div>
</template>

<script setup>
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const showNotification = inject('showNotification')
const router = useRouter()


const flight = ref({
  origin: '',
  destination: '',
  departureDate: '',
  departureTime: '',
  arrivalDate: '',
  arrivalTime: '',
  price: null
})


const submitFlight = async () => {
  try {
    if (flight.value.departureTime.length === 5) {
      flight.value.departureTime += ':00'
    }
    if (flight.value.arrivalTime.length === 5) {
      flight.value.arrivalTime += ':00'
    }
    await axios.post('http://localhost:8080/flights/add', flight.value)
    showNotification('Lend lisatud edukalt!')
    await router.push('/')
  } catch (err) {
    console.error('Lennu lisamine ebaõnnestus', err)
    showNotification('Lennu lisamine ebaõnnestus')

  }
}
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: auto;
  padding: 1rem;
}

label {
  display: block;
  margin: 10px 0;
}

input {
  width: 100%;
  padding: 0.4rem;
  margin-top: 4px;
  box-sizing: border-box;
}

button {
  margin-top: 1rem;
  padding: 0.6rem 1.2rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.success {
  margin-top: 1rem;
  color: green;
  font-weight: bold;
}
</style>
