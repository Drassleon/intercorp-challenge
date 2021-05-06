package org.intercorpretail.challenge.retail.business;

import org.intercorpretail.challenge.retail.business.output.UserService;
import org.intercorpretail.challenge.retail.repository.UserRepository;
import org.intercorpretail.challenge.retail.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findUserById(String userId) {
        return this.userRepository.findById(userId);
    }
}
