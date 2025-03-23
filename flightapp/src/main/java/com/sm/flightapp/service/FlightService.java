package com.sm.flightapp.service;

import com.sm.flightapp.dao.FlightDao;
import com.sm.flightapp.dao.SeatDao;
import com.sm.flightapp.model.Flight;
import com.sm.flightapp.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for getting and adding flights
 */
@Service
public class FlightService {

    @Autowired
    FlightDao flightDao;

    @Autowired
    private SeatService seatService;

    public ResponseEntity<List<Flight>> getAllFlights() {
        return new ResponseEntity<>(flightDao.findAll(), HttpStatus.OK);
    }

    /**
     * Adds flights, and also creates list of all seats, some of whic
     * are occupied by random.
     * @param flight
     * @return
     */
    public ResponseEntity<Flight> addFlight(Flight flight) {
        Flight newFlight = flightDao.save(flight);
        List<Seat> seats = (List<Seat>) seatService.generateSeats(newFlight.getId());
        newFlight.setSeats(seats);
        return new ResponseEntity<>(flightDao.save(newFlight), HttpStatus.OK);
    }

}
