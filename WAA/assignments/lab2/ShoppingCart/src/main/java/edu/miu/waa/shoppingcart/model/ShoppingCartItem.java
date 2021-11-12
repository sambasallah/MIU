package edu.miu.waa.shoppingcart.model;

import lombok.*;

@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ShoppingCartItem {
    private int quantity;
    private Product product;

    public double getTotal() {
        return quantity * (product == null ? 0.0 : product.getPrice());
    }
}
