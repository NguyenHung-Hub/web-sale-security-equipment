package com.metan.websalesecurityequipment.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemPK implements Serializable {
    private String product;
    private long cart;

    public CartItemPK() {
    }

    public CartItemPK(String product, long cart) {
        this.product = product;
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemPK that = (CartItemPK) o;

        if (cart != that.cart) return false;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (int) (cart ^ (cart >>> 32));
        return result;
    }
}
