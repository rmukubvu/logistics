package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.VehicleLocation;

public interface VehicleLocationRepository extends MongoRepository<VehicleLocation,String> {
    Iterable<VehicleLocation> findAllByVehicleIdEquals(String vehicleId);
}
