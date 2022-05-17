package com.metan.websalesecurityequipment.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequest {
    private String status;
    private int currentPage;

}
