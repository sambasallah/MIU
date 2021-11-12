package edu.miu.waa.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @NotNull
    @Min(1)
    private Integer isbn;
    @Length(min = 3)
    private String author;
    @Length(min = 3)
    private String title;
    @NotNull
    private Double price;
}
