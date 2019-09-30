package com.github.magg.parking.config;

import com.github.magg.parking.domain.ParkingSlotType;
import com.github.magg.parking.domain.allocation.ParkingSlotAllocationType;
import com.github.magg.parking.domain.pricing.PolicyType;
import com.github.magg.parking.exception.ParkingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.magg.parking.exception.ParkingException.PARKING_ALLOCATION_NULL;
import static com.github.magg.parking.exception.ParkingException.PARKING_POLICY_NULL;
import static com.github.magg.parking.exception.ParkingException.PARKING_SLOTS_INVALID;

public class ParkingConfiguration {

    private Map<ParkingSlotType, Integer> parkingSlotsSizes;
    private PolicyType policyType;
    private ParkingSlotAllocationType parkingSlotAllocationType;

    private ParkingConfiguration(Map<ParkingSlotType, Integer> parkingSlotsSizes, PolicyType policyType, ParkingSlotAllocationType parkingSlotAllocationType) {
        this.parkingSlotsSizes = parkingSlotsSizes;
        this.policyType = policyType;
        this.parkingSlotAllocationType = parkingSlotAllocationType;
    }

    public static ParkingConfigurationBuilder builder() {
        return new ParkingConfigurationBuilder();
    }

    /***
     *
     * @return a Map of ParkingSlotType and it's integer count
     */
    public Map<ParkingSlotType, Integer> getParkingSlotsSizes() {
        return this.parkingSlotsSizes;
    }


    /***
     *  Returns the size of the ParkingSlot for a given type
     * @param type the ParkingSlotType
     * @return int the size of the ParkingSlot list
     */
    public int getParkingSlotsSizes(ParkingSlotType type) {
        return this.parkingSlotsSizes.get(type);
    }

    public PolicyType getPolicyType() {
        return this.policyType;
    }

    public ParkingSlotAllocationType getParkingSlotAllocationType() {
        return parkingSlotAllocationType;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ParkingConfiguration)) return false;
        final ParkingConfiguration other = (ParkingConfiguration) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$parkingSlotsSizes = this.getParkingSlotsSizes();
        final Object other$parkingSlotsSizes = other.getParkingSlotsSizes();
        if (!Objects.equals(this$parkingSlotsSizes, other$parkingSlotsSizes))
            return false;
        final Object this$policyType = this.getPolicyType();
        final Object other$policyType = other.getPolicyType();
        if (!Objects.equals(this$policyType, other$policyType))
            return false;
        return true;
    }

    private boolean canEqual(final Object other) {
        return other instanceof ParkingConfiguration;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $parkingSlotsSizes = this.getParkingSlotsSizes();
        result = result * PRIME + ($parkingSlotsSizes == null ? 43 : $parkingSlotsSizes.hashCode());
        final Object $policyType = this.getPolicyType();
        result = result * PRIME + ($policyType == null ? 43 : $policyType.hashCode());
        return result;
    }

    public String toString() {
        return "ParkingConfiguration(parkingSlotsSizes=" + this.getParkingSlotsSizes() + ", policyType=" + this.getPolicyType() + ")";
    }

    public ParkingConfigurationBuilder toBuilder() {
        return new ParkingConfigurationBuilder();
    }

    public static class ParkingConfigurationBuilder {
        private Map<ParkingSlotType, Integer> parkingSlotsSizes;
        private PolicyType policyType;
        private ParkingSlotAllocationType parkingSlotAllocationType;

        ParkingConfigurationBuilder() {
            this.parkingSlotsSizes = new HashMap<>();
        }

        /***
         * Add a number of ParkingSlots for a given ParkingSlotType, it might throw ParkingException
         *
         * @param type the ParkingSlotType needed
         * @param count the size of the ParkingSlot list
         * @return ParkingConfiguration.ParkingConfigurationBuilder instance
         */

        public ParkingConfiguration.ParkingConfigurationBuilder parkingSlots(ParkingSlotType type, int count){
            if (count < 0) throw new ParkingException(PARKING_SLOTS_INVALID);
            this.parkingSlotsSizes.put(type, count);
            return this;
        }

        public ParkingConfiguration.ParkingConfigurationBuilder policyType(PolicyType policyType) {
            this.policyType = policyType;
            return this;
        }

        public ParkingConfiguration.ParkingConfigurationBuilder allocationType(ParkingSlotAllocationType parkingSlotAllocationType){
            this.parkingSlotAllocationType = parkingSlotAllocationType;
            return this;
        }

        /***
         *  Builder method to create ParkingConfiguration, it might throw ParkingException
         *
         *
         * @return ParkingConfiguration built using the Builder pattern
         */

        public ParkingConfiguration build() {

            if (policyType == null) throw new ParkingException(PARKING_POLICY_NULL);

            if (parkingSlotAllocationType == null) throw new ParkingException(PARKING_ALLOCATION_NULL);


            return new ParkingConfiguration(parkingSlotsSizes, policyType, parkingSlotAllocationType);
        }

        public String toString() {
            return "ParkingConfiguration.ParkingConfigurationBuilder(parkingSlotsSizes=" + this.parkingSlotsSizes + ", policyType=" + this.policyType + ", allocationType=" + parkingSlotAllocationType + ")";
        }
    }
}
