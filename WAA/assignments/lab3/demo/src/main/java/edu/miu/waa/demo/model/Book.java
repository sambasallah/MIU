package edu.miu.waa.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer isbn;
    private String author;
    private String title;
    private Double price;
}
