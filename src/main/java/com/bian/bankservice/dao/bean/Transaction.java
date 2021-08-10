package com.bian.bankservice.dao.bean;

import com.bian.bankservice.constants.TransactionTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "transaction_timestamp")
    private String transactionTimeStamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionTypeEnum transactionType;

    @Column(name = "amount")
    private Float amount;


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionTimeStamp='" + transactionTimeStamp + '\'' +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        transactionTimeStamp = LocalDateTime.now().toString();
    }
}
