package com.github.magg.parking.vehicles;


import com.github.magg.parking.domain.vehicles.Electric20KW;
import com.github.magg.parking.domain.vehicles.Electric50KW;
import com.github.magg.parking.domain.vehicles.RandomVehicleBuilder;
import com.github.magg.parking.domain.vehicles.Vehicle;
import com.github.magg.parking.domain.vehicles.VehicleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class VehicleTests {


    @Test
    public void testCreationAndType(){

        Electric50KW electric50KW = new Electric50KW();


        assertNotNull(electric50KW.getCarType());
        assertEquals(VehicleType.ELECTRIC_50KW, electric50KW.getCarType());

    }

    @Test
    public void testCreationAndDifferentTypes(){

        Electric50KW electric50KW = new Electric50KW();


        Electric20KW electric20KW = new Electric20KW();


        assertNotNull(electric50KW.getCarType());

        assertNotNull(electric20KW.getCarType());

        assertNotEquals(electric20KW.getCarType(), electric50KW.getCarType());

    }

    @Test
    public void testRandomBuilder(){
        RandomVehicleBuilder randomVehicleBuilder = new RandomVehicleBuilder();
        randomVehicleBuilder.addVehicles(VehicleType.ELECTRIC_20KW, 2)
                .addVehicles(VehicleType.ELECTRIC_50KW, 2)
                .addVehicles(VehicleType.GASOLINE_POWERED, 10);

        List<Vehicle> randomCars = randomVehicleBuilder.createVehicleList();

        assertNotNull(randomCars);
        System.out.println(randomCars.size());
        assertTrue(randomCars.get(0).getLicensePlates().contains("EU-FR"));

    }
}
