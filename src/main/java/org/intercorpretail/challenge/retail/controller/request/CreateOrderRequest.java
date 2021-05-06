package org.intercorpretail.challenge.retail.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static org.intercorpretail.challenge.utils.constants.RegexConstants.UUID_V4_REGEX;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderRequest {

    @NotNull(message = "User ID cannot be null")
    @Pattern(regexp = UUID_V4_REGEX, message = "User ID must be a UUID")
    String userId;

    @NotNull(message = "Product List cannot be null!")
    @NotEmpty(message = "Product list cannot not be empty!")
    List<CreateProductRequest> productListRequest;
}
