package com.sm.flightapp.service;

import com.sm.flightapp.dao.FlightDao;
import com.sm.flightapp.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    FlightDao flightDao;

    public ResponseEntity<List<Flight>> getAllFlights(){
        return new ResponseEntity<>(flightDao.findAll(),HttpStatus.OK);
    }
}
