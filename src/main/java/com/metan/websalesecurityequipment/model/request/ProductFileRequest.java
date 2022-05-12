package com.metan.websalesecurityequipment.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductFileRequest {
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String shortDesc;
    private String longDesc;
    private String thumbnail;
}
