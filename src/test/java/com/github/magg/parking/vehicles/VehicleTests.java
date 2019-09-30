package com.github.magg.parking.vehicles;


import com.github.magg.parking.domain.vehicles.Electric20KW;
import com.github.magg.parking.domain.vehicles.Electric50KW;
import com.github.magg.parking.domain.vehicles.VehicleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
}
