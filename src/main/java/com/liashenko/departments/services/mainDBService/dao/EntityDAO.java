package com.liashenko.departments.services.mainDBService.dao;


import com.liashenko.departments.services.mainDBService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public abstract class EntityDAO {

    public Executor executor;

    public EntityDAO (){}

    public EntityDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void execQuery(String query, LinkedHashMap<String, String> paramsMap) throws SQLException {
        executor.prepStatementExec(query, paramsMap);
    }

    public int getSQLFuncResult(String query, LinkedHashMap<String, String> paramsMap) throws SQLException {
        int[] resultInt = new int[1];
        return executor.prepStatementExecQuery(query, paramsMap, result -> {
            if (result != null) {
                result.next();
                resultInt[0] = result.getInt(1);
            }
            return resultInt[0];
        });
    }
}
