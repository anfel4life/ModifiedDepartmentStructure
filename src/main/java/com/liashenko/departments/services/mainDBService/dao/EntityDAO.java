package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.DataAccessLayerException;
import com.liashenko.departments.services.mainDBService.HibernateFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class EntityDAO {
//    protected Session session;

    protected Session session;
    protected Transaction tx;

//    public EntityDAO(Session session) {
//        this.session = session;
//    }


    public EntityDAO() {
        HibernateFactory.buildIfNeeded();
    }
//
//    protected void saveOrUpdate(Object obj) {
//        try {
//            startOperation();
//            session.saveOrUpdate(obj);
//            tx.commit();
//        } catch (HibernateException e) {
//            handleException(e);
//        } finally {
//            HibernateFactory.close(session);
//        }
//    }
//
//    protected void delete(Object obj) {
//        try {
//            startOperation();
//            session.delete(obj);
//            tx.commit();
//        } catch (HibernateException e) {
//            handleException(e);
//        } finally {
//            HibernateFactory.close(session);
//        }
//    }
//
//    protected Object find(Class clazz, Long id) {
//        Object obj = null;
//        try {
//            startOperation();
//            obj = session.load(clazz, id);
//            tx.commit();
//        } catch (HibernateException e) {
//            handleException(e);
//        } finally {
//            HibernateFactory.close(session);
//        }
//        return obj;
//    }
//
//    protected List findAll(Class clazz) {
//        List objects = null;
//        try {
//            startOperation();
//            Query query = session.createQuery("from " + clazz.getName());
//            objects = query.list();
//            tx.commit();
//        } catch (HibernateException e) {
//            handleException(e);
//        } finally {
//            HibernateFactory.close(session);
//        }
//        return objects;
//    }

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
    // realisation of base methods in the inherited DAOs
    //also add Utils for Session creation and DAOFactory
    //add logger where it is necessary
}
