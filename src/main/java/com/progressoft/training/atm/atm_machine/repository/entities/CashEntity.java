package com.progressoft.training.atm.atm_machine.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "CASH")
@NotNull(message = "CashEntity cannot be null")
public class CashEntity {

    @Id
    @NotEmpty(message = "Cash ID cannot be null or empty")
    private String cashID;

    @Column(name = "CASH_NAME", unique = true)
    @NotEmpty(message = "Cash name cannot be null or empty")
    private String cashName;

    @Column(name = "CASH_AMOUNT", unique = true)
    @NotNull(message = "Cash count cannot be null")
    @Positive(message = "Cash count must be positive")
    private BigDecimal cashAmount;

    @Column(name = "CASH_QUANTITY")
    @NotNull(message = "Cash quantity cannot be null")
    @Positive(message = "Cash quantity must be positive")
    private int cashQuantity;
}
