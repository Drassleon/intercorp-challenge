package org.intercorpretail.challenge.retail.business.output;

import org.intercorpretail.challenge.retail.business.domain.Product;

import java.util.List;

public interface OrderProductsService {

    void saveProductsForOrder(List<Product> products, String orderId);
}
