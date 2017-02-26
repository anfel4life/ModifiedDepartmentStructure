package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


public class DepartmentDAO extends EntityDAO {

    public DepartmentDAO(Session session) {
        super(session);
    }

    public Integer insertDepartment(String name) throws HibernateException {
        return (Integer) session.save(new DepartmentDataSet(name));
    }

    public void removeDepartment(String name) throws HibernateException {
//        String hql = "DELETE DEPARTMENT WHERE name > :name ";
//        Query query = session.createQuery(hql);
//        query.setParameter("name", name);
//        int result = query.executeUpdate();
    }

    public ArrayList<DepartmentDataSet> getDepartments() {
        Criteria cr = session.createCriteria(DepartmentDataSet.class);
        List departmentList = cr.list();
        return (ArrayList<DepartmentDataSet>) departmentList;
    }

//    public DepartmentDataSet getDepartment(String name) throws HibernateException {
//        Criteria cr = session.createCriteria(DepartmentDataSet.class);
//        List departmentList = cr.add(Restrictions.eq("DEPARTMENT.name", name)).list();
//        return (DepartmentDataSet) session.get(DepartmentDataSet.class, name);
//    }

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


    /*
    public long getUserId(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getId();
    }
    */

//    public UsersDataSet getUserLogin(String login) throws HibernateException {
//        Criteria criteria = session.createCriteria(UsersDataSet.class);
//        return (UsersDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult();
//    }


/*
    public long insertUser(String name) throws HibernateException {
        return (Long) session.save(new UsersDataSet(name));
    }
*/
//    public long insertUser(String login, String password) throws HibernateException {
//        return (Long) session.save(new UsersDataSet(login, password));
//    }
}
