package com.github.magg.parking.domain.vehicles;

import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentFixedStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomVehicleBuilder {

    public RandomVehicleBuilder() {
        this.vehicles = new ArrayList<>();
    }

    private Vehicle createVehicle(VehicleType type){

        switch(type){
            case ELECTRIC_20KW:
                return new Electric20KW();
            case ELECTRIC_50KW:
                return new Electric50KW();
            case GASOLINE_POWERED:
                return new Car();
            default:
                throw new IllegalArgumentException("Unrecognized VehicleType " + type);
        }

    }

    /***
     * The vehicles in the list
     */
    private List<Vehicle> vehicles;


    /***
     * Add vehicles of type VehicleType
     *
     * @param count  the number of vehicles to create
     * @param type the VehicleType of the vehicle
     * @return the RandomVehicleBuilder instance
     */
    public RandomVehicleBuilder addVehicles(VehicleType type, int count){


        for (int i = 1 ; i <= vehicles.size(); i++){
            Vehicle vehicle = createVehicle(type);
            vehicle.setLicensePlates("EU-FR-"+ i);
            vehicles.add(vehicle);
        }

        return this;
    }

    /***
     * @return a shuffled list of vehicles
     */
    public List<Vehicle> createVehicleList() {
        List<Vehicle> shuffled = new ArrayList<>(this.vehicles);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}
