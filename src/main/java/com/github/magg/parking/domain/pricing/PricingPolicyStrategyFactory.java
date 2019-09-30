package com.github.magg.parking.domain.pricing;

public class PricingPolicyStrategyFactory {

    /***
     * Factory class that creates Pricing Policy Strategies
     *
     * @param policyType The Pricing Policy Type
     * @return PricingPolicyCalculationStrategy
     */
    public static PricingPolicyCalculationStrategy createPolicyStrategy( PolicyType policyType){

        switch(policyType){
            case HOUR_SPENT:
                return new PricingPolicyHoursSpentStrategy();
            case HOUR_SPENT_FIXED_AMOUNT:
                return new PricingPolicyHoursSpentFixedStrategy();
            default:
                throw new IllegalArgumentException("Unrecognized PolicyType " + policyType);
        }
    }
}
