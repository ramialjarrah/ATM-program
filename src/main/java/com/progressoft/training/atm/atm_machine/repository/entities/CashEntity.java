package com.progressoft.training.atm.atm_machine.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CashEntity {

    @Id
    private UUID cashID;

    @Column(name = "CASH_NAME", unique = true)
    @NonNull
    private String cashName;

    @Column(name = "CASH_COUNT", unique = true)
    @NonNull
    private BigDecimal cashCount;

    @Column(name = "CASH_QUANTITY")
    private int cashQuantity;
}
