package org.intercorpretail.challenge.retail.business.output;

import org.intercorpretail.challenge.retail.business.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getBySkuId(String skuId);

}
