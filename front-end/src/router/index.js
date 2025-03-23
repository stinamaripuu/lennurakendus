import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import BookingPage from '../pages/BookingPage.vue'
import AddFlightPage from "@/pages/AddFlightPage.vue";

const routes = [
    { path: '/', component: HomePage },
    { path: '/booking', component: BookingPage },
    { path: '/add-flight', component: AddFlightPage },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router