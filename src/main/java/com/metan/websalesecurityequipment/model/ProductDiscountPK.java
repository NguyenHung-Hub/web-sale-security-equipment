package com.metan.websalesecurityequipment.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductDiscountPK implements Serializable {
    private String product;
    private String discount;

    public ProductDiscountPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDiscountPK that = (ProductDiscountPK) o;

        if (!Objects.equals(product, that.product)) return false;
        return Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }
}
