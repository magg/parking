package com.github.magg.parking.domain.allocation;

import com.github.magg.parking.domain.ParkingSlot;
import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.vehicles.Vehicle;
import com.github.magg.parking.exception.ParkingException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

import static com.github.magg.parking.exception.ParkingException.PARKING_SLOTS_INVALID;

@Slf4j
public class RandomParkingSlotAllocation implements ParkingSlotAllocation {

    @Override
    public int getNextAvailableSlot(List<ParkingSlot> slots) {

        if ( slots == null || slots.isEmpty() ) return -1;

        List<Integer> freeList = new ArrayList<>();

        for (int i = 0; i < slots.size(); i++){
            if (slots.get(i).isFree()) {
                freeList.add(i);
            }
        }

        if (freeList.size() == 1) return freeList.get(0);

        if (freeList.size() == 0) return -1;

        int rand = getRandomNumberInRange(0, freeList.size() -1);
        return freeList.get(rand);
    }

    @Override
    public int makeSlotAvailable(List<ParkingSlot> slots) {

        if ( slots == null || slots.isEmpty() ) return -1;

        List<Integer> occupiedList = new ArrayList<>();

        for (int i = 0; i < slots.size(); i++){
            if (!slots.get(i).isFree()) {
                occupiedList.add(i);
            }
        }

        log.info("random occupied sized {}", occupiedList.size());

        if (occupiedList.size() == 1) return occupiedList.get(0);

        if (occupiedList.size() == 0) return -1;

        int rand = getRandomNumberInRange(0, occupiedList.size() -1);
        return occupiedList.get(rand);
    }

    @Override
    public List<ParkingSlot> assignVehicleToSlot(Vehicle vehicle, int index,  List<ParkingSlot> slots) {

        if ( slots == null || slots.isEmpty() ) throw new ParkingException(PARKING_SLOTS_INVALID);


        ParkingSlot slot =  slots.get(index);
        vehicle.setParkingSlotID(slot.getId());
        slot =  slot
                .toBuilder()
                .vehicle(vehicle)
                .free(false)
                .build();
        slots.set(index, slot);

        return slots;

    }

    private int getRandomNumberInRange(int min, int max) {
        Random random = new Random();

        OptionalInt randomInt = random.ints(min, (max + 1)).findFirst();

        if (randomInt.isPresent()) return randomInt.getAsInt();

        else return -1;

    }

    public ParkingSlotType getRandomPolicy(){
       List< ParkingSlotType> types = Arrays.asList(ParkingSlotType.values());
        int rand = getRandomNumberInRange(0, types.size() -1);
        return types.get(rand);

    }

}
