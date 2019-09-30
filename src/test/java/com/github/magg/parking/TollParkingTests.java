package com.github.magg.parking;

import com.github.magg.parking.config.ParkingConfiguration;
import com.github.magg.parking.domain.ParkingSlot;
import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.ParkingTicket;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocationType;
import com.github.magg.parking.domain.pricing.PolicyType;
import com.github.magg.parking.domain.pricing.PricingPolicyData;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentFixedStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentStrategy;
import com.github.magg.parking.domain.vehicles.Car;
import com.github.magg.parking.domain.vehicles.Electric20KW;
import com.github.magg.parking.domain.vehicles.Electric50KW;
import com.github.magg.parking.domain.vehicles.RandomVehicleBuilder;
import com.github.magg.parking.domain.vehicles.Vehicle;
import com.github.magg.parking.domain.vehicles.VehicleType;
import com.github.magg.parking.exception.ParkingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({PricingPolicyHoursSpentStrategy.class, PricingPolicyHoursSpentFixedStrategy.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*"})
@RunWith(PowerMockRunner.class)
public class TollParkingTests {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void parkingLotIntegrationHourPriceTest(){

        String now = LocalDateTime.now().plusMinutes(45).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .policyType(PolicyType.HOUR_SPENT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        PricingPolicyData pricingPolicyData = PricingPolicyData
                .builder()
                .hourPrice(new BigDecimal(37))
                .build();

        TollParking parkingLot = new TollParking(parkingConfiguration, pricingPolicyData);

        Electric50KW teslaModelX = new Electric50KW();
         parkingLot.vehicleEnters(teslaModelX);

        ParkingTicket ticket =  parkingLot.vehicleLeaves(teslaModelX);

        System.out.println("total amount " +ticket.getTotalAmount());

        assertEquals(new BigDecimal(37), ticket.getTotalAmount());

    }


    @Test
    public void parkingLotIntegrationHourFixedPriceTest(){

        String now = LocalDateTime.now().plusMinutes(45).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .policyType(PolicyType.HOUR_SPENT_FIXED_AMOUNT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        PricingPolicyData pricingPolicyData = PricingPolicyData
                .builder()
                .hourPrice(new BigDecimal(37))
                .fixedPrice(new BigDecimal(20))
                .build();

        TollParking parkingLot = new TollParking(parkingConfiguration, pricingPolicyData);

        Electric50KW teslaModelX = new Electric50KW();
        parkingLot.vehicleEnters(teslaModelX);

        ParkingTicket ticket = parkingLot.vehicleLeaves(teslaModelX);

        System.out.println("total amount " +ticket.getTotalAmount());

        assertEquals(new BigDecimal(57), ticket.getTotalAmount());


        int countFree = 0;
        List<ParkingSlot> slots = parkingLot.getParkingSlots().get(ParkingSlotType.POWER_SUPPLY_OF_50KW);

        for ( ParkingSlot p : slots){
            if (p.isFree()) countFree++;
        }

        assertEquals(2, countFree);


    }

    @Test(expected = ParkingException.class)
    public void parkingLotFullTest(){

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .policyType(PolicyType.HOUR_SPENT_FIXED_AMOUNT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        PricingPolicyData pricingPolicyData = PricingPolicyData
                .builder()
                .hourPrice(new BigDecimal(37))
                .fixedPrice(new BigDecimal(20))
                .build();

        TollParking parkingLot = new TollParking(parkingConfiguration, pricingPolicyData);

        Electric50KW teslaModelX = new Electric50KW();
        parkingLot.vehicleEnters(teslaModelX);
        Electric50KW teslaModelY = new Electric50KW();
        parkingLot.vehicleEnters(teslaModelY);
        Electric50KW teslaModelZ = new Electric50KW();
        parkingLot.vehicleEnters(teslaModelZ);


    }


    @Test
    public void testParkingConfNotNULL() throws Exception{

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Parking Configuration must not be null!");



        PricingPolicyData pricingPolicyData = PricingPolicyData
                .builder()
                .hourPrice(new BigDecimal(37))
                .fixedPrice(new BigDecimal(20))
                .build();

        TollParking parkingLot = new TollParking(null, pricingPolicyData);


    }


    @Test
    public void testPricingConfigurationNotNULL() throws Exception{

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Pricing Policy Data must not be null!");


        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .policyType(PolicyType.HOUR_SPENT_FIXED_AMOUNT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        TollParking parkingLot = new TollParking(parkingConfiguration, null);



    }


    @Test
    public void parkingLotMultiSlots(){

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_20KW, 2)
                .parkingSlots(ParkingSlotType.STANDARD, 10)
                .policyType(PolicyType.HOUR_SPENT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        for (Map.Entry<ParkingSlotType, Integer> entries  : parkingConfiguration.getParkingSlotsSizes().entrySet()){
            System.out.println("KEY "+ entries.getKey() + " value " + entries.getValue());
        }


        PricingPolicyData pricingPolicyData = PricingPolicyData
                .builder()
                .hourPrice(new BigDecimal(37))
                .build();

        TollParking parkingLot = new TollParking(parkingConfiguration, pricingPolicyData);

        Electric50KW teslaModelX = new Electric50KW();
        Electric20KW teslaModelY = new Electric20KW();
        Car nissanVersa = new Car();
        ParkingTicket ticketTeslaX = parkingLot.vehicleEnters(teslaModelX);
        ParkingTicket ticketTeslaY= parkingLot.vehicleEnters(teslaModelY);
        ParkingTicket ticketNissan = parkingLot.vehicleEnters(nissanVersa);

        assertNotNull(ticketTeslaX);
        assertNotNull(ticketTeslaY);
        assertNotNull(ticketNissan);


        ParkingTicket ticket1 =  parkingLot.vehicleLeaves(teslaModelX);
        ParkingTicket ticket2 =  parkingLot.vehicleLeaves(teslaModelY);
        ParkingTicket ticket3 =  parkingLot.vehicleLeaves(nissanVersa);


        String now = LocalDateTime.now().plusMinutes(45).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        System.out.println("total amount " +ticket1.getTotalAmount());

        assertEquals(new BigDecimal(37), ticket1.getTotalAmount());
        assertEquals(new BigDecimal(37), ticket2.getTotalAmount());
        assertEquals(new BigDecimal(37), ticket3.getTotalAmount());

    }


    @Test
    public void testRandomEntraceAndExits(){

        ParkingConfiguration parkingConfiguration= ParkingConfiguration
                .builder()
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_50KW, 2)
                .parkingSlots(ParkingSlotType.POWER_SUPPLY_OF_20KW, 2)
                .parkingSlots(ParkingSlotType.STANDARD, 10)
                .policyType(PolicyType.HOUR_SPENT)
                .allocationType(ParkingSlotAllocationType.RANDOM)
                .build();

        PricingPolicyData pricingPolicyData = PricingPolicyData
                .builder()
                .hourPrice(new BigDecimal(37))
                .build();

        TollParking parkingLot = new TollParking(parkingConfiguration, pricingPolicyData);

        RandomVehicleBuilder randomVehicleBuilder = new RandomVehicleBuilder()
                .addVehicles(VehicleType.ELECTRIC_20KW, 2)
                .addVehicles(VehicleType.ELECTRIC_50KW, 2)
                .addVehicles(VehicleType.GASOLINE_POWERED, 10);

        List<Vehicle> randomCars = randomVehicleBuilder.createVehicleList();

        for(Vehicle vehicle: randomCars){
            parkingLot.vehicleEnters(vehicle);
        }

        String now = LocalDateTime.now().plusMinutes(45).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        for(Vehicle vehicle: randomCars) {
            ParkingTicket t1 = parkingLot.vehicleLeaves();

            assertEquals(new BigDecimal(37), t1.getTotalAmount());

        }

    }



}
