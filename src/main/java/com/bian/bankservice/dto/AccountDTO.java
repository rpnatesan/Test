package com.bian.bankservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String accountNumber;
    private Date lastUpdateTimestamp;
    private Float balance;

    @Override
    public String toString() {
        return "AccountDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", lastUpdateTimestamp=" + lastUpdateTimestamp +
                ", balance=" + balance +
                '}';
    }
}
