package edu.miu.waa.demo.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String description;
    private Double amount;
    private LocalDate date;

    public Transaction(String description, Double amount){
        this.description = description;
        this.amount = amount;
        date = LocalDate.now();
    }

    @Override
    public String toString() {
        return "\n        Transaction{" +
                "  description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date + "}";
    }
}