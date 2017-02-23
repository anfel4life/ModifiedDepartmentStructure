package com.liashenko.departments.services.mainDBService.utils;


import com.liashenko.departments.entities.Department;
import com.liashenko.departments.entities.Developer;
import com.liashenko.departments.entities.Employee;
import com.liashenko.departments.entities.Manager;
import com.liashenko.departments.services.mainDBService.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class StringConstructorUtils {
    private static final int SYMBOLS_IN_CELL = 25;
    private static final int SYMBOLS_IN_FILE = 10;
    private static final String TABLE_HEAD_ID = "ID";
    private static final String TABLE_HEAD_TYPE = "Type";
    private static final String TABLE_HEAD_NAME = "Name";
    private static final String TABLE_HEAD_AGE = "Age";

    private static final String ALL_EMPLOYEE_VIEW_HEAD_EMPL_NAME = "Employee";
    private static final String ALL_EMPLOYEE_VIEW_HEAD_DEP_NAME = "Department";
    private static final String ALL_EMPLOYEE_VIEW_HEAD_EMPL_TYPE = "Type";
    private static final String ALL_EMPLOYEE_VIEW_HEAD_EMPL_AGE = "Age";

    private static final String TABLE_HEAD_METHODOLOGY = "Methodology";
    private static final String TABLE_HEAD_LANGUAGE = "Language";

    public static String departmentList(ArrayList<Department> departmentList) {
        StringBuilder sb = new StringBuilder();
        if (departmentList != null && !departmentList.isEmpty()) {
            for (Department department : departmentList) {
                sb.append(department.getName());
                sb.append("\n");
            }
        } else {
            sb.append("No departments");
        }
        return sb.toString();
    }

    public static String allEmployeeView(ArrayList<HashMap<String, String>> employeesList) {
        StringBuilder sb = new StringBuilder();
        if (employeesList != null && !employeesList.isEmpty()) {
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_NAME));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_DEP_NAME));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_TYPE));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_AGE));
            sb.append("|\n");
            for (HashMap<String, String> rowMap : employeesList) {
                for (String rowCell : QueryBuilder.ALL_EMPLOYEE_VIEW_COLUMNS_ARR) {
                    sb.append("|-").append(symbolsBetweenColumns(rowMap.get(rowCell)));
                }
                sb.append("|\n");
            }
        } else {
            sb.append("No data");
        }
        return sb.toString();
    }

    public static String getEmployeeList(ArrayList<HashMap<String, String>> employeesList) {
        StringBuilder sb = new StringBuilder();
        if (employeesList != null && !employeesList.isEmpty()) {
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_NAME));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_TYPE));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_DEP_NAME));
            sb.append("|\n");
            for (HashMap<String, String> employeeMap : employeesList) {
                sb.append("|-").append(symbolsBetweenColumns(employeeMap.get(QueryBuilder.EMPLOYEE_COL_NAME)));
                sb.append("|-").append(symbolsBetweenColumns(employeeMap.get(QueryBuilder.EMPLOYEE_COL_TYPE)));
                sb.append("|-").append(symbolsBetweenColumns(employeeMap.get(QueryBuilder.EMPLOYEE_COL_DEPARTMENT_NAME)));
                sb.append("|\n");
            }
        } else {
            sb.append("No data");
        }
        return sb.toString();
    }

    public static String employeesList(ArrayList<Employee> employeesList) {
        StringBuilder list = new StringBuilder();
        if (employeesList != null && !employeesList.isEmpty()) {
            list.append("|-")
                    .append(symbolsBetweenColumns(TABLE_HEAD_ID))
                    .append("|-")
                    .append(symbolsBetweenColumns(TABLE_HEAD_TYPE))
                    .append("|-")
                    .append(symbolsBetweenColumns(TABLE_HEAD_NAME))
                    .append("|-")
                    .append(symbolsBetweenColumns(TABLE_HEAD_AGE))
                    .append("|\n");

            for (Employee employee : employeesList) {
                if (employee != null) {
                    list.append("|-")
                            .append(symbolsBetweenColumns(String.valueOf(employee.getId())))
                            .append("|-")
                            .append(symbolsBetweenColumns(employee.getType()))
                            .append("|-")
                            .append(symbolsBetweenColumns(employee.getName()))
                            .append("|-")
                            .append(symbolsBetweenColumns(String.valueOf(employee.getAge())))
                            .append("|\n");
                }
            }
        } else {
            list.append("No employees");
        }
        return list.toString();
    }

    public static String topDepartmentsList(ArrayList<String> departmentsList, String employeeType) {
        StringBuilder list = new StringBuilder();
        if (departmentsList != null && !departmentsList.isEmpty()) {
            list.append("The department(s) with the largest number of employees (" + employeeType + "):\n");
            for (String departmentName : departmentsList) {
                list.append(departmentName).append("\n");
            }
        } else {
            list.append("No departments with employees (" + employeeType + ")");
        }
        return list.toString();
    }

    private static String whiteSpaces(String value) {
        int symbolsLeft = SYMBOLS_IN_FILE - value.length();
        StringBuilder whiteSpaces = new StringBuilder(value);
        for (int i = 0; i < symbolsLeft; i++) {
            whiteSpaces.append(" ");
        }
        return whiteSpaces.toString();
    }

    public static String getEmployeeInfo(Employee employee) {
        StringBuilder info = new StringBuilder();
        info.append("Employee ").append(employee.getName()).append(":\n");

        info.append(whiteSpaces(TABLE_HEAD_ID)).append(":")
                .append(String.valueOf(employee.getId()))
                .append("\n");
        info.append(whiteSpaces(TABLE_HEAD_TYPE)).append(":")
                .append(employee.getType())
                .append("\n");
        info.append(whiteSpaces(TABLE_HEAD_AGE)).append(":")
                .append(String.valueOf(employee.getAge()))
                .append("\n");

        if (employee.getType().equals(Manager.MANAGER_EMPLOYEE)) {
            Manager manager = (Manager) employee;
            info.append(whiteSpaces(TABLE_HEAD_METHODOLOGY)).append(":")
                    .append(manager.getMethodology())
                    .append("\n");
        } else if (employee.getType().equals(Developer.DEVELOPER_EMPLOYEE)) {
            Developer developer = (Developer) employee;
            info.append(whiteSpaces(TABLE_HEAD_LANGUAGE)).append(":")
                    .append(developer.getLanguage())
                    .append("\n");
        }
        return info.toString();
    }

    private static String symbolsBetweenColumns(String value) {
        int symbolsLeft = SYMBOLS_IN_CELL - value.length();
        StringBuilder whiteSpaces = new StringBuilder(value);
        for (int i = 0; i < symbolsLeft; i++) {
            whiteSpaces.append("-");
        }
        return whiteSpaces.toString();
    }
}
