package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Consignee;

public interface ConsigneeRepository extends MongoRepository<Consignee,String>{
    Consignee findDistinctFirstByIdEquals(String consigneeId);
}
