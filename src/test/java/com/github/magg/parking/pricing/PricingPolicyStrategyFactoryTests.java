package com.github.magg.parking.pricing;

import com.github.magg.parking.domain.pricing.PolicyType;
import com.github.magg.parking.domain.pricing.PricingPolicyCalculationStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentFixedStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyHoursSpentStrategy;
import com.github.magg.parking.domain.pricing.PricingPolicyStrategyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PricingPolicyStrategyFactoryTests {

    @Test
    public void createFromFactoryTest_HourSpent(){
        PricingPolicyCalculationStrategy pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(PolicyType.HOUR_SPENT);
        assertNotNull(pricingPolicy);
        assertEquals(PricingPolicyHoursSpentStrategy.class, pricingPolicy.getClass());
    }


    @Test
    public void createFromFactoryTest_HourSpentFixed(){
        PricingPolicyCalculationStrategy pricingPolicy = PricingPolicyStrategyFactory.createPolicyStrategy(PolicyType.HOUR_SPENT_FIXED_AMOUNT);
        assertNotNull(pricingPolicy);
        assertEquals(PricingPolicyHoursSpentFixedStrategy.class, pricingPolicy.getClass());
    }
}
