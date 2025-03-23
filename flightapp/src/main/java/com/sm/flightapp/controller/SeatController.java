package com.sm.flightapp.controller;

import com.sm.flightapp.model.Seat;
import com.sm.flightapp.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Seat>> getSeatsForFlight(@PathVariable Integer flightId) {
        return seatService.getSeatsForFlight(flightId);
    }

    @PostMapping("/{seatId}/select")
    public ResponseEntity<String> selectSeat(@PathVariable Integer seatId) {
        return seatService.selectSeat(seatId);
    }
}
