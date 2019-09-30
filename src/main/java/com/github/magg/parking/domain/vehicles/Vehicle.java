package com.github.magg.parking.domain.vehicles;

import com.github.magg.parking.domain.ParkingTicket;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Vehicle {

    private String licensePlates;
    private VehicleType carType;
    private ParkingTicket ticket;
    private String parkingSlotID;

    public Vehicle(VehicleType carType) {
        this.carType = carType;
    }
}
