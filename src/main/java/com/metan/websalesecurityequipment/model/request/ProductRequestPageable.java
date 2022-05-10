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
    private int rating;
    private Double minPrice;
    private Double maxPrice;
    private int page;
    private String columnName;
}
