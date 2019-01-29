package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Driver;

public interface DriverRepository extends MongoRepository<Driver,String> {
}
