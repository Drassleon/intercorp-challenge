package org.intercorpretail.challenge.retail.business;

import lombok.extern.slf4j.Slf4j;
import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.input.ECommerceClientPort;
import org.intercorpretail.challenge.retail.business.output.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ECommerceClientPort eCommerceClientPort;

    @Autowired
    public ProductServiceImpl(ECommerceClientPort eCommerceClientPort) {
        this.eCommerceClientPort = eCommerceClientPort;
    }


    @Override
    public List<Product> getAll() {
        log.info("Retrieving all products from external API");
        return this.eCommerceClientPort.getAllRawProducts()
                .collectList()
                .block();
    }

    @Override
    public Product getBySkuId(String skuId) {
        log.info("Searching for product with sku: {}", skuId);
        return this.eCommerceClientPort.getAllRawProducts()
                .filter(product -> skuId.equals(product.getSkuId()))
                .blockFirst();
    }
}
