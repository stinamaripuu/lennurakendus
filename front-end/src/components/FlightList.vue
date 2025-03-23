<template>
  <div class="flight-table-container">
    <h1>Saadavalolevad lennud</h1>

    <p>Vali endale meelepärane lennuaeg: </p>
    <div class="filters">
      <label>
        Lähtekoht:
        <select v-model="filters.origin">
          <option value="">Kõik</option>
          <option v-for="o in origins" :key="o" :value="o">{{ o }}</option>
        </select>
      </label>

      <label>
        Sihtkoht:
        <select v-model="filters.destination">
          <option value="">Kõik</option>
          <option v-for="d in destinations" :key="d" :value="d">{{ d }}</option>
        </select>
      </label>

      <label>
        Väljumise kuupäev:
        <input type="date" v-model="filters.departureDate" />
      </label>

      <label>
        Väljumise aeg:
        <input type="time" v-model="filters.departureTime" />
      </label>

      <label>
        Saabumise kuupäev:
        <input type="date" v-model="filters.arrivalDate" />
      </label>

      <label>
        Saabumise aeg:
        <input type="time" v-model="filters.arrivalTime" />
      </label>

      <label>
        Max hind (€):
        <input type="number" v-model.number="filters.maxPrice" />
      </label>
    </div>

    <table>
      <thead>
      <tr>
        <th>Lähtekoht</th>
        <th>Sihtkoht</th>
        <th>Väljub</th>
        <th>Saabub</th>
        <th>Hind (€)</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="flight in filteredFlights" :key="flight.id">
        <td>{{ flight.origin }}</td>
        <td>{{ flight.destination }}</td>
        <td>{{ flight.departureDate }} {{ flight.departureTime }}</td>
        <td>{{ flight.arrivalDate }} {{ flight.arrivalTime }}</td>
        <td>{{ flight.price }}</td>
        <td><button @click="$emit('select-flight', flight)">Vali</button></td>
      </tr>
      </tbody>
    </table>

    <p>Vali omale sobiv lend ning näed allpool istmete plaani.</p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'

const flights = ref([])
const filters = ref({
  origin: '',
  destination: '',
  departureDate: '',
  departureTime: '',
  arrivalDate: '',
  arrivalTime: '',
  maxPrice: null
})

onMounted(async () => {
  const res = await axios.get('http://localhost:8080/flights/all')
  flights.value = res.data
})

const origins = computed(() => [...new Set(flights.value.map(f => f.origin))])
const destinations = computed(() => [...new Set(flights.value.map(f => f.destination))])

const filteredFlights = computed(() => {
  return flights.value.filter(flight => {
    return (
        (!filters.value.origin || flight.origin === filters.value.origin) &&
        (!filters.value.destination || flight.destination === filters.value.destination) &&
        (!filters.value.departureDate || flight.departureDate === filters.value.departureDate) &&
        (!filters.value.departureTime || flight.departureTime.startsWith(filters.value.departureTime)) &&
        (!filters.value.arrivalDate || flight.arrivalDate === filters.value.arrivalDate) &&
        (!filters.value.arrivalTime || flight.arrivalTime.startsWith(filters.value.arrivalTime)) &&
        (!filters.value.maxPrice || parseFloat(flight.price) <= filters.value.maxPrice)
    )
  })
})
</script>

<style scoped>
.flight-table-container {
  max-width: 1000px;
  margin: auto;
  padding: 2rem;
}

.filters {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

th, td {
  padding: 0.75rem;
  border: 1px solid #ccc;
  text-align: left;
}

thead {
  background-color: #f4f4f4;
}

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
