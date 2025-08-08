package com.progressoft.training.atm.atm_machine.repository;

import com.progressoft.training.atm.atm_machine.mapper.CashEntityDomainMapper;
import com.progressoft.training.atm.atm_machine.repository.entities.CashEntity;
import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import org.hibernate.Session;
import java.math.BigDecimal;
import java.util.*;

public class AtmRepositoryImpl implements AtmRepository {

    CashEntityDomainMapper mapper;
    Session session;

    public AtmRepositoryImpl(CashEntityDomainMapper mapper, Session session) {
        this.mapper = mapper;
        this.session = session;
    }
    @Override
    public void saveCategory(CashDomain cashDomain) {
        session.beginTransaction();
        CashEntity cashEntity = new CashEntity(cashDomain.getCashID(), cashDomain.getCashName(), cashDomain.getCashCount(), cashDomain.getCashQuantity());
        session.persist(cashEntity);
        session.getTransaction().commit();
    }

    @Override
    public void updateCategory(CashDomain cashDomain) {
        session.beginTransaction();
        CashEntity cashEntity = session.find(CashEntity.class, cashDomain.getCashID());
        cashEntity.setCashQuantity(cashDomain.getCashQuantity());
        session.merge(cashEntity);
        session.getTransaction().commit();
    }

    @Override
    public List<CashDomain> retrieveCashes() {
        List<CashEntity> cashEntities = session.createQuery("from CashEntity", CashEntity.class).getResultList();
        return mapper.ToDomainList(cashEntities);
    }

    @Override
    public List<CashDomain> sortCashCategory() {
        List<CashDomain> cashDomains = retrieveCashes();
        cashDomains.sort(Comparator.comparing(CashDomain::getCashCount));
        Collections.reverse(cashDomains);
        return cashDomains;
    }

    @Override
    public BigDecimal getActualAmount() {
        BigDecimal actualAmount = BigDecimal.ZERO;
        List<CashDomain> cashDomains = retrieveCashes();
        for (CashDomain cashDomain : cashDomains) {
            actualAmount = actualAmount.add(cashDomain.getCashCount().multiply(BigDecimal.valueOf(cashDomain.getCashQuantity())));
        }
        return actualAmount;
    }
}
