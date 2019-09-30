package com.github.magg.parking.config;

import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocationType;
import com.github.magg.parking.domain.pricing.PolicyType;
import com.github.magg.parking.exception.ParkingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(JUnit4.class)
public class ParkingConfigurationTests {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testInvalidPolicy(){

        expectedEx.expect(ParkingException.class);
        expectedEx.expectMessage("Parking Policy cannot be null");

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();
    }


    @Test
    public void testInvalidParkingSlots(){

        expectedEx.expect(ParkingException.class);
        expectedEx.expectMessage("Parking Slots are invalid, empty or NULL");

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, -40)
                .policyType(PolicyType.HOUR_SPENT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();
    }


    @Test
    public void testInvalidAllocation(){

        expectedEx.expect(ParkingException.class);
        expectedEx.expectMessage("Parking ALLOCATION cannot be null");

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 30)
                .policyType(PolicyType.HOUR_SPENT)
                .build();
    }


    @Test
    public void testValidCreation(){
        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .policyType(PolicyType.HOUR_SPENT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        assertNotNull(parkingConfiguration);
        assertEquals(ParkingSlotAllocationType.RANDOM, parkingConfiguration.getParkingSlotAllocationType());
        assertEquals(PolicyType.HOUR_SPENT, parkingConfiguration.getPolicyType());


        assertEquals(2, parkingConfiguration.getParkingSlotsSizes(ParkingSlotType.POWER_SUPPLY_OF_50KW));

    }
}
