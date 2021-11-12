package edu.miu.waa.shoppingcart.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Size(min = 2,max=5)
    @NotNull
    private String number;
    @Size(min = 2,max=20)
    @NotNull
    private String name;
    private Double price;
}
