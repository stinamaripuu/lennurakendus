package com.sm.flightapp.dao;

import com.sm.flightapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * has 2 elements, findByFlightID was initially, just finds all seats based on that
 * but for frontend to accurately display and have all seats
 * the second (findByFlightIDOrderByRowNumberAscSeatNumberAsc)
 * had to be made so that the seats stayed in a logical array
 */
public interface SeatDao extends JpaRepository<Seat, Integer> {

    List<Seat> findByFlightID(Integer flightId);
    List<Seat> findByFlightIDOrderByRowNumberAscSeatNumberAsc(Integer flightId);
}
