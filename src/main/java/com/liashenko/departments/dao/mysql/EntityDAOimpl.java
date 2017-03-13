package com.liashenko.departments.dao.mysql;

import com.liashenko.departments.services.database.DataAccessLayerException;
import com.liashenko.departments.services.database.HibernateFactoryUtil;
import com.liashenko.departments.dao.EntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class EntityDAOimpl<T> implements EntityDAO<T> {

    protected Session session;
    protected Transaction tx;

    protected EntityDAOimpl() {
        HibernateFactoryUtil.buildIfNeeded();
    }

    public boolean insertEntity(T entity) {
        try {
            startOperation();
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactoryUtil.close(session);
        }
        return true;
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        HibernateFactoryUtil.rollback(tx);
        throw new DataAccessLayerException(e);
    }

    protected void startOperation() throws HibernateException {
        session = HibernateFactoryUtil.openSession();
        tx = session.beginTransaction();
    }

    protected void openSession() throws HibernateException {
        session = HibernateFactoryUtil.openSession();
    }
}
