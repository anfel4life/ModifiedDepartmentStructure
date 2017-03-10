package com.liashenko.departments.services.nodesService;


import com.liashenko.departments.services.dbService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.dbService.dataSets.EmployeeDataSet;
import com.liashenko.departments.services.dbService.dataSets.Root;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class NodeGeneratorUtil {
    public static final String ROOT_NODE_TYPE = "root";
    public static final String DEPARTMENT_NODE_TYPE = "department";
    public static final String EMPLOYEE_NODE_TYPE = "employee";
    public static final String DEVELOPER_NODE_TYPE = "developer";
    public static final String MANAGER_NODE_TYPE = "manager";

    private static final HashMap<String, HashSet<String>> nodeMap = new HashMap<String, HashSet<String>>();

    private static final HashSet<String> root = new HashSet<String>();
    private static final HashSet<String> department = new HashSet<String>();
    private static final HashSet<String> employee = new HashSet<String>();

    static {
        root.add(Root.class.getName());
        department.add(DepartmentDataSet.class.getName());
        employee.add(EmployeeDataSet.class.getName());
        nodeMap.put(NodeGeneratorUtil.ROOT_NODE_TYPE, root);
        nodeMap.put(NodeGeneratorUtil.DEPARTMENT_NODE_TYPE, department);
        nodeMap.put(NodeGeneratorUtil.EMPLOYEE_NODE_TYPE, employee);
    }

    public static String getNodeTypeByClassName(Object object){
        String nodeType;
        for (Map.Entry<String, HashSet<String>> entry : nodeMap.entrySet()) {
            HashSet<String> set = entry.getValue();
            if (set.contains(object.getClass().getName())){
               nodeType = entry.getKey();
                return nodeType;
            }
        }
        return null;
    }
}
