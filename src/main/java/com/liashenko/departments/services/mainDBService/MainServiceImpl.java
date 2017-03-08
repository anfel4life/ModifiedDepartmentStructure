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
import java.util.*;


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
        configuration.setProperty("hibernate.connection.autocommit", "true");
        configuration.setProperty("useSSL", "false");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
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
            System.out.println(">>MainServiceImpl() createNewDepartment " + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeDepartment(int departmentId) {
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDao = new DepartmentDAO(session);
            departmentDao.removeDepartment(departmentId);
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() removeDepartment " + e);
            return false;
        }
        return true;
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
    public LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> getAllEmployeeView() {
        LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> result =
                new LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>>();
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDAO = new DepartmentDAO(session);
            ArrayList<DepartmentDataSet> departments = departmentDAO.getDepartments();
            if (departments != null && !departments.isEmpty()){
                for (DepartmentDataSet department : departments) {
                    ArrayList<EmployeeDataSet> employees = new ArrayList<EmployeeDataSet>();
                    employees = departmentDAO.getEmployees(department.getId());
                    result.put(department, employees);
                }
            }
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
        }
        return result;
    }

    @Override
    public HashSet<DepartmentDataSet>  getEmployeeCountWithType(String employeeType) {
        HashSet<DepartmentDataSet> result = new HashSet<DepartmentDataSet>();
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDAO = new DepartmentDAO(session);
            TreeMap<Integer, DepartmentDataSet> sortedByCountMap =
                    new TreeMap<Integer, DepartmentDataSet>(Collections.reverseOrder());
            ArrayList<DepartmentDataSet> departments = departmentDAO.getDepartments();
            if (departments != null && !departments.isEmpty()){
                for (DepartmentDataSet department : departments) {
                    ArrayList<EmployeeDataSet> employeesByType = new ArrayList<EmployeeDataSet>();
                    ArrayList<EmployeeDataSet> employees = departmentDAO.getEmployees(department.getId());
                    if (employees != null && !employees.isEmpty()){
                        Integer employeesCount = 0;
                        for (EmployeeDataSet employee : employees){
                            if (employee.getType().equals(employeeType)){
                                employeesCount = employeesCount + 1;
                                employeesByType.add(employee);
                            }
                        }
                        sortedByCountMap.put(employeesCount, department);
                    }
                }
            }
            session.close();

            if (sortedByCountMap != null && !sortedByCountMap.isEmpty()){

                int maxCount = sortedByCountMap.firstKey();
                for (Map.Entry<Integer, DepartmentDataSet> entry : sortedByCountMap.entrySet()) {
                   int count = entry.getKey();
                   if (count >= maxCount){
                        result.add(entry.getValue());
                   } else {
                       break;
                   }
                }
            }
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
        }
        return result;
    }

    @Override
    public ArrayList<EmployeeDataSet> getEmployeesFromDepartmentByAge(String departmentName, String age) {
        ArrayList<EmployeeDataSet> resultList = new ArrayList<EmployeeDataSet>();
        try {
            Session session = sessionFactory.openSession();
            DepartmentDAO departmentDAO = new DepartmentDAO(session);
            DepartmentDataSet department = departmentDAO.getDepartment(departmentName);
            ArrayList<EmployeeDataSet> employeesList = departmentDAO.getEmployees(department.getId());
            if (employeesList != null && !employeesList.isEmpty()){
                for (EmployeeDataSet employee : employeesList){
                    if (employee.getAge().equals(age)){
                        resultList.add(employee);
                    }
                }
            }
            session.close();
        } catch (HibernateException e) {
            System.out.println(">>MainServiceImpl() getEmployeeById " + e);
        }
        return resultList;
    }
}
