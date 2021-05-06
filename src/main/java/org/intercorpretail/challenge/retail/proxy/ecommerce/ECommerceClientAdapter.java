package org.intercorpretail.challenge.retail.proxy.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.intercorpretail.challenge.retail.business.input.ECommerceClientPort;
import org.intercorpretail.challenge.retail.proxy.ecommerce.response.ProductResponse;
import org.intercorpretail.challenge.retail.business.domain.Product;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Component
@Slf4j
public class ECommerceClientAdapter implements ECommerceClientPort {

    private final WebClient client;

    public ECommerceClientAdapter() {
        log.info("Starting web server with React Core Web Client");
        this.client = WebClient.builder()
                .baseUrl("http://ecommerce-rest-api.herokuapp.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.setBasicAuth("shashwat", "shashwat"))
                .build();
    }

    public ECommerceClientAdapter(String baseUrl) {
        log.info("Starting web server with React Core Web Client");
        this.client = WebClient.builder().baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    @Override
    public Flux<Product> getAllRawProducts() {
        Mono<ProductResponse> rawResponse = client.get().uri("/products").exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode()
                    .equals(HttpStatus.OK)) {
                log.info("Retrieved product list successfully!");
                return clientResponse.bodyToMono(ProductResponse.class);
            } else if (clientResponse.statusCode()
                    .is4xxClientError()) {
                return Mono.just(ProductResponse.builder().data(Collections.emptyList()).build());
            } else {
                log.info("An unknown error happened while calling E-Commerce API with status code {}", clientResponse.statusCode());
                return clientResponse.createException()
                        .flatMap(Mono::error);
            }
        });
        return rawResponse.flatMapMany(productResponse -> Flux.fromIterable(productResponse.getData()));
    }
}
