package com.liashenko.departments.services.mainDBService;


import com.liashenko.departments.services.mainDBService.dao.DepartmentDAO;
import com.liashenko.departments.services.mainDBService.dao.EmployeeDAO;
import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import com.liashenko.departments.services.mainService.MainService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainServiceImpl implements MainService {
    private static final String hibernate_show_sql = "false"; //at the time when is working on set "false"
    //private static final String hibernate_hbm2ddl_auto = "create"; //
    private static final String hibernate_hbm2ddl_auto = "update"; //update

    private final SessionFactory sessionFactory;

    public MainServiceImpl() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(EmployeeDataSet.class);
        configuration.addAnnotatedClass(DepartmentDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/STAFF");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "1");
        configuration.setProperty("useSSL", "false");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

//public EmployeeDataSet getEmployee(int id) throws DBException {
//    try {
//        Session session = sessionFactory.openSession();
//        DepartmentDAO dao = new DepartmentDAO(session);
//        EmployeeDataSet dataSet = dao.getUserLogin(login);
//        session.close();
//        return dataSet;
//    } catch (HibernateException e) {
//        throw new DBException(e);
//    }
//}

//    public DepartmentDataSet getDepartment(int id) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            DepartmentDAO departmentDao = new DepartmentDAO(session);
//            DepartmentDataSet dataSet = departmentDao.getDepartment(id);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public ArrayList<EmployeeDataSet> getDepartmentEmployeesList(int departmentId) {
        ArrayList<EmployeeDataSet> employeesList = null;
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDao = new DepartmentDAO(session);
            employeesList = departmentDao.getEmployees(departmentId);
            session.close();
        } catch (HibernateException e) {
//            throw new DBException(e);
            System.out.println(">>MainServiceImpl() getDepartmentEmployeesList " + e);
            return employeesList;
        }
        return employeesList;
    }

    @Override
    public boolean createNewDepartment(String newDepartmentName) {
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDao = new DepartmentDAO(session);
            departmentDao.insertDepartment(newDepartmentName);
            session.close();
        } catch (HibernateException e) {
//            throw new DBException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeDepartment(String departmentName) {
        return false;
    }

    @Override
    public ArrayList<DepartmentDataSet> getDepartmentsList() {
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDao = new DepartmentDAO(session);
            ArrayList<DepartmentDataSet> departmentList = departmentDao.getDepartments();
            session.close();
            return departmentList;
        } catch (HibernateException e) {
//            throw new DBException(e);
            return null;
        }
    }

    @Override
    public DepartmentDataSet getDepartmentByName(String departmentName) {
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDao = new DepartmentDAO(session);
            DepartmentDataSet department = departmentDao.getDepartment(departmentName);
            session.close();
            return department;
        } catch (HibernateException e) {
//            throw new DBException(e);
            System.out.println(">>MainServiceImpl() getDepartmentByName " + e);
            return null;
        }
    }

    @Override
    public DepartmentDataSet getDepartmentById(int departmentId) {
        return null;
    }

    @Override
    public EmployeeDataSet getEmployeeById(int employeeId) {
        EmployeeDataSet employee;
        try {
            Session session = sessionFactory.openSession();
            EmployeeDAO employeeDao = new EmployeeDAO(session);
            employee = employeeDao.getEmployee(employeeId);
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
//            throw new DBException(e);
            return null;
        }
        return employee;
    }

    @Override
    public boolean updateEmployee(EmployeeDataSet newEmployee) {
        try {
            Session session = sessionFactory.openSession();
            EmployeeDAO employeeDao = new EmployeeDAO(session);
            employeeDao.updateEmployee(newEmployee);
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
//            throw new DBException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeEmployee(int id) {
        try {
            Session session = sessionFactory.openSession();
            EmployeeDAO employeeDao = new EmployeeDAO(session);
            employeeDao.removeEmployee(id);
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean createNewEmployee(String employeeName, int departmentId, String employeeType, String language,
                                     String methodology, String employeeAge) {
        try {
            Session session = sessionFactory.openSession();
            EmployeeDAO employeeDao = new EmployeeDAO(session);
            EmployeeDataSet newEmployee = new EmployeeDataSet(employeeName, employeeType, employeeAge, departmentId,
                    methodology, language);
            employeeDao.insertEmployee(newEmployee);
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<HashMap<String, String>> getAllEmployeeView() {
        return null;
    }

    @Override
    public ArrayList<String> getEmployeeCountWithType(String employeeType) {
        return null;
    }

    @Override
    public ArrayList<HashMap<String, String>> getEmployeesFromDepartmentByAge(String departmentName, String age) {
        return null;
    }
}
