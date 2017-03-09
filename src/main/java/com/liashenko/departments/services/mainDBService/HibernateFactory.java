package com.liashenko.departments.services.mainDBService;


import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateFactory {

    private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String CONNECTION_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/STAFF";
    private static final String CONNECTION_USERNAME = "root";
    private static final String CONNECTION_PASSWORD = "1";
    private static final String IS_CONNECTION_AUTOCOMMIT = "true";
    private static final String IS_SSL = "false";
    private static final String IS_HIBERNATE_SHOW_SQL = "false";
    //private static final String hibernate_hbm2ddl_auto = "create"; //
    private static final String HIBERNATE_HBM2DDL_AUTO  = "update"; //update

    private static SessionFactory sessionFactory;
    private static Logger log = LogManager.getLogger(HibernateFactory.class);

    private static Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(EmployeeDataSet.class);
        configuration.addAnnotatedClass(DepartmentDataSet.class);

        configuration.setProperty("hibernate.dialect", HIBERNATE_DIALECT);
        configuration.setProperty("hibernate.connection.driver_class", CONNECTION_DRIVER_CLASS);
        configuration.setProperty("hibernate.connection.url", CONNECTION_URL);
        configuration.setProperty("hibernate.connection.username", CONNECTION_USERNAME);
        configuration.setProperty("hibernate.connection.password", CONNECTION_PASSWORD);
        configuration.setProperty("hibernate.connection.autocommit", IS_CONNECTION_AUTOCOMMIT);
        configuration.setProperty("useSSL", IS_SSL);
        configuration.setProperty("hibernate.show_sql", IS_HIBERNATE_SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
        return sessionFactory;
    }

    /**
     * Constructs a new Singleton SessionFactory
     *
     * @return
     * @throws HibernateException
     */
    public static SessionFactory buildSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    /**
     * Builds a SessionFactory, if it hasn't been already.
     */
    public static SessionFactory buildIfNeeded() throws DataAccessLayerException {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            return configureSessionFactory();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() throws HibernateException {
        buildIfNeeded();
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                log.error("Couldn't close SessionFactory", ignored);
            }
        }
    }

    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                log.error("Couldn't close Session", ignored);
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            log.error("Couldn't rollback Transaction", ignored);
        }
    }
}
