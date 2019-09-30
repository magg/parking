package com.github.magg.parking.domain.pricing;

import com.github.magg.parking.domain.ParkingTicket;
import com.github.magg.parking.domain.vehicles.Vehicle;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@ToString
@Slf4j
public class PricingPolicyHoursSpentStrategy implements PricingPolicyCalculationStrategy {

    @Override
    public ParkingTicket calculateParkingPrice(PricingPolicyData data, Vehicle vehicle) {
        long timeInHours = calculateTime(vehicle).toHours();
        if (timeInHours < 1){
            timeInHours = 1;
        }
        log.info(" time in hourrs {}", timeInHours);
        BigDecimal result = data.hourPrice.multiply( new BigDecimal(timeInHours));
        vehicle.getTicket().setTotalAmount(result);
        return vehicle.getTicket();
    }
}
