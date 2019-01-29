package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.SmartDeviceAllocation;

public interface SmartDeviceAllocationRepository extends MongoRepository<SmartDeviceAllocation,String> {
    SmartDeviceAllocation findSmartDeviceAllocationByVehicleIdEquals(String vehicleId);
    SmartDeviceAllocation findSmartDeviceAllocationByDeviceIdEquals(String deviceId);
}
