package com.github.magg.parking;

import com.github.magg.parking.config.ParkingConfiguration;
import com.github.magg.parking.domain.ParkingSlot;
import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.ParkingTicket;
import com.github.magg.parking.domain.allocation.ParkingAllocationFactory;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocation;
import com.github.magg.parking.domain.pricing.PricingPolicyCalculationStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyData;
import com.github.magg.parking.domain.pricing.PricingPolicyStrategyFactory;
import com.github.magg.parking.domain.vehicles.Vehicle;
import com.github.magg.parking.domain.vehicles.VehicleType;
import com.github.magg.parking.exception.ParkingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.magg.parking.exception.ParkingException.NO_SLOT_AVAILABLE;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Slf4j
public class TollParking {

    private Map<ParkingSlotType, List<ParkingSlot>> parkingSlots;
    private ParkingConfiguration parkingConfiguration;
    private PricingPolicyCalculationStrategy pricingPolicy;
    private ParkingSlotAllocation parkingSlotAllocation;
    private PricingPolicyData pricingPolicyData;


    /***
     *  The main constructor for the TollParking API, {@link ParkingConfiguration} and {@link PricingPolicyData} need to be passed as arguments
     * @param parkingConfiguration the ParkingConfiguration
     * @param pricingPolicyData the PricingPolicyData
     */
    public TollParking(ParkingConfiguration parkingConfiguration, PricingPolicyData pricingPolicyData) {


        if (parkingConfiguration == null){
            throw new IllegalArgumentException("Parking Configuration must not be null!");
        }


        if (pricingPolicyData == null){
            throw new IllegalArgumentException("Pricing Policy Data must not be null!");
        }

        this.parkingConfiguration = parkingConfiguration;
        this.parkingSlots = new ConcurrentHashMap<>();
        this.pricingPolicyData = pricingPolicyData;
        this.pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(this.parkingConfiguration.getPolicyType());
        this.parkingSlotAllocation = ParkingAllocationFactory.createParkingAllocation(this.parkingConfiguration.getParkingSlotAllocationType());
        populateParkingSlots();
    }


    private void populateParkingSlots(){
        for (Map.Entry<ParkingSlotType, Integer> entries: parkingConfiguration.getParkingSlotsSizes().entrySet()){
            List<ParkingSlot> queue = createQueue(entries.getKey(), entries.getValue());
            parkingSlots.put(entries.getKey(), queue);
        }
    }

    private List<ParkingSlot> createQueue(ParkingSlotType type, int size){
        List<ParkingSlot> queue = new ArrayList<>();

        for (int i = 0; i < size; i++){
            ParkingSlot slot = ParkingSlot.builder()
                    .free(true)
                    .type(type)
                    .id(String.valueOf(i)).build();
            queue.add(slot);

        }
        return queue;
    }

    /***
     *  Gets a new ParkingTicket
     * @param vehicle a vehicle
     * @return an Optional of ParkingTicket if there's enough space for it
     */

    public Optional<ParkingTicket> getParkingTicket(Vehicle vehicle){

        if (isFull(vehicle)){
            log.info("FULL");

            return Optional.empty();
        } else {
            log.info("NOT FULL");
        }

        ParkingTicket ticket = new ParkingTicket();
        vehicle.setTicket(ticket);

        return Optional.of(ticket);
    }

    /***
     *
     * Checks if a List of ParkingSlots is Full
     * @param vehicle the vehicle that needs a slot
     * @return boolean  whether the parkingslot for the given vehicle is full or not
     */

    public boolean isFull(Vehicle vehicle){

        ParkingSlotType parkingSlotType =getParkingSlotTypeForVehicleType(vehicle.getCarType());
        List<ParkingSlot> queue = parkingSlots.get(parkingSlotType);
        if (queue != null){
            for (ParkingSlot parkingSlot: queue){
                if(parkingSlot.isFree()){
                   return false;
                }
            }
        }
        return true;
    }

    /***
     * Gets the parkingslottype for a vehicletype
     *
     * @param type the VehicleType
     * @return a ParkingSlotType
     */

    private ParkingSlotType getParkingSlotTypeForVehicleType(VehicleType type){

        switch(type){
            case ELECTRIC_20KW:
                return ParkingSlotType.POWER_SUPPLY_OF_20KW;
            case ELECTRIC_50KW:
                return ParkingSlotType.POWER_SUPPLY_OF_50KW;
            case GASOLINE_POWERED:
                return ParkingSlotType.STANDARD;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    /***
     *  Adds a Vehicle into the TollParking
     *
     * @param vehicle The new Vehicle
     * @return a new Parking ticket
     */
    public synchronized ParkingTicket vehicleEnters(Vehicle vehicle){
        Optional<ParkingTicket> parkingTicket = getParkingTicket(vehicle);

        if(!parkingTicket.isPresent()){
            throw new ParkingException(NO_SLOT_AVAILABLE);
        } else {
            log.info("slot avail");
        }

        ParkingSlotType parkingSlotType =getParkingSlotTypeForVehicleType(vehicle.getCarType());
        int index = parkingSlotAllocation.getNextAvailableSlot(parkingSlots.get(parkingSlotType));
        List<ParkingSlot> slots = parkingSlotAllocation.assignVehicleToSlot(vehicle, index, parkingSlots.get(parkingSlotType));
        parkingSlots.put(parkingSlotType, slots);


        return parkingTicket.get();
    }


    /***
     *  Removes a vehicle from the TollParking
     *
     * @param vehicle a vehicle that leaves the parking
     * @return a ParkingTicket with the new calculated amount
     */
    public synchronized ParkingTicket vehicleLeaves(Vehicle vehicle){


        ParkingTicket ticket = pricingPolicy.calculateParkingPrice(pricingPolicyData, vehicle);
        ParkingSlotType parkingSlotType = getParkingSlotTypeForVehicleType(vehicle.getCarType());

        List <ParkingSlot> slots =parkingSlots.get(parkingSlotType);
        int index = -1;
        if (vehicle.getParkingSlotID() != null && !vehicle.getParkingSlotID().isEmpty()){
            index = Integer.parseInt(vehicle.getParkingSlotID());
        }


        ParkingSlot slot = slots.get(index);

               slot = slot
                        .toBuilder()
                        .free(true)
                        .vehicle(null)
                        .build();
        slots.set(index, slot);

        parkingSlots.put(parkingSlotType, slots);
        vehicle.setTicket(ticket);
        vehicle.setParkingSlotID(null);
        return ticket ;
    }


    public synchronized ParkingTicket vehicleLeaves(){

        ParkingSlotType parkingSlotType = parkingSlotAllocation.getRandomPolicy();

        List <ParkingSlot> slots =parkingSlots.get(parkingSlotType);
        int index = parkingSlotAllocation.makeSlotAvailable(slots);

        ParkingSlot slot = slots.get(index);

        Vehicle vehicle = slot.getVehicle();

        ParkingTicket ticket = pricingPolicy.calculateParkingPrice(pricingPolicyData, vehicle);


        slot = slot
                .toBuilder()
                .free(true)
                .vehicle(null)
                .build();
        slots.set(index, slot);

        parkingSlots.put(parkingSlotType, slots);
        vehicle.setTicket(ticket);
        vehicle.setParkingSlotID(null);
        return ticket;
    }



}
