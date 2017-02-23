package com.liashenko.departments.userInterface;


public interface CommandsController {
    String createNewDepartment(String departmentName);

    String openDepartment(String departmentName);

    String removeDepartment(String departmentName);

    String departmentsList();

    String createEmployee(String employeeName, String employeeType, String language, String methodology,
                          String employeeAge);

    String createEmployee(String department, String employeeName, String employeeType, String language,
                          String methodology, String employeeAge);

    String updateEmployee(String employeeId, String employeeName, String language, String methodology, String employeeAge);

    String removeEmployee(String employeeIdStr);

    String openEmployee(String employeeIdStr);

    String help();

    String all();

    String searchEmployeeInDepartmentByAge(String department, String age);

    String searchDepartmentWithTopEmployees(String employeeType);
}
