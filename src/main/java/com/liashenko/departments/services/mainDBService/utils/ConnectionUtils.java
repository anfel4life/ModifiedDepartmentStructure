package com.liashenko.departments.services.mainDBService.utils;

import com.liashenko.departments.services.mainDBService.QueryBuilder;
import com.liashenko.departments.services.mainDBService.dao.RootDAO;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionUtils {

    public static Connection getMysqlConnection() {
        try {
//            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("STAFF?").               //db name
                    append("user=root&").           //login
                    append("password=1&").          //password
                    append("useSSL=false");         //use SSL
//            System.out.println("URL: " + url + "\n");
            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException e) {
            System.out.println("Connection to database wasn't successful.");
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.print("Connection to database wasn't successful.");
            System.out.println("Check connection params.");
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.print("Connection to database wasn't successful. ");
            System.out.println("Check com.mysql.jdbc.Driver");
//            e.printStackTrace();
        }
        return null;
    }

    public static boolean isTablesExist() {
        try {
            RootDAO rootDao = new RootDAO(getMysqlConnection());
            rootDao.execQuery(QueryBuilder.createEmployeeTableQuery(), null);
            rootDao.execQuery(QueryBuilder.createDepartmentTableQuery(), null);
            return true;
        } catch (SQLException ignore) {
        }
        return false;
    }

//    public void printConnectInfo() {
//        try {
//            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
//            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
//            System.out.println("Driver: " + connection.getMetaData().getDriverName());
//            System.out.println("Autocommit: " + connection.getAutoCommit());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
