package com.progressoft.training.atm.atm_machine.service.request;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;

import java.util.Map;

public record DepositRequest(String pin, Map<CashDomain,Integer> cashDomains) {
}
