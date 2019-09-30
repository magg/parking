package com.github.magg.parking.domain;

import com.github.magg.parking.domain.vehicles.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@ToString
public class ParkingSlot {

    private String id;
    private boolean free;
    private Vehicle vehicle;
    private ParkingSlotType type;

}
