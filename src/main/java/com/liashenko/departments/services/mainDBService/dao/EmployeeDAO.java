package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.HibernateFactory;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;


public class EmployeeDAO extends EntityDAO {

    public EmployeeDataSet getEntity(int entityId) {
        EmployeeDataSet entity = null;
        try {
            openSession();
            Criteria cr = session.createCriteria(EmployeeDataSet.class);
            entity = ((EmployeeDataSet) cr.add(Restrictions.eq("id", entityId)).uniqueResult());
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return entity;
    }

    public boolean updateEntity(EmployeeDataSet entityToUpdate) {
        try {
            startOperation();
            session.update(entityToUpdate);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }

    public boolean removeEntity(int entityId) {
        try {
            startOperation();
            String hql = "delete from EmployeeDataSet where id = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", entityId);
            int rowCount = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println(">>EmployeeDAO removeEntity: " + rowCount);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }
}
