
parking is Java library for a Toll Parking system

to compile 

`gradle build`

## Usage

### Create a new Toll Parking

To create a new TollParking instance, first you need to create a ParkingConfiguration and
PricingPolicyData
```
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
```

### ParkingConfiguration and PricingPolicyData

ParkingConfiguration is used to setup parking slots, pricing policy type and slot allocation type

PricingPolicyData is used internally to calculate pricing according to pricing policy in use

```
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
              
                
```

### Create vehicles

```
  Electric50KW teslaModelX = new Electric50KW();
  Electric20KW teslaModelY = new Electric20KW();
  Car nissanVersa = new Car();
```
