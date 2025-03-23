package com.sm.flightapp.dao;

import com.sm.flightapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatDao extends JpaRepository<Seat, Integer> {

    List<Seat> findByFlightID(Integer flightId);
    List<Seat> findByFlightIDOrderByRowNumberAscSeatNumberAsc(Integer flightId);
}
