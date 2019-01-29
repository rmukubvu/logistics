package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.MessagingLog;

public interface MessagingLogsRepository extends MongoRepository<MessagingLog,String> {
}
