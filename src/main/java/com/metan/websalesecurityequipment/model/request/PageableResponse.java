package com.metan.websalesecurityequipment.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse {
    private Object items;

    private int totalPages;

    private int currentPage;
}
