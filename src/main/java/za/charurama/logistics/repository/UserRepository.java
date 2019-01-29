package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.User;

public interface UserRepository extends MongoRepository<User,String> {
    User findDistinctByEmailAddressEquals(String email);
}
