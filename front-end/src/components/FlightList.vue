<template>
  <div>
    <h1>Saadavalolevad lennud</h1>
    <p>Lähtekoht (kuupäev, kellaaeg) -> Sihtkoht (kuupäev, kellaaeg), hind </p>
    <ul>
      <li v-for="flight in flights" :key="flight.id">
        {{flight.origin}} ({{ flight.departureDate }} - {{flight.departureTime}}) -> {{flight.destination}} ({{ flight.arrivalDate }} - {{flight.arrivalTime}}) : {{ flight.price }}
        <button @click="$emit('select-flight', flight)">Select</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import axios from "axios";

const flights = ref([])

onMounted(async () => {
  const res = await axios.get('http://localhost:8080/flights/all')
  flights.value = res.data
})
</script>

