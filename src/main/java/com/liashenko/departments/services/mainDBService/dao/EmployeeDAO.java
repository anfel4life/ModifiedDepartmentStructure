package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends EntityDAO {
//    private Session session;

    public EmployeeDAO(Session session) {
        super(session);
//        this.session = session;
    }

//    public EmployeeDataSet getEmployee(String id) throws HibernateException {
//        return (EmployeeDataSet) session.get(EmployeeDataSet.class, id);
//    }

    public EmployeeDataSet getEmployee(int id) {
        Criteria cr = session.createCriteria(EmployeeDataSet.class);
        return ((EmployeeDataSet) cr.add(Restrictions.eq("id", id)).uniqueResult());
    }

    public void insertEmployee(EmployeeDataSet newEmployee) throws HibernateException {
        session.beginTransaction();
        session.save(newEmployee);
        session.getTransaction().commit();
    }

    public void updateEmployee(EmployeeDataSet updatedEmployee) throws HibernateException {
        session.beginTransaction();
        session.update(updatedEmployee);
        session.getTransaction().commit();
    }

    public void removeEmployee(int id) throws HibernateException {
        session.beginTransaction();
        String hql = "delete from EmployeeDataSet where id = :id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println(">>EmployeeDAO removeEmployee: " + rowCount);
    }

    public ArrayList<EmployeeDataSet> getEmployees() {
        Criteria cr = session.createCriteria(EmployeeDataSet.class);
        List employeeList = cr.list();
        return (ArrayList<EmployeeDataSet>) employeeList;
    }
}
