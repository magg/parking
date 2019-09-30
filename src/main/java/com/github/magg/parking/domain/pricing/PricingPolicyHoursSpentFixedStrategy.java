package com.github.magg.parking.domain.pricing;

import com.github.magg.parking.domain.ParkingTicket;
import com.github.magg.parking.domain.vehicles.Vehicle;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
public class PricingPolicyHoursSpentFixedStrategy implements  PricingPolicyCalculationStrategy {

    @Override
    public ParkingTicket calculateParkingPrice(PricingPolicyData data, Vehicle vehicle) {
        long timeInHours = calculateTime(vehicle).toHours();
        if (timeInHours < 1){
            timeInHours = 1;
        }
        BigDecimal result = data.hourPrice.multiply( new BigDecimal(timeInHours)).add(data.fixedPrice);
        vehicle.getTicket().setTotalAmount(result);
        return vehicle.getTicket();
    }
}
