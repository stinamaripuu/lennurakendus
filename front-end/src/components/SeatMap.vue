<template>
  <!-- Filters for seat selection -->

  <div>
    <h1>Istmete plaan</h1>
    <p>Vali siit endale sobivad filtrid ning vajuta nuppu "Soovita mulle kohti".</p>
    <div class="preferences">
      <label><input type="checkbox" v-model="preferences.window" /> Tahan istekohta akna alla </label>
      <label><input type="checkbox" v-model="preferences.legRoom" /> Tahan rohkem jalaruumi </label>
      <label><input type="checkbox" v-model="preferences.exit" /> Tahan kohta väljapääsu lähedal</label>
      <label><input type="checkbox" v-model="grouped" /> Tahan kohti võimalikult lähedal üksteisele</label>
      <label>
        Palun sisesta soovitud piletite arv:
        <input
            type="number"
            v-model.number="seatCount"
            :max="6"
            :min="1"
            class="ticket-input"
        />
      </label>

      <div class="price-summary">
        Ühe pileti hind: {{ flightPrice }} €
        <br />
        Piletite hind kokku: <strong>{{ seatCount * flightPrice }} €</strong>
      </div>

      <label>
        <input type="checkbox" v-model="manualSelection" />
        Tahan ise plaani pealt kohti valida
      </label>

      <button v-if="!manualSelection" class="suggest-button" @click="applySeatRecommendation">
        Soovita mulle kohti
      </button>

      <button v-if="selectedSeats.length > 0" class="cancel-button" @click="clearSelection">
        Tühista kohasoovitused
      </button>
    </div>
    <!-- Seat display in grid -->

    <div class="seat-grid">
      <button
          v-for="seat in seats"
          :key="seat.id"
          :disabled="!seat.available || (!manualSelection && !selectedSeats.includes(seat.id))"
          :class="{
          taken: !seat.available,
          selected: selectedSeats.includes(seat.id),
          available: seat.available && !selectedSeats.includes(seat.id),
        }"
          :style="{
          gridRow: seat.rowNumber,
          gridColumn: seat.seatNumber > 3 ? seat.seatNumber + 1 : seat.seatNumber
        }"
          @click="manualSelection && toggleSelect(seat.id)"
      >
        {{ seat.rowNumber }}{{ seat.seatNumber }}
      </button>
    </div>
    <!-- Booking selected seats -->

    <br />
    <button :disabled="selectedSeats.length === 0" class="book-button" @click="bookSelected">
      Broneeri valitud kohad
    </button>
  </div>
</template>

<script setup>
import { ref, watch, inject } from 'vue'
import axios from 'axios'

const showNotification = inject('showNotification')

/* eslint-disable no-undef */
const props = defineProps({
  flightId: {
    type: Number,
    required: true
  },
  flightPrice: {
    type: Number,
    required: true
  }
})


const seats = ref([])
const selectedSeats = ref([])
const grouped = ref(false)

const preferences = ref({
  window: false,
  legRoom: false,
  exit: false,
})

const seatCount = ref(1)
const manualSelection = ref(false)

const toggleSelect = (id) => {
  if (selectedSeats.value.includes(id)) {
    selectedSeats.value = selectedSeats.value.filter(s => s !== id)
  } else if (selectedSeats.value.length < seatCount.value) {
    selectedSeats.value.push(id)
  }
}

const fetchSeats = async () => {
  const res = await axios.get(`http://localhost:8080/seats/flight/${props.flightId}`)
  seats.value = res.data
}

watch(() => props.flightId, fetchSeats, { immediate: true })

/**
 * Algorithm for finding best seating arrangement that fits the desired criteria that comes from checkboxes
 * ChatGPT generated
 */
const applySeatRecommendation = () => {
  const available = seats.value.filter(s => s.available)
  const requiredCount = seatCount.value
  const selected = []
  const usedIds = new Set()

  const requiredCriteria = ['window', 'legRoom', 'exit']
  const matchesPreference = (seat, criteria) => {
    if (criteria === 'window') return seat.isWindow
    if (criteria === 'legRoom') return seat.legRoom
    if (criteria === 'exit') return seat.exit
    return false
  }

  // 1. Find anchors for each preference (1 per)
  const anchors = []
  for (const crit of requiredCriteria) {
    if (!preferences.value[crit]) continue

    const seat = available.find(s =>
        matchesPreference(s, crit) && !usedIds.has(s.id)
    )

    if (seat) {
      selected.push(seat.id)
      anchors.push(seat)
      usedIds.add(seat.id)
    }
  }

  let remaining = requiredCount - selected.length

  // 2. Try to build around anchors if grouped
  if (grouped.value && anchors.length > 0 && remaining > 0) {
    for (const anchor of anchors) {
      const rowSeats = available
          .filter(s => s.rowNumber === anchor.rowNumber && !usedIds.has(s.id))
          .sort((a, b) => a.seatNumber - b.seatNumber)

      const anchorIndex = rowSeats.findIndex(s => s.id === anchor.id)

      let left = anchorIndex - 1
      let right = anchorIndex + 1

      while (remaining > 0 && (left >= 0 || right < rowSeats.length)) {
        if (left >= 0 && !usedIds.has(rowSeats[left].id)) {
          selected.push(rowSeats[left].id)
          usedIds.add(rowSeats[left].id)
          remaining--
        }

        if (right < rowSeats.length && remaining > 0 && !usedIds.has(rowSeats[right].id)) {
          selected.push(rowSeats[right].id)
          usedIds.add(rowSeats[right].id)
          remaining--
        }

        left--
        right++
      }

      if (remaining <= 0) break
    }
  }

  // 3. If grouping still needed and we have room: form clean groups
  if (grouped.value && remaining > 0) {
    const sorted = available
        .filter(s => !usedIds.has(s.id))
        .sort((a, b) =>
            a.rowNumber !== b.rowNumber
                ? a.rowNumber - b.rowNumber
                : a.seatNumber - b.seatNumber
        )

    for (let i = 0; i < sorted.length && remaining > 0; i++) {
      const group = [sorted[i]]
      let last = sorted[i]

      for (let j = i + 1; j < sorted.length && group.length < 3; j++) {
        const next = sorted[j]
        if (
            next.rowNumber === last.rowNumber &&
            next.seatNumber === last.seatNumber + 1 &&
            !usedIds.has(next.id)
        ) {
          group.push(next)
          last = next
        } else {
          break
        }
      }

      for (const seat of group) {
        if (!usedIds.has(seat.id)) {
          selected.push(seat.id)
          usedIds.add(seat.id)
          remaining--
        }
        if (remaining <= 0) break
      }
    }
  }

  // 4. Fallback: fill up with best-available by score
  if (remaining > 0) {
    const fallback = available
        .filter(s => !usedIds.has(s.id))
        .sort((a, b) => {
          const prefs = requiredCriteria
          const score = seat => prefs.reduce(
              (acc, key) => acc + (preferences.value[key] && matchesPreference(seat, key) ? 1 : 0),
              0
          )
          return score(b) - score(a)
        })

    for (const seat of fallback) {
      selected.push(seat.id)
      usedIds.add(seat.id)
      remaining--
      if (remaining <= 0) break
    }
  }

  // 5. Assign final selected seats
  selectedSeats.value = selected
}




const bookSelected = async () => {
  for (const seatId of selectedSeats.value) {
    await axios.post(`http://localhost:8080/seats/${seatId}/select`)
  }
  await fetchSeats()
  selectedSeats.value = []
  showNotification('Istmed edukalt broneeritud!')
}

const clearSelection = () => {
  selectedSeats.value = []
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

.suggest-button {
  background-color: #f1c40f; /* Yellow */
}

.suggest-button:hover:not(:disabled) {
  background-color: #d4ac0d;
}

.cancel-button {
  background-color: #e74c3c; /* Red */
}

.cancel-button:hover:not(:disabled) {
  background-color: #c0392b;
}

.book-button {
  background-color: #2ecc71; /* Green */
}

.book-button:hover:not(:disabled) {
  background-color: #27ae60;
}

.available {
  background-color: lightgreen;
}

.selected {
  background-color: gold;
}

.taken {
  background-color: gray;
  cursor: not-allowed;
}

.preferences {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
  text-align: left;
}

.preferences label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.ticket-input {
  width: 60px;
  padding: 4px;
}

.price-summary {
  margin-top: 1rem;
  font-size: 1.1rem;
  text-align: center;
}
</style>
