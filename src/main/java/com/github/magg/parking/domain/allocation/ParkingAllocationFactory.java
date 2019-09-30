package com.github.magg.parking.domain.allocation;

public class ParkingAllocationFactory {


    /***
     *  Factory method to create ParkingSlotAllocation
     * @param type the ParkingSlotAllocationType
     * @return a class that implements ParkingSlotAllocation
     */

    public static ParkingSlotAllocation createParkingAllocation(ParkingSlotAllocationType type){
        switch(type){
            case RANDOM:
                return new RandomParkingSlotAllocation();
            default:
                throw new IllegalArgumentException("Unrecognized PolicyType " + type);
        }
    }
}
