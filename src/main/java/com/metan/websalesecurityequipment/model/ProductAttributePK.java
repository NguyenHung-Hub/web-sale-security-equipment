package com.metan.websalesecurityequipment.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductAttributePK implements Serializable {
    private String product;
    private String attributeValue;

    public ProductAttributePK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttributePK that = (ProductAttributePK) o;

        if (!Objects.equals(product, that.product)) return false;
        return Objects.equals(attributeValue, that.attributeValue);
    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (attributeValue != null ? attributeValue.hashCode() : 0);
        return result;
    }
}
