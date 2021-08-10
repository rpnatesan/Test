package com.bian.bankservice.dao.bean;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "last_updated_timestamp")
    private String lastUpdateTimestamp;

    @Column(name = "balance")
    private Float balance;


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", lastUpdateTimestamp='" + lastUpdateTimestamp + '\'' +
                ", balance=" + balance +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        lastUpdateTimestamp = LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateTimestamp = LocalDateTime.now().toString();
    }
}
