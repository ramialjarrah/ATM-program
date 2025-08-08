package com.progressoft.training.atm.atm_machine.service.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;




@AllArgsConstructor
@Setter
@Getter
public class CashDomain {
    private
    UUID cashID;
    private
    String cashName;
    private
    BigDecimal cashCount;
    private
    int cashQuantity;
}
