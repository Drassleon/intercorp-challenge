package org.intercorpretail.challenge.retail.repository;

import org.intercorpretail.challenge.retail.repository.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

}
