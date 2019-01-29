package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.ClearingStatus;

public interface ClearingStatusRepository extends MongoRepository<ClearingStatus,String> {
}
