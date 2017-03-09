package com.liashenko.departments.services.mainDBService.dao;

import com.liashenko.departments.services.mainDBService.DataAccessLayerException;
import com.liashenko.departments.services.mainDBService.HibernateFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class EntityDAO <T> {

    protected Session session;
    protected Transaction tx;

    public EntityDAO() {
        HibernateFactory.buildIfNeeded();
    }

    abstract boolean removeEntity (int entityId);

    public boolean insertEntity (T entity) {
        try {
            startOperation();
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        HibernateFactory.rollback(tx);
        throw new DataAccessLayerException(e);
    }

    protected void startOperation() throws HibernateException {
        session = HibernateFactory.openSession();
        tx = session.beginTransaction();
    }

    protected void openSession() throws HibernateException {
        session = HibernateFactory.openSession();
    }
    //create interfaces for DAOs and use this abstract class to provide
    //add logger where it is necessary
}
