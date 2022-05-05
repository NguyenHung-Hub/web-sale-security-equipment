package com.metan.websalesecurityequipment.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewRequest {
    private String productId;
    private int page;
}
