package org.intercorpretail.challenge.retail.business.input;

import org.intercorpretail.challenge.retail.business.domain.Product;
import reactor.core.publisher.Flux;

public interface ECommerceClientPort {

    Flux<Product> getAllRawProducts();
}
