package org.intercorpretail.challenge.retail.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    @JsonProperty("seller_id")
    private Integer sellerId;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("available_colors")
    private String availableColors;
    @JsonProperty("sku_id")
    private String skuId;
    @JsonProperty("video_urls")
    private String videoUrls;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("description1")
    private String description1;
    @JsonProperty("description2")
    private String description2;
    @JsonProperty("creation_date")
    private Object creationDate;
    @JsonProperty("coupons")
    private String coupons;
    @JsonProperty("discount")
    private String discount;
    @JsonProperty("image_urls")
    private String imageUrls;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private String price;
    @JsonProperty("last_update")
    private Object lastUpdate;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Double amount;
}
