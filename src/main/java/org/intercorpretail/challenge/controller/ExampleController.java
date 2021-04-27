package org.intercorpretail.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intercorpretail.challenge.domain.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/example")
public class ExampleController {

    private WebClient client = WebClient.builder()
            .baseUrl("http://ecommerce-rest-api.herokuapp.com")
            .defaultHeaders(header -> header.setBasicAuth("shashwat", "shashwat"))
            .build();


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Mono<String> get() {
        return client.get().uri("/products").retrieve().bodyToMono(String.class);
    }
}
