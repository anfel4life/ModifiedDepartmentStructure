package com.liashenko.departments.services.mainService;


import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public interface MainService {

    ArrayList<EmployeeDataSet> getDepartmentEmployeesList(int departmentId);

    boolean createNewDepartment(String newDepartmentName);

    boolean removeDepartment(int departmentId);

    ArrayList<DepartmentDataSet> getDepartmentsList();

    DepartmentDataSet getDepartmentByName(String departmentName);

    DepartmentDataSet getDepartmentById(int departmentId);

    HashSet<DepartmentDataSet> getEmployeeCountWithType(String employeeType);

    ArrayList<EmployeeDataSet> getEmployeesFromDepartmentByAge(String departmentName, String age);

    EmployeeDataSet getEmployeeById(int employeeId);

    boolean updateEmployee(EmployeeDataSet employeeToUpdate);
    LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> getAllEmployeeView();

    boolean createNewEmployee(String employeeName, int departmentId, String employeeType,
                              String language, String methodology, String employeeAge);

    boolean removeEmployee(int id);
}
