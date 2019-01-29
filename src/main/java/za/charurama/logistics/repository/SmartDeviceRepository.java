package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.SmartDevice;

public interface SmartDeviceRepository extends MongoRepository<SmartDevice,String> {
}
