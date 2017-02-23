package com.liashenko.departments.services.mainDBService.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void prepStatementExec(String update, LinkedHashMap<String, String> paramsMap) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(update);
        if (paramsMap != null && !paramsMap.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, String> p : paramsMap.entrySet()) {
                stmt.setString(++i, p.getValue());
            }
        }

        try {
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    public <T> T prepStatementExecQuery(String query, LinkedHashMap<String, String> paramsMap,
                                        ResultHandler<T> handler) throws SQLException {
        T value = null;
        ResultSet result = null;

        if (connection == null) {
            return null;
        }

        PreparedStatement stmt = connection.prepareStatement(query);
        int i = 0;
        if (paramsMap != null && !paramsMap.isEmpty()) {
            for (Map.Entry<String, String> p : paramsMap.entrySet()) {
                stmt.setString(++i, p.getValue());
            }
        }

        try {
            result = stmt.executeQuery();
            value = handler.handle(result);
        } finally {
            if (result != null) {
                result.close();
            }
            stmt.close();
        }
        return value;
    }
}
