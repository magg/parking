package com.github.magg.parking.domain.vehicles;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class Electric50KW extends Vehicle {

    public Electric50KW() {
        super(VehicleType.ELECTRIC_50KW);
    }
}
