package com.liashenko.departments.userInterface;


import com.liashenko.departments.services.nodesService.NodeGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class CommandsHolderUtils implements Serializable {
    public static final String EXIT_COM = "type \"exit\" to quit from the application";
    public static final String OPEN_DEPARTMENT_COM = "type \"open -d department_name\" to watch employees of this " +
            "department";
    public static final String OPEN_EMPLOYEE_COM = "type \"open -e employee_id\" to watch employee details";
    public static final String REMOVE_DEPARTMENT_COM = "type \"rm -d department_name\" to remove department";
    public static final String REMOVE_EMPLOYEE_COM = "type \"rm -e employee -id\" to remove employee";
    public static final String UPDATE_EMPLOYEE_COM = "type \"update -e employee_id -n employee_name -t -a age " +
            "(type \"-m\" for manager, \"-d\" for developer)\" \n      \"-l\" (developers only) \"-m\" " +
            "(managers only) \" to update employee information";
    public static final String CREATE_EMPLOYEE_COM = "type \"create -e -n employee_name -t (type \"m\" for manager, " +
            "\"d\" for developer)\" \n -a age -l language (developers only) -m methodology (managers only) \" to create new employee";
    public static final String CREATE_DEPARTMENT_COM = "type \"create -d department_name\" to create new department";
    public static final String CREATE_EMPLOYEE_IN_DEPARTMENT_COM = "type \"create -e -dn department_name " +
            "-n employee_name -t (type \"m\" for manager, \"d\" for developer)\n -a age -l language (developers only) " +
            "-m methodology (managers only)\" to create new employee in specific department";
    public static final String DEPARTMENT_COM = "type \"departments\" to return to the list of departments";
    public static final String HELP_COM = "type \"help\" for command list";

    public static final String ALL_COM = "type \"all\" to see all info about staff structure";
    public static final String SEARCH_EMPLOYEE_IN_DEPARTMENT_BY_AGE_COM = "type \"search -e -a age_to_search -d department\"" +
            " to search employee by age in specified department";
    public static final String MAX_EMPLOYEES_IN_DEPARTMENT_COM = "type \"top -d -t type_of_employee (type \"m\" for manager, " +
            "\"d\" for developer)\" to see department " +
            "with the largest number of employees";

    private static final HashMap<String, ArrayList<String>> commandPermissionsMap = new HashMap<String, ArrayList<String>>();
    private static final ArrayList<String> rootCommands = new ArrayList<String>();
    private static final ArrayList<String> departmentCommands = new ArrayList<String>();
    private static final ArrayList<String> employeeCommands = new ArrayList<String>();

    static {
        commandPermissionsMap.put(NodeGenerator.ROOT_NODE_TYPE, rootCommands);
        commandPermissionsMap.put(NodeGenerator.DEPARTMENT_NODE_TYPE, departmentCommands);
        commandPermissionsMap.put(NodeGenerator.EMPLOYEE_NODE_TYPE, employeeCommands);

        rootCommands.add(DEPARTMENT_COM);
        rootCommands.add(OPEN_DEPARTMENT_COM);
        rootCommands.add(CREATE_DEPARTMENT_COM);
        rootCommands.add(REMOVE_DEPARTMENT_COM);
        rootCommands.add(CREATE_EMPLOYEE_IN_DEPARTMENT_COM);
        rootCommands.add(ALL_COM);
        rootCommands.add(HELP_COM);
        rootCommands.add(SEARCH_EMPLOYEE_IN_DEPARTMENT_BY_AGE_COM);
        rootCommands.add(MAX_EMPLOYEES_IN_DEPARTMENT_COM);
        rootCommands.add(EXIT_COM);

        departmentCommands.add(OPEN_EMPLOYEE_COM);
        departmentCommands.add(UPDATE_EMPLOYEE_COM);
        departmentCommands.add(CREATE_EMPLOYEE_COM);
        departmentCommands.add(REMOVE_EMPLOYEE_COM);
        departmentCommands.add(DEPARTMENT_COM);
        departmentCommands.add(HELP_COM);
        departmentCommands.add(EXIT_COM);

        employeeCommands.add(UPDATE_EMPLOYEE_COM);
        employeeCommands.add(DEPARTMENT_COM);
        employeeCommands.add(OPEN_DEPARTMENT_COM);
        employeeCommands.add(HELP_COM);
        employeeCommands.add(EXIT_COM);

        commandPermissionsMap.put(NodeGenerator.ROOT_NODE_TYPE, rootCommands);
        commandPermissionsMap.put(NodeGenerator.DEPARTMENT_NODE_TYPE, departmentCommands);
        commandPermissionsMap.put(NodeGenerator.EMPLOYEE_NODE_TYPE, employeeCommands);
    }

    public static boolean isCommandAllowed(String nodeType, String command) {
        return commandPermissionsMap.get(nodeType).contains(command);
    }

    public static ArrayList<String> getCommands(String nodeType) {
        return commandPermissionsMap.get(nodeType);
    }
}
