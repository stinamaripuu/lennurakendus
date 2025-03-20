package com.sm.flightapp.dao;

import com.sm.flightapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatDao extends JpaRepository<Seat, Integer> {

}
