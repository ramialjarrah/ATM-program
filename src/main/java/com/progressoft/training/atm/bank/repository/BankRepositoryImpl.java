package com.progressoft.training.atm.bank.repository;

import com.progressoft.training.atm.bank.mapper.UserMapper;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import com.progressoft.training.atm.bank.repository.entities.UserEntity;
import com.progressoft.training.atm.util.validators.BeanValidator;
import org.hibernate.Session;

import java.math.BigDecimal;

public class BankRepositoryImpl implements BankRepository {

    private final UserMapper mapper;
    private final Session session;

    public BankRepositoryImpl(UserMapper mapper, Session session) {
        this.session = session;
        this.mapper = mapper;
    }

    @Override
    public UserDomain getUserByPin(String pin) {
            UserEntity userEntity = session.createQuery("FROM UserEntity u WHERE u.pin = :pin",UserEntity.class)
                            .setParameter("pin", pin)
                                    .uniqueResult();
            BeanValidator.validate(userEntity);
            return mapper.userEntityToDomain(userEntity);
    }

    @Override
    public void updateUserBalance(UserDomain userDomain) {
        session.beginTransaction();
        BeanValidator.validate(userDomain);

        UserEntity userEntity = session.createQuery("FROM UserEntity u WHERE u.pin = :pin",UserEntity.class)
                .setParameter("pin", userDomain.getPin())
                .uniqueResult();

        userEntity.setBalance(userDomain.getBalance());
        session.getTransaction().commit();
    }

    @Override
    public BigDecimal getUserBalance(String pin) {
        UserEntity userEntity = session.createQuery("FROM UserEntity u WHERE u.pin = :pin",UserEntity.class)
                .setParameter("pin", pin)
                .uniqueResult();
        BeanValidator.validate(userEntity);
        return userEntity.getBalance();
    }
}
