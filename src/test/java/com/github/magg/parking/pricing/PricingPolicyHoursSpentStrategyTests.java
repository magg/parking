package com.github.magg.parking.pricing;


import com.github.magg.parking.domain.ParkingTicket;
import com.github.magg.parking.domain.pricing.PolicyType;
import com.github.magg.parking.domain.pricing.PricingPolicyCalculationStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyData;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentFixedStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyStrategyFactory;
import com.github.magg.parking.domain.vehicles.Electric50KW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({ PricingPolicyCalculationStrategy.class, PricingPolicyHoursSpentFixedStrategy.class, PricingPolicyHoursSpentStrategy.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*"})
@RunWith(PowerMockRunner.class)
public class PricingPolicyHoursSpentStrategyTests {


    @Test
    public void calculationTest(){



        PricingPolicyCalculationStrategy pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(PolicyType.HOUR_SPENT);

        PricingPolicyData data = PricingPolicyData
                .builder()
                .hourPrice( new BigDecimal(20))
                .build();


        Electric50KW teslaModelX = new Electric50KW();

        ParkingTicket ticket = new ParkingTicket();
        teslaModelX.setTicket(ticket);



        String now = LocalDateTime.now().plusHours(3).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        ParkingTicket result = pricingPolicy.calculateParkingPrice(data, teslaModelX);

        System.out.println(result.getTotalAmount().toString());

        assertEquals(new BigDecimal(60), result.getTotalAmount());

    }


    @Test
    public void calculationTestLessThanOneHour(){



        PricingPolicyCalculationStrategy pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(PolicyType.HOUR_SPENT);

        PricingPolicyData data = PricingPolicyData
                .builder()
                .hourPrice( new BigDecimal(20))
                .build();


        Electric50KW teslaModelX = new Electric50KW();

        ParkingTicket ticket = new ParkingTicket();
        teslaModelX.setTicket(ticket);



        String now = LocalDateTime.now().plusMinutes(30).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        ParkingTicket result = pricingPolicy.calculateParkingPrice(data, teslaModelX);

        System.out.println(result.getTotalAmount().toString());

        assertEquals(new BigDecimal(20), result.getTotalAmount());

    }

    @Test
    public void calculateHoursLess(){
        PricingPolicyCalculationStrategy pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(PolicyType.HOUR_SPENT);


        Electric50KW teslaModelX = new Electric50KW();

        ParkingTicket ticket = new ParkingTicket();
        teslaModelX.setTicket(ticket);

        Duration calculateTime = pricingPolicy.calculateTime(teslaModelX);

        assertNotNull(calculateTime);
        long time = calculateTime.toHours();

        System.out.println("TIME "+ time);

        assertEquals(0, time);


    }

    @Test
    public void calculateHoursMore(){
        PricingPolicyCalculationStrategy pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(PolicyType.HOUR_SPENT);


        Electric50KW teslaModelX = new Electric50KW();

        ParkingTicket ticket = new ParkingTicket();
        teslaModelX.setTicket(ticket);

        String now = LocalDateTime.now().plusHours(3).toString();

        LocalDateTime expected = LocalDateTime.parse(now);

        PowerMockito.spy(LocalDateTime.class);
        when(LocalDateTime.now()).thenReturn(expected);

        Duration calculateTime = pricingPolicy.calculateTime(teslaModelX);

        assertNotNull(calculateTime);
        long time = calculateTime.toHours();

        assertTrue(time > 1);


    }




}
