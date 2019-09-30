package com.github.magg.parking.domain.vehicles;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class Car extends Vehicle {
    public Car() {
        super(VehicleType.GASOLINE_POWERED);
    }
}
