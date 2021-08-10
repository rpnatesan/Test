package com.bian.bankservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String accountNumber;
    private String transactionType;
    private Float amount;


    @Override
    public String toString() {
        return "TransactionDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                '}';
    }
}
