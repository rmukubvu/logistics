package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Consignor;

public interface ConsignorRepository extends MongoRepository<Consignor,String>{
}
