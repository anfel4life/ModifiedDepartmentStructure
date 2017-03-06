package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends EntityDAO {

    public DepartmentDAO(Session session) {
        super(session);
    }

    public void insertDepartment(String name) throws HibernateException {
        session.beginTransaction();
        session.save(new DepartmentDataSet(name));
        session.getTransaction().commit();
    }

    public void removeDepartment(int departmentId) throws HibernateException {
        session.beginTransaction();
        session.delete(session.get(DepartmentDataSet.class, departmentId));
        session.getTransaction().commit();
    }

    public ArrayList<DepartmentDataSet> getDepartments() {
        Criteria cr = session.createCriteria(DepartmentDataSet.class);
        List departmentList = cr.list();
        return (ArrayList<DepartmentDataSet>) departmentList;
    }

    public ArrayList<EmployeeDataSet> getEmployees(int departmentId) {
        Criteria cr = session.createCriteria(EmployeeDataSet.class);
        ArrayList<EmployeeDataSet> employeesList =  (ArrayList<EmployeeDataSet>)
                cr.add(Restrictions.eq("departmentId", departmentId)).list();
        return employeesList;
    }

    public DepartmentDataSet getDepartment(String name) throws HibernateException {
        Criteria cr = session.createCriteria(DepartmentDataSet.class);
        return ((DepartmentDataSet) cr.add(Restrictions.eq("name", name)).uniqueResult());
    }
}
