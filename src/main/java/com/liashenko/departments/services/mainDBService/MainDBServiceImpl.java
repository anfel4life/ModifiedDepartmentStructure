package com.liashenko.departments.services.mainDBService;

import com.liashenko.departments.entities.Department;
import com.liashenko.departments.entities.Employee;
import com.liashenko.departments.services.mainDBService.dao.DepartmentDAO;
import com.liashenko.departments.services.mainDBService.dao.EmployeeDAO;
import com.liashenko.departments.services.mainDBService.dao.RootDAO;
import com.liashenko.departments.services.mainDBService.utils.ConnectionUtils;
import com.liashenko.departments.services.mainService.MainService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class MainDBServiceImpl implements MainService {
    private RootDAO rootDao;
    private DepartmentDAO departmentDao;
    private EmployeeDAO employeeDao;

    public MainDBServiceImpl() {
        rootDao = new RootDAO(ConnectionUtils.getMysqlConnection());
        departmentDao = new DepartmentDAO(ConnectionUtils.getMysqlConnection());
        employeeDao = new EmployeeDAO(ConnectionUtils.getMysqlConnection());
    }

    public ArrayList<Employee> getDepartmentEmployeesList(int departmentId) {
        try {
            LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put(QueryBuilder.EMPLOYEE_COL_DEPARTMENT_ID, String.valueOf(departmentId));
            return employeeDao.getEntitiesList(QueryBuilder.getEmployeesListQuery(), paramsMap);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public boolean createNewDepartment(String newDepartmentName) {
        Department department = getDepartmentByName(newDepartmentName);
        if (department != null) {
            return false;
        }

        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.DEPARTMENT_COL_NAME, newDepartmentName);
        try {
            departmentDao.execQuery(QueryBuilder.createDepartmentQuery(), paramsMap);
            return true;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return false;
    }

    public boolean removeDepartment(String departmentName) {
        Department department;
        department = getDepartmentByName(departmentName);
        if (department == null || department.getId() == 0) {
            return false;
        }

        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.DEPARTMENT_COL_NAME, departmentName);
        try {
            departmentDao.execQuery(QueryBuilder.removeDepartmentQuery(), paramsMap);
            return true;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return false;
    }

    public ArrayList<Department> getDepartmentsList() {
        try {
            return departmentDao.getEntitiesList(QueryBuilder.getDepartmentListQuery(), null);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public Department getDepartmentByName(String departmentName) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.DEPARTMENT_COL_NAME, departmentName);
        try {
            return departmentDao.getEntity(QueryBuilder.getDepartmentByNameQuery(), paramsMap);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public Department getDepartmentById(int departmentId) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.DEPARTMENT_COL_ID, String.valueOf(departmentId));
        try {
            return departmentDao.getEntity(QueryBuilder.getDepartmentByIdQuery(), paramsMap);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public ArrayList<String> getEmployeeCountWithType(String employeeType) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_TYPE, employeeType);
        try {
            employeeDao.execQuery(QueryBuilder.dropTempTableForDepartmentsWithMaxCountsQuery(), null);
            employeeDao.execQuery(QueryBuilder.dropTempTableForTopDepartmentQuery(), null);
            employeeDao.execQuery(QueryBuilder.dropTempTableForTopDepartmentsWithIdQuery(), null);

            employeeDao.execQuery(QueryBuilder.createTempTableForDepartmentsWithMaxCountsQuery(), paramsMap);
            employeeDao.execQuery(QueryBuilder.createTempTableForTopDepartmentQuery(), null);
            employeeDao.execQuery(QueryBuilder.createTempTableForTopDepartmentsWithIdQuery(), null);

            return rootDao.getEmployeeCountWithType(QueryBuilder.searchDepartmentWithTopEmployeesQuery(), null);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public ArrayList<HashMap<String, String>> getEmployeesFromDepartmentByAge(String departmentName, String age) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_AGE, age);
        paramsMap.put(QueryBuilder.DEPARTMENT_COL_NAME, departmentName);
        try {
            rootDao.execQuery(QueryBuilder.dropTempTableWithEmployeesFromDepartmentByAge(), null);
            rootDao.execQuery(QueryBuilder.createTempTableWithEmployeesFromDepartmentByAge(), paramsMap);

            return rootDao.getEmployeesFromDepartmentByAge(QueryBuilder.getEmployeesFromDepartmentByAge(), null);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public Employee getEmployeeById(String employeeId) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_ID, String.valueOf(employeeId));
        try {
            return employeeDao.getEntity(QueryBuilder.getEmployeeByIdQuery(), paramsMap);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public boolean updateEmployee(String employeeId, String employeeName, String employeeLanguage,
                                  String employeeMethodology, String employeeeAge) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_NAME, employeeName);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_LANGUAGE, employeeLanguage);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_METHODOLOGY, employeeMethodology);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_AGE, employeeeAge);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_ID, employeeId);
        try {
            employeeDao.execQuery(QueryBuilder.updateEmployeeQuery(), paramsMap);
            return true;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> getAllEmployeeView() {
        try {
            return rootDao.getAllEmployeeView(QueryBuilder.getAllEmployeeViewQuery(), null);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return null;
    }

    public boolean createNewEmployee(String employeeName, int departmentId, String employeeType,
                                     String language, String methodology, String employeeAge) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_NAME, employeeName);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_DEPARTMENT_ID, String.valueOf(departmentId));
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_TYPE, employeeType);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_LANGUAGE, language);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_METHODOLOGY, methodology);
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_AGE, employeeAge);
        try {
            employeeDao.execQuery(QueryBuilder.addNewEmployeeQuery(), paramsMap);
            return true;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return false;
    }

    public boolean removeEmployee(String id) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put(QueryBuilder.EMPLOYEE_COL_ID, id);
        try {
            employeeDao.execQuery(QueryBuilder.removeEmployeeQuery(), paramsMap);
            return true;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Internal database error.");
        }
        return false;
    }
}
