package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Country;

public interface CountryRepository extends MongoRepository<Country,String> {
}
