package com.sm.flightapp.controller;

import com.sm.flightapp.model.Flight;
import com.sm.flightapp.model.Seat;
import com.sm.flightapp.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * controller for adding and getting all flights
 */

@RestController
@RequestMapping("flights")
public class FlightController {


    @Autowired
    FlightService flightService;

    @GetMapping("all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return flightService.getAllFlights();
    }

    @PostMapping("add")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }



}
