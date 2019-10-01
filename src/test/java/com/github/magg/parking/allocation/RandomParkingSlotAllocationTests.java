package com.github.magg.parking.allocation;

import com.github.magg.parking.domain.ParkingSlot;
import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.allocation.ParkingAllocationFactory;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocation;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocationType;
import com.github.magg.parking.domain.allocation.RandomParkingSlotAllocation;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentFixedStrategy;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class RandomParkingSlotAllocationTests {

    List<ParkingSlot> queue;


    @Before
    public void setUp(){
        queue = new ArrayList<>();
        for (int i = 0; i < 19; i++){
            ParkingSlot slot = ParkingSlot.builder()
                    .free(false)
                    .type(ParkingSlotType.POWER_SUPPLY_OF_50KW)
                    .id(String.valueOf(i)).build();
            queue.add(slot);

        }


        for (int i = 0; i < 1; i++){
            ParkingSlot slot = ParkingSlot.builder()
                    .free(true)
                    .type(ParkingSlotType.POWER_SUPPLY_OF_50KW)
                    .id(String.valueOf(19)).build();
            queue.add(slot);

        }
    }

    @Test
    public void testRandomAllocationCreation(){
       ParkingSlotAllocation random =  ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        assertNotNull(random);
        assertEquals(RandomParkingSlotAllocation.class, random.getClass());
    }


    @Test
    public void testAvailable(){
        ParkingSlotAllocation random =  ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        int slotIndex = random.getNextAvailableSlot(queue);
        assertEquals(19, slotIndex);
    }


    @Test
    public void testRandomAvailable(){
        ParkingSlot slot = ParkingSlot.builder()
                .free(true)
                .type(ParkingSlotType.POWER_SUPPLY_OF_50KW)
                .id(String.valueOf(20)).build();
        queue.add(slot);


        ParkingSlotAllocation random =  ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        int slotIndex = random.getNextAvailableSlot(queue);

        Set<Integer> possible = new HashSet<>();
        possible.add(19);
        possible.add(20);

        assertTrue(possible.contains(slotIndex));

    }

    @Test
    public void nullTest(){

        ParkingSlotAllocation random =  ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        int slotIndex =  random.getNextAvailableSlot(null);

        assertEquals(-1, slotIndex);

    }

    @Test
    public void emptytest(){

        ParkingSlotAllocation random =  ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        int slotIndex =  random.getNextAvailableSlot(parkingSlots);

        assertEquals(-1, slotIndex);

    }

    @Test
    public void testMakeAvailable(){
        ParkingSlotAllocation random =  ParkingAllocationFactory.createParkingAllocation(ParkingSlotAllocationType.RANDOM);
        int slotIndex = random.makeSlotAvailable(queue);
        assertFalse(queue.get(slotIndex).isFree());
    }




}
