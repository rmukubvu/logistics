package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.ConsigneeContactDetails;

public interface ConsigneeContactsRepository extends MongoRepository<ConsigneeContactDetails,String>{
    Iterable<ConsigneeContactDetails> findAllConsigneeContactDetailsByConsigneeIdEquals(String consigneeId);
}
