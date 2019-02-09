package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.SystemAdmin;

public interface SystemAdminsRepository extends MongoRepository<SystemAdmin,String> {
    SystemAdmin findFirstByUserIdEquals(String userId);
}
