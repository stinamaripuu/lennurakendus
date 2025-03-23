<template>
  <div>
    <FlightList @select-flight="selectFlight" />
    <SeatMap v-if="selectedFlight"
             :flightId="selectedFlight.id"
             :flightPrice="selectedFlight.price"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import FlightList from '../components/FlightList.vue'
import SeatMap from '../components/SeatMap.vue'

const selectedFlight = ref(null)

const selectFlight = (flight) => {
  selectedFlight.value = flight
}
</script>

