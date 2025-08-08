package com.progressoft.training.atm.bank.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDomain {
    private
    String pin;
    private
    String username;
    private
    BigDecimal balance;
}
