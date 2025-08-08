package com.progressoft.training.atm.bank.repository;

import com.progressoft.training.atm.bank.mapper.UserEntityDomainMapper;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.bank.repository.entities.UserEntity;
import org.hibernate.Session;

import java.math.BigDecimal;

import java.util.List;

public class BankRepositoryImpl implements BankRepository {

    UserEntityDomainMapper mapper;
    Session session;

    public BankRepositoryImpl(UserEntityDomainMapper mapper, Session session) {
        this.session = session;
        this.mapper = mapper;
    }

    @Override
    public UserDomain getUserByPin(String pin) {
        try {
            session.beginTransaction();
            if (pin == null) {
                throw new ValidationException("pin is null!");
            }
            UserEntity userEntity = session.find(UserEntity.class, pin);
            if (userEntity == null) {
                return null;
            }
            session.getTransaction().commit();
            return mapper.userEntityToDomain(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public UserDomain getUserByUsername(String username) {
        try {
            if (username == null) {
                throw new ValidationException("username is null!");
            }
            session.beginTransaction();
            UserEntity userEntity = session.find(UserEntity.class, username);
            if (userEntity == null) {
                return null;
            }
            session.getTransaction().commit();
            return mapper.userEntityToDomain(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void updateUserBalance(UserDomain userDomain) {
        if (userDomain.getPin() == null || userDomain.getBalance() == null) {
            throw new ValidationException("balance and pin cannot be null!");
        }
        session.beginTransaction();
        UserEntity userEntity = session.find(UserEntity.class, userDomain.getPin());
        userEntity.setBalance(userDomain.getBalance());
        session.getTransaction().commit();
    }

    public List<UserDomain> getAllUsers() {
        List<UserEntity> userEntities = session.createQuery("from UserEntity", UserEntity.class).getResultList();
        return mapper.userEntityToDomainList(userEntities);
    }

    public BigDecimal getUserBalance(String pin) {
        if (pin == null) {
            throw new ValidationException("pin is null!");
        }
        UserEntity userEntity = session.find(UserEntity.class, pin);
        if (userEntity == null) {
            return null;
        }
        return userEntity.getBalance();
    }
}
