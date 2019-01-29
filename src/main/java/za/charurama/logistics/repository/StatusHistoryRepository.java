package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Driver;
import za.charurama.logistics.models.StatusHistory;

public interface StatusHistoryRepository extends MongoRepository<StatusHistory,String> {
    Iterable<StatusHistory> findStatusHistoryByWayBillNumberEquals(long waybillNumber);
}
