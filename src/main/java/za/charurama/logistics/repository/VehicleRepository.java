package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle,String> {
    Vehicle findFirstByLicenseIdEquals(String license);
}
