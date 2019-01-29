package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.AutoNumber;

public interface AutoNumberGeneratorRepository extends MongoRepository<AutoNumber,String> {
    AutoNumber findFirstBySequenceNameEquals(String sequenceName);
}
