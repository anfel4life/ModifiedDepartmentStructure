package com.liashenko.departments.services.mainDBService.dao;

import com.liashenko.departments.entities.Developer;
import com.liashenko.departments.entities.Employee;
import com.liashenko.departments.entities.Manager;
import com.liashenko.departments.services.mainDBService.QueryBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EmployeeDAO extends EntityDAO{

    public EmployeeDAO (){}

    public EmployeeDAO(Connection connection) {
        super(connection);
    }

    public Employee getEntity(String query, LinkedHashMap<String, String> paramsMap) throws SQLException {
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    int employeeId = result.getInt(QueryBuilder.EMPLOYEE_COL_ID);
                    String employeeName = result.getString(QueryBuilder.EMPLOYEE_COL_NAME);
                    String employeeType = result.getString(QueryBuilder.EMPLOYEE_COL_TYPE);
                    String employeeLanguage = result.getString(QueryBuilder.EMPLOYEE_COL_LANGUAGE);
                    String employeeMethodology = result.getString(QueryBuilder.EMPLOYEE_COL_METHODOLOGY);
                    short employeeAge = result.getShort(QueryBuilder.EMPLOYEE_COL_AGE);
                    if (employeeType.equals(Manager.MANAGER_EMPLOYEE)) {
                        return new Manager(employeeId, employeeName, employeeAge, employeeMethodology);
                    } else if (employeeType.equals(Developer.DEVELOPER_EMPLOYEE)) {
                        return new Developer(employeeId, employeeName, employeeAge, employeeLanguage);
                    }
                }
            }
            return null;
        });
    }

    public ArrayList<Employee> getEntitiesList(String query, LinkedHashMap<String, String> paramsMap) throws SQLException {
        ArrayList<Employee> resultList = new ArrayList<Employee>();
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    Employee employee = null;
                    int employeeId = result.getInt(QueryBuilder.EMPLOYEE_COL_ID);
                    String employeeName = result.getString(QueryBuilder.EMPLOYEE_COL_NAME);
                    short employeeAge = result.getShort(QueryBuilder.EMPLOYEE_COL_AGE);
                    String employeeType = result.getString(QueryBuilder.EMPLOYEE_COL_TYPE);
                    String employeeSkill = "";

                    switch (employeeType) {
                        case Manager.MANAGER_EMPLOYEE:
                            employeeSkill = result.getString(QueryBuilder.EMPLOYEE_COL_METHODOLOGY);
                            employee = new Manager(employeeId, employeeName, employeeAge, employeeSkill);
                            break;
                        case Developer.DEVELOPER_EMPLOYEE:
                            employeeSkill = result.getString(QueryBuilder.EMPLOYEE_COL_LANGUAGE);
                            employee = new Developer(employeeId, employeeName, employeeAge, employeeSkill);
                            break;
                    }
                    resultList.add(employee);
                }
            }
            return resultList;
        });
    }
}
