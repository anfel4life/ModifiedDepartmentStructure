package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.HibernateFactory;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;


public class EmployeeDAO extends EntityDAO {

    public EmployeeDataSet getEmployee(int id) {
        EmployeeDataSet employee = null;
        try {
            openSession();
            Criteria cr = session.createCriteria(EmployeeDataSet.class);
            employee = ((EmployeeDataSet) cr.add(Restrictions.eq("id", id)).uniqueResult());
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return employee;
    }

    public boolean insertEmployee(EmployeeDataSet newEmployee) {
        try {
            startOperation();
            session.save(newEmployee);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }

    public boolean updateEmployee(EmployeeDataSet updatedEmployee) {
        try {
            startOperation();
            session.update(updatedEmployee);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }

    public boolean removeEmployee(int id) {
        try {
            startOperation();
            String hql = "delete from EmployeeDataSet where id = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", id);
            int rowCount = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println(">>EmployeeDAO removeEmployee: " + rowCount);
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
