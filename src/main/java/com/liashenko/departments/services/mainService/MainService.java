package com.liashenko.departments.services.mainService;

import com.liashenko.departments.entities.Department;
import com.liashenko.departments.entities.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public interface MainService {

    ArrayList<Employee> getDepartmentEmployeesList(int departmentId);

    boolean createNewDepartment(String newDepartmentName);

    boolean removeDepartment(String departmentName);

    ArrayList<Department> getDepartmentsList();

    Department getDepartmentByName(String departmentName);

    Department getDepartmentById(int departmentId);

    ArrayList<String> getEmployeeCountWithType(String employeeType);

    ArrayList<HashMap<String, String>> getEmployeesFromDepartmentByAge(String departmentName, String age);

    Employee getEmployeeById(String employeeId);

    boolean updateEmployee(String employeeId, String employeeName, String employeeLanguage,
                           String employeeMethodology, String employeeeAge);
    ArrayList<HashMap<String, String>> getAllEmployeeView();

//    int getLastDepartment();

    boolean createNewEmployee(String employeeName, int departmentId, String employeeType,
                              String language, String methodology, String employeeAge);

    boolean removeEmployee(String id);
}
