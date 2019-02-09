package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.VehicleAllocation;

public interface VehicleAllocationRepository extends MongoRepository<VehicleAllocation,String> {
    Iterable<VehicleAllocation> findAllByDriverIdEquals(String driverId);
    VehicleAllocation findFirstByVehicleIdEqualsAndAllocatedEndDateIsNull(String vehicleId);
    VehicleAllocation findFirstByDriverIdEquals(String driverId);
}
