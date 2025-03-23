<template>
  <div>
    <h1>Istmete plaan</h1>
    <div class="seat-grid">
      <button v-for="seat in seats" :key="seat.id"
              :disabled="!seat.available"
              :class="{
                taken: !seat.available,
                selected: selectedSeats.includes(seat.id),
                available: seat.available && !selectedSeats.includes(seat.id),
              }"
              :style="{
                gridRow: seat.rowNumber,
                 gridColumn: seat.seatNumber > 3 ? seat.seatNumber + 1 : seat.seatNumber
              }"
              @click="toggleSelect(seat.id)">
        {{seat.rowNumber}}{{seat.seatNumber}}
      </button>

    </div>
    <br />
    <button :disabled="selectedSeats.length===0" @click="bookSelected">Broneeri valitud kohad</button>
  </div>
</template>

<script setup>
import { ref, watch, inject } from 'vue'
import axios from 'axios'

const showNotification = inject('showNotification')

/* eslint-disable no-undef */
const props = defineProps({
  flightId: Number
})

const seats = ref([])
const selectedSeats = ref([])

const toggleSelect = (id) => {
  if (selectedSeats.value.includes(id)) {
    selectedSeats.value = selectedSeats.value.filter(s => s !== id)
  } else {
    selectedSeats.value.push(id)
  }
}

const fetchSeats = async () => {
  const res = await axios.get(`http://localhost:8080/seats/flight/${props.flightId}`)
  seats.value = res.data
}

watch(() => props.flightId, fetchSeats, { immediate: true })

const bookSelected = async () => {
  for (const seatId of selectedSeats.value) {
    await axios.post(`http://localhost:8080/seats/${seatId}/select`)
  }
  await fetchSeats()
  selectedSeats.value = []
  showNotification('Istmed edukalt broneeritud!')
}


</script>

<style scoped>
.seat-grid {
  display: grid;
  grid-template-columns: repeat(3, 50px) 30px repeat(3, 50px);
  gap: 8px;
  justify-content: center;
  padding: 1rem;
}

button {
  padding: 10px;
  border: none;
  border-radius: 4px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}
.available{
  background-color: lightgreen;
}

.selected{
  background-color: gold;
}
.taken{
  background-color: gray;
  cursor: not-allowed;
}

</style>