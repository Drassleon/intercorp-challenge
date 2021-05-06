package org.intercorpretail.challenge.retail.controller;

import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.output.ProductService;
import org.intercorpretail.challenge.utils.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private static Product product;

    @BeforeAll
    static void setUp() {
        product = Product.builder()
                .id(123)
                .name("testProduct")
                .price("1.0")
                .build();
    }

    @Test
    void testGetProducts_GivenValidInputs_ShouldReturnProductList() {

        Mockito.when(this.productService.getAll()).thenReturn(Collections.singletonList(product));

        List<Product> actual = this.productController.getProducts();

        Assertions.assertFalse(actual.isEmpty());
        Assertions.assertEquals(product.getId(), actual.get(0).getId());
    }

    @Test
    void testGetProductBySkuId_GivenValidInputs_ShouldReturnSingleProduct() throws EntityNotFoundException {

        String skuId = "skuId";

        Mockito.when(this.productService.getBySkuId(skuId)).thenReturn(product);

        Product actual = this.productController.getProductBySkuId(skuId);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(product.getId(), actual.getId());
    }
}