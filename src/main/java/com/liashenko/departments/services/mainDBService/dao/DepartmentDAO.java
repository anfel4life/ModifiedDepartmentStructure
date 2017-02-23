package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.entities.Department;
import com.liashenko.departments.services.mainDBService.QueryBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DepartmentDAO extends EntityDAO {

    public DepartmentDAO(Connection connection) {
        super(connection);
    }

    public Department getEntity(String query, LinkedHashMap<String, String> paramsMap) throws SQLException {
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    int id = result.getInt(QueryBuilder.DEPARTMENT_COL_ID);
                    String departmentName = result.getString(QueryBuilder.DEPARTMENT_COL_NAME);
                    return new Department(id, departmentName);
                }
            }
            return null;
        });
    }

    public ArrayList<Department> getEntitiesList(String query, LinkedHashMap<String, String> paramsMap) throws SQLException {
        ArrayList<Department> resultList = new ArrayList<Department>();
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                while (result.next()) {
                    int id = result.getInt(QueryBuilder.DEPARTMENT_COL_ID);
                    String departmentName = result.getString(QueryBuilder.DEPARTMENT_COL_NAME);
                    Department department = new Department(id, departmentName);
                    resultList.add(department);
                }
            }
            return resultList;
        });
    }

}

