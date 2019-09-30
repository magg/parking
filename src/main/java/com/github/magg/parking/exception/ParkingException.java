package com.github.magg.parking.exception;

public class ParkingException extends RuntimeException {
    public static final String NO_SLOT_AVAILABLE = "There's not a ParkingSlot available";
    public static final String INVALID_PARKING_EXIT_DATE = "Parking Ticket exitTime cannot be before issueTime";
    public static final String PARKING_SLOTS_INVALID = "Parking Slots are invalid, empty or NULL";
    public static final String  PARKING_POLICY_NULL = "Parking Policy cannot be null";
    public static final String  PARKING_ALLOCATION_NULL = "Parking ALLOCATION cannot be null";


    public ParkingException(String message) {
        super(message);
    }

}