package org.intercorpretail.challenge.retail.business;

import org.intercorpretail.challenge.retail.repository.UserRepository;
import org.intercorpretail.challenge.retail.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id("userId")
                .createdDate(LocalDateTime.now())
                .documentNumber("000830391")
                .documentType(2)
                .name("Test Subject")
                .lastName("Last Name")
                .lastModified(LocalDateTime.now())
                .phoneNumber("992252496")
                .build();
    }

    @Test
    void tesFindUserById_GivenValidInputs_ShouldReturnUserData() {

        String userId = "userId";
        Mono<User> userMono = Mono.just(user);
        Mockito.when(this.userRepository.findById(userId)).thenReturn(userMono);
        Mono<User> actual = this.userService.findUserById(userId);

        Assertions.assertEquals(userMono, actual);
    }
}