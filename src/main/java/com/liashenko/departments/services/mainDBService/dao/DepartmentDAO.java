package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.HibernateFactory;
import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;


public class DepartmentDAO extends EntityDAO {

    public boolean insertDepartment(String name) {
        try {
            startOperation();
            session.save(new DepartmentDataSet(name));
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }

    public boolean removeDepartment(int departmentId) {
        try {
            startOperation();
            session.delete(session.get(DepartmentDataSet.class, departmentId));
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactory.close(session);
        }
        return true;
    }

    public ArrayList<DepartmentDataSet> getDepartments() {//common method
        ArrayList<DepartmentDataSet> departmentList = null;
        try {
            startOperation();
            Criteria cr = session.createCriteria(DepartmentDataSet.class);
            departmentList = (ArrayList<DepartmentDataSet>) cr.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return departmentList;
    }

    public ArrayList<EmployeeDataSet> getEmployees(int departmentId) {
        ArrayList<EmployeeDataSet> employeeList = null;
        try {
            openSession();
            Criteria cr = session.createCriteria(EmployeeDataSet.class);
            employeeList = (ArrayList<EmployeeDataSet>) cr.add(Restrictions
                    .eq("departmentId", departmentId)).list();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return employeeList;
    }

    public DepartmentDataSet getDepartment(String name) {
        DepartmentDataSet department = null;
        try {
            openSession();
            Criteria cr = session.createCriteria(DepartmentDataSet.class);
            department = ((DepartmentDataSet) cr.add(Restrictions.eq("name", name)).uniqueResult());
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return department;
    }
}
