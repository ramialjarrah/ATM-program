package com.progressoft.training.atm.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public HibernateUtil() {}

    public static Session getSessionFactory() {
        try{
            if(sessionFactory == null){
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }

        } catch (Exception e){
            throw new RuntimeException("Connection Failed! ",e);
        }

        return sessionFactory.getCurrentSession();
    }
}
