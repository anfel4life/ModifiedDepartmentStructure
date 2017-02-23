package com.liashenko.departments.services.mainDBService.dao;

import com.liashenko.departments.services.mainDBService.QueryBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RootDAO extends EntityDAO {

    public RootDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<HashMap<String, String>> getAllEmployeeView(String query, LinkedHashMap<String, String> paramsMap)
            throws SQLException {
        ArrayList<HashMap<String, String>> resultMap = new ArrayList<HashMap<String, String>>();
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    HashMap<String, String> rowMap = new HashMap<String, String>();
                    String departmentName = result.getString(QueryBuilder.DEPARTMENT_TABLE_NAME + "." +
                            QueryBuilder.DEPARTMENT_COL_NAME);
                    String employeeName = result.getString(QueryBuilder.EMPLOYEE_TABLE_NAME + "." +
                            QueryBuilder.EMPLOYEE_COL_NAME);
                    String employeeType = result.getString(QueryBuilder.EMPLOYEE_TABLE_NAME + "." +
                            QueryBuilder.EMPLOYEE_COL_TYPE);
                    String employeeAge = result.getString(QueryBuilder.EMPLOYEE_TABLE_NAME + "." +
                            QueryBuilder.EMPLOYEE_COL_AGE);
                    rowMap.put(QueryBuilder.DEPARTMENT_TABLE_NAME + "." + QueryBuilder.DEPARTMENT_COL_NAME, departmentName);
                    rowMap.put(QueryBuilder.EMPLOYEE_TABLE_NAME + "." + QueryBuilder.EMPLOYEE_COL_NAME, employeeName);
                    rowMap.put(QueryBuilder.EMPLOYEE_TABLE_NAME + "." + QueryBuilder.EMPLOYEE_COL_TYPE, employeeType);
                    rowMap.put(QueryBuilder.EMPLOYEE_TABLE_NAME + "." + QueryBuilder.EMPLOYEE_COL_AGE, employeeAge);
                    resultMap.add(rowMap);
                }
            }
            return resultMap;
        });
    }

    public ArrayList<String> getEmployeeCountWithType(String query, LinkedHashMap<String, String> paramsMap)
            throws SQLException {
        ArrayList<String> resultList = new ArrayList<String>();
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    String departmentName = result.getString(QueryBuilder.DEPARTMENT_COL_NAME);
                    resultList.add(departmentName);
                }
            }
            return resultList;
        });
    }


    public ArrayList<HashMap<String, String>> getEmployeesFromDepartmentByAge(String query,
                                                                              LinkedHashMap<String, String> paramsMap)
            throws SQLException {
        ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    HashMap<String, String> employeesMap = new HashMap<String, String>();
                    String employeeName = result.getString(QueryBuilder.EMPLOYEE_COL_NAME);
                    String employeeType = result.getString(QueryBuilder.EMPLOYEE_COL_TYPE);
                    String departmentName = result.getString(QueryBuilder.EMPLOYEE_COL_DEPARTMENT_NAME);
                    employeesMap.put(QueryBuilder.EMPLOYEE_COL_NAME, employeeName);
                    employeesMap.put(QueryBuilder.EMPLOYEE_COL_TYPE, employeeType);
                    employeesMap.put(QueryBuilder.EMPLOYEE_COL_DEPARTMENT_NAME, departmentName);
                    resultList.add(employeesMap);
                }
            }
            return resultList;
        });
    }

}
