package org.intercorpretail.challenge.domain;

import java.util.List;

public interface GetProductBySkuIdService {
    public List<Product> getBySkuId(String skuId);
}
