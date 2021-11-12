package edu.miu.waa.demo.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Bank {

    @Min(1)
    @Id
    private Integer accountNumber;
    @NotNull
    @Length(min = 3)
    private String accountHolder;
    private Collection<Transaction> transactionList = new ArrayList<>();

    public Bank(Integer accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    public double calcBalance(){
        return transactionList.stream().mapToDouble(t -> t.getAmount()).sum();
    }

    @Override
    public String toString() {
        return "Bank{ " +
                "\n  accountNumber=" + accountNumber +
                "\n  accountHolder='" + accountHolder + '\'' +
                "\n  balance='" + calcBalance() + '\'' +
                "\n  transactionList=" + transactionList +" \n }";
    }
}
