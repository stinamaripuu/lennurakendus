package com.sm.flightapp.dao;

import com.sm.flightapp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * for saving flights
 */

@Repository
public interface FlightDao extends JpaRepository<Flight, Integer> {
}
