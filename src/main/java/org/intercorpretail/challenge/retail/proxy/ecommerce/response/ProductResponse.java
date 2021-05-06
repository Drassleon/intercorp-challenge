package org.intercorpretail.challenge.retail.proxy.ecommerce.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.intercorpretail.challenge.retail.business.domain.Product;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {

    @JsonProperty("data")
    List<Product> data;
}
