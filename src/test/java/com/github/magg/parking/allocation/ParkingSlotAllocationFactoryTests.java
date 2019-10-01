package com.github.magg.parking.allocation;

import com.github.magg.parking.domain.allocation.ParkingAllocationFactory;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocation;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocationType;
import com.github.magg.parking.domain.pricing.PolicyType;
import com.github.magg.parking.domain.pricing.PricingPolicyCalculationStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyStrategyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertNotNull;

@RunWith(JUnit4.class)
public class ParkingSlotAllocationFactoryTests {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalid(){
        ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.ROUND_ROBIN);
    }

    @Test
    public void testValid(){
        ParkingSlotAllocation allocation =ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        assertNotNull(allocation);

    }
}

