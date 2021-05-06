package org.intercorpretail.challenge.retail.controller;

import lombok.extern.slf4j.Slf4j;
import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.output.ProductService;
import org.intercorpretail.challenge.utils.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
@Slf4j
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return this.productService.getAll();
    }

    @GetMapping("/sku/{skuId}")
    public Product getProductBySkuId(@PathVariable("skuId") String skuId) throws EntityNotFoundException {
        return Optional.ofNullable(this.productService.getBySkuId(skuId)).orElseThrow(() -> new EntityNotFoundException(Product.class, skuId));
    }

}
