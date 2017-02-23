package com.liashenko.departments.entities;


import com.liashenko.departments.userInterface.CommandsHolderUtils;

import java.util.ArrayList;

public abstract class Node {
    public static String ROOT_NODE_TYPE = "root";
    public static String DEPARTMENT_NODE_TYPE = "department";
    public static String EMPLOYEE_NODE_TYPE = "employee";

    public abstract int getNodeId();

    public abstract String getNodeType();

    public ArrayList<String> getNodeCommands(String nodeType) {
        return CommandsHolderUtils.getCommands(nodeType);
    }
}
