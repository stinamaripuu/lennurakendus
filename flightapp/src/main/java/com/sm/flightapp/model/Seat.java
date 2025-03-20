package com.sm.flightapp.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Boolean available = true;
    private Boolean isWindow = false;
    private Boolean legRoom = false;
    private Boolean exit = false;
    private Integer rowNumber;
    private Integer seatNumber;
    private Integer flightID;
}
