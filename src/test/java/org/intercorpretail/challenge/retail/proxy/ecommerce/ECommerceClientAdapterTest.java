package org.intercorpretail.challenge.retail.proxy.ecommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.proxy.ecommerce.response.ProductResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ECommerceClientAdapterTest {

    public static MockWebServer mockBackEnd;

    ObjectMapper objectMapper;

    private ECommerceClientAdapter eCommerceClientAdapter;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        objectMapper = new ObjectMapper();
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        eCommerceClientAdapter = new ECommerceClientAdapter(baseUrl);
    }

    @Test
    void testGetAllRawProducts_GivenValidInputs_ShouldReturnProductResponse() throws JsonProcessingException {
        ProductResponse mockedResponse = ProductResponse.builder()
                .data(Collections.singletonList(Product.builder()
                        .id(123)
                        .name("testProduct")
                        .price("1.0")
                        .build()))
                .build();
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(mockedResponse))
                .addHeader("Content-Type", "application/json"));

        Flux<Product> actual = this.eCommerceClientAdapter.getAllRawProducts();
        List<Product> actualValue = actual.collectList().block();
        Assertions.assertNotNull(actualValue);
        Assertions.assertFalse(actualValue.isEmpty());
        Assertions.assertEquals(123, actualValue.get(0).getId());
    }

    @Test
    void testGetAllRawProducts_GivenValidInputs_ButResponseCodeIs4XX_ShouldReturnEmptyProductResponse() throws JsonProcessingException {
        ProductResponse mockedResponse = ProductResponse.builder()
                .data(Collections.singletonList(Product.builder()
                        .build()))
                .build();
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(400)
                .setBody(objectMapper.writeValueAsString(mockedResponse))
                .addHeader("Content-Type", "application/json"));

        Flux<Product> actual = this.eCommerceClientAdapter.getAllRawProducts();
        List<Product> actualValue = actual.collectList().block();
        Assertions.assertNotNull(actualValue);
        Assertions.assertTrue(actualValue.isEmpty());
    }

    @Test
    void testGetAllRawProducts_GivenValidInputs_ButResponseCodeIs500_ShouldThrowMonoError() throws JsonProcessingException {
        ProductResponse mockedResponse = ProductResponse.builder()
                .data(Collections.singletonList(Product.builder()
                        .build()))
                .build();
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody(objectMapper.writeValueAsString(mockedResponse))
                .addHeader("Content-Type", "application/json"));
        Exception exception = Assertions.assertThrows(Exception.class, () -> this.eCommerceClientAdapter.getAllRawProducts().blockFirst());
        Assertions.assertNotNull(exception);
    }
}