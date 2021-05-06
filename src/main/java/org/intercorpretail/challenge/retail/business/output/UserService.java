package org.intercorpretail.challenge.retail.business.output;

import org.intercorpretail.challenge.retail.repository.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findUserById(String userId);
}
