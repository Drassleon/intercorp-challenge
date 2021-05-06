package org.intercorpretail.challenge.retail.business;

import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.input.ECommerceClientPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ECommerceClientPort eCommerceClientPort;

    Flux<Product> input;

    @BeforeEach
    public void init() {
        input = Flux.just(Product.builder()
                .skuId("skuId")
                .discount("1.0")
                .id(123)
                .name("testProduct")
                .price("1.0")
                .build());
    }

    @Test
    void testGetAll_GivenValidParameters_ShouldReturnProductList() {

        Mockito.when(this.eCommerceClientPort.getAllRawProducts()).thenReturn(input);
        List<Product> actual = this.productService.getAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(123, actual.get(0).getId());
    }

    @Test
    void testGetBySkuId_GivenValidParameters_ShouldReturnSingleProduct() {
        Mockito.when(this.eCommerceClientPort.getAllRawProducts()).thenReturn(input);
        Product actual = this.productService.getBySkuId("skuId");
        Assertions.assertEquals(123, actual.getId());
        Assertions.assertEquals("1.0", actual.getDiscount());

    }
}