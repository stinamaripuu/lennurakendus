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

@Service
public class FlightService {

    @Autowired
    FlightDao flightDao;

    @Autowired
    SeatDao seatDao;

    public ResponseEntity<List<Flight>> getAllFlights() {
        return new ResponseEntity<>(flightDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Flight> addFlight(Flight flight) {
        Flight newFlight = flightDao.save(flight);
        List<Seat> seats = generateSeats(newFlight.getId());
        newFlight.setSeats(seats);
        return new ResponseEntity<>(flightDao.save(newFlight), HttpStatus.OK);
    }


    private List<Seat> generateSeats(Integer flightId) {
        List<Seat> seats = new ArrayList<>();

        int totalRows = 6; // palju istmeid kokku
        int seatsInRow = 6; // istmeid ühes reas
        //int centerExitRow = 3; // et teada kuhu jalaruumi  - saab lisada vajadusel


        Random random = new Random();
        int howManyOccupied = 15; // palju täidetud kohti tuleb, saab vabalt muuta
        List<Integer> occupiedSeats = new ArrayList<>();
        for (int i = 0; i < howManyOccupied; i++) {
            occupiedSeats.add(random.nextInt(totalRows * seatsInRow)); // arvutab istme "id", mille järgi märgitakse kohe available = false
        }

        int i = 0; // istme id lennukis
        for (int row = 1; row <= totalRows; row++) {
            for (int seat = 1; seat <= seatsInRow; seat++) {
                Seat newSeat = new Seat();
                newSeat.setFlightID(flightId);
                newSeat.setSeatNumber(seat);
                newSeat.setRowNumber(row);
                if (occupiedSeats.contains(i)) {
                    newSeat.setAvailable(false);
                }
                if (seat == 1 || seat == seatsInRow) {
                    newSeat.setIsWindow(true);
                }
                if (row == 1 || row == totalRows) {
                    newSeat.setExit(true);
                    if (row == 1) {
                        newSeat.setLegRoom(true);
                    }
                }
                seats.add(newSeat);
                seatDao.save(newSeat);
                i++;
            }
        }
        return seats;
    }
}
