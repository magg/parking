package com.github.magg.parking.domain.pricing;

import com.github.magg.parking.domain.ParkingTicket;
import com.github.magg.parking.domain.vehicles.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

public interface PricingPolicyCalculationStrategy {

    /***
     *
     *  Method to calculate the parking price
     * @param data the PricingPolicyData that contains the values needed for calculation
     * @param vehicle the Vehicle that needs to leave
     * @return a new {@link ParkingTicket} with calculated totalAmount set
     */

    public ParkingTicket calculateParkingPrice(PricingPolicyData data , Vehicle vehicle);


    /**
     *  Mehotd to calculate the time between the issue time of a Vehicle's parking ticket and the exit time
     * @param vehicle the Vehicle that needs to leave
     * @return Duration instance
     */
    default Duration calculateTime(Vehicle vehicle){
        LocalDateTime issuedTime = vehicle.getTicket().getIssueTime();
        LocalDateTime now = LocalDateTime.now();
        vehicle.getTicket().setExitTime(now);
        return Duration.between(issuedTime, now);
    }

}
