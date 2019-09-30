package com.github.magg.parking.domain.allocation;

import com.github.magg.parking.domain.ParkingSlot;
import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.vehicles.Vehicle;

import java.util.List;

public interface ParkingSlotAllocation {

    /***
     *
     * @param slots the list of parking slots
     * @return int the index of the next possible position assigned
     */

    public int getNextAvailableSlot(List<ParkingSlot> slots);

    public int makeSlotAvailable(List<ParkingSlot> slots);


    public ParkingSlotType getRandomPolicy();

    /***
     *
     * @param vehicle the vehicle to be assigned
     * @param index the index of the position on the parking slot list
     * @param slots the list of parking slots
     * @return the list of parking slots with the vehicle assigned
     */

    public List<ParkingSlot> assignVehicleToSlot(Vehicle vehicle, int index, List<ParkingSlot> slots);
}
