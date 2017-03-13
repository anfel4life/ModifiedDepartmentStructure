package com.liashenko.departments.userInterface.utils;

import com.liashenko.departments.services.database.entities.DepartmentDataSet;
import com.liashenko.departments.services.database.entities.EmployeeDataSet;
import com.liashenko.departments.services.nodes.NodeGeneratorUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static String departmentList(ArrayList<DepartmentDataSet> departmentList) {
        StringBuilder sb = new StringBuilder();
        if (departmentList != null && !departmentList.isEmpty()) {
            for (DepartmentDataSet department : departmentList) {
                sb.append(department.getName());
                sb.append("\n");
            }
        } else {
            sb.append("No departments");
        }
        return sb.toString();
    }

    public static String allEmployeeView(LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> employeesList) {
        StringBuilder sb = new StringBuilder();
        if (employeesList != null && !employeesList.isEmpty()) {
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_DEP_NAME));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_NAME));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_TYPE));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_AGE));
            sb.append("|\n");
            for (Map.Entry<DepartmentDataSet, ArrayList<EmployeeDataSet>> entry : employeesList.entrySet()) {
                String departmentName = entry.getKey().getName();
                ArrayList<EmployeeDataSet> employees = new ArrayList<EmployeeDataSet>();
                employees = entry.getValue();
                if (employees != null && !employees.isEmpty()) {
                    for (EmployeeDataSet employee : employees) {
                        sb.append("|-").append(symbolsBetweenColumns(departmentName));
                        sb.append("|-").append(symbolsBetweenColumns(employee.getName()));
                        sb.append("|-").append(symbolsBetweenColumns(employee.getType()));
                        sb.append("|-").append(symbolsBetweenColumns(employee.getAge()));
                        sb.append("|\n");
                    }
                } else {
                    sb.append("|-").append(symbolsBetweenColumns(departmentName));
                    sb.append("|-").append(symbolsBetweenColumns(""));
                    sb.append("|-").append(symbolsBetweenColumns(""));
                    sb.append("|-").append(symbolsBetweenColumns(""));
                    sb.append("|\n");
                }
            }
        } else {
            sb.append("No data");
        }
        return sb.toString();
    }

    public static String getEmployeeList(ArrayList<EmployeeDataSet> employeesList) {
        StringBuilder sb = new StringBuilder();
        if (employeesList != null && !employeesList.isEmpty()) {
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_NAME));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_TYPE));
            sb.append("|-").append(symbolsBetweenColumns(ALL_EMPLOYEE_VIEW_HEAD_EMPL_AGE));
            sb.append("|\n");
            for (EmployeeDataSet employee : employeesList) {
                sb.append("|-").append(symbolsBetweenColumns(employee.getName()));
                sb.append("|-").append(symbolsBetweenColumns(employee.getType()));
                sb.append("|-").append(symbolsBetweenColumns(employee.getAge()));
                sb.append("|\n");
            }
        } else {
            sb.append("No data");
        }
        return sb.toString();
    }

    public static String employeesList(ArrayList<EmployeeDataSet> employeesList) {
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

            for (EmployeeDataSet employee : employeesList) {
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

    public static String topDepartmentsList(HashSet<DepartmentDataSet> departmentsSet, String employeeType) {
        StringBuilder list = new StringBuilder();
        if (departmentsSet != null && !departmentsSet.isEmpty()) {
            list.append("The department(s) with the largest number of employees (" + employeeType + "):\n");
            for (DepartmentDataSet department : departmentsSet) {
                list.append(department.getName()).append("\n");
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

    public static String getEmployeeInfo(EmployeeDataSet employee) {
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

        if (employee.getType().equals(NodeGeneratorUtil.MANAGER_NODE_TYPE)) {
            info.append(whiteSpaces(TABLE_HEAD_METHODOLOGY)).append(":")
                    .append(employee.getMethodology())
                    .append("\n");
        } else if (employee.getType().equals(NodeGeneratorUtil.DEVELOPER_NODE_TYPE)) {
            info.append(whiteSpaces(TABLE_HEAD_LANGUAGE)).append(":")
                    .append(employee.getLanguage())
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
