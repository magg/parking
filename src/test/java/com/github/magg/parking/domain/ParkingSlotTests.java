package com.github.magg.parking.domain;

import com.github.magg.parking.domain.vehicles.Electric20KW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.magg.parking.domain.ParkingSlotType.POWER_SUPPLY_OF_20KW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ParkingSlotTests {

    @Test
    public void testCreationFree(){
        ParkingSlot slot = ParkingSlot.builder()
                .free(true)
                .type(POWER_SUPPLY_OF_20KW)
                .id("TEST123").build();
        assertTrue(slot.isFree());
    }

    @Test
    public void testAssignCar(){
        ParkingSlot slot = ParkingSlot.builder()
                .free(true)
                .type(POWER_SUPPLY_OF_20KW)
                .id("TEST123").build();

        Electric20KW car = new Electric20KW();

        slot = slot.toBuilder().vehicle(car).build();

        assertEquals(car, slot.getVehicle());
    }

    @Test
    public void testCreationWithoutCar(){
        ParkingSlot slot = ParkingSlot.builder()
                .free(true)
                .type(POWER_SUPPLY_OF_20KW)
                .id("TEST123").build();

        Electric20KW car = new Electric20KW();

        assertNotEquals(car, slot.getVehicle());
    }


}
