package com.progressoft.training.atm.atm_machine.repository;

import com.progressoft.training.atm.atm_machine.mapper.CashMapper;
import com.progressoft.training.atm.atm_machine.repository.entities.CashEntity;
import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import com.progressoft.training.atm.util.validators.BeanValidator;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AtmRepositoryImpl implements AtmRepository {

    private final CashMapper mapper;

    private final Session session;

    public AtmRepositoryImpl(CashMapper mapper, Session session) {
        this.mapper = mapper;
        this.session = session;
    }

    @Override
    public void updateCategory(CashDomain cashDomain) {
        session.beginTransaction();
        CashEntity cashEntity = session.find(CashEntity.class, cashDomain.getCashID());
        BeanValidator.validate(cashEntity);
        cashEntity.setCashQuantity(cashDomain.getCashQuantity());
        session.merge(cashEntity);
        session.getTransaction().commit();
    }

    @Override
    public List<CashDomain> listCashCategories() {
        List<CashEntity> cashEntities = session.createQuery("from CashEntity", CashEntity.class).getResultList();
        BeanValidator.validate(cashEntities);
        cashEntities.sort(Comparator.comparing(CashEntity::getCashAmount));
        Collections.reverse(cashEntities);
        return mapper.ToDomainList(cashEntities);
    }


    @Override
    public BigDecimal getActualAmount() {
        BigDecimal actualAmount = BigDecimal.ZERO;
        List<CashDomain> cashDomains = listCashCategories();
        for (CashDomain cashDomain : cashDomains) {
            actualAmount = actualAmount.add(cashDomain.getCashAmount().multiply(BigDecimal.valueOf(cashDomain.getCashQuantity())));
        }
        return actualAmount;
    }

    @Override
    public CashDomain getCashById(String cashId){
        CashEntity cashEntity = session.find(CashEntity.class, cashId);
        BeanValidator.validate(cashEntity);
        return mapper.ToDomain(cashEntity);
    }
}
