package com.github.magg.parking.domain.vehicles;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class Electric20KW extends Vehicle {

    public Electric20KW() {
        super(VehicleType.ELECTRIC_20KW);
    }

}
