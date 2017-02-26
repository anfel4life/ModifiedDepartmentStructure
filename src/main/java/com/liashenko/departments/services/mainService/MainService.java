package com.liashenko.departments.services.mainService;


import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;

import java.util.ArrayList;
import java.util.HashMap;

public interface MainService {

    ArrayList<EmployeeDataSet> getDepartmentEmployeesList(int departmentId);

    boolean createNewDepartment(String newDepartmentName);

    boolean removeDepartment(String departmentName);

    ArrayList<DepartmentDataSet> getDepartmentsList();

    DepartmentDataSet getDepartmentByName(String departmentName);

    DepartmentDataSet getDepartmentById(int departmentId);

    ArrayList<String> getEmployeeCountWithType(String employeeType);

    ArrayList<HashMap<String, String>> getEmployeesFromDepartmentByAge(String departmentName, String age);

    EmployeeDataSet getEmployeeById(int employeeId);

    boolean updateEmployee(EmployeeDataSet employeeToUpdate);
    ArrayList<HashMap<String, String>> getAllEmployeeView();

//    int getLastDepartment();
    boolean createNewEmployee(String employeeName, int departmentId, String employeeType,
                              String language, String methodology, String employeeAge);

    boolean removeEmployee(int id);
}
