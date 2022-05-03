package com.metan.websalesecurityequipment.model.request;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductRequestPageable {
    private List<Integer> categoryIds;
    private List<Integer> brandIds;
    private List<Double> ratings;

    private Double minPrice;
    private Double maxPrice;
    private int page;
}
