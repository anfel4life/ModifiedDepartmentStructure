package com.liashenko.departments.entities;


public class Department extends Entity {

    private String nodeType;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        nodeType = DEPARTMENT_NODE_TYPE;
    }

    @Override
    public int getNodeId() {
        return id;
    }

    @Override
    public String getNodeType() {
        return nodeType;
    }
}
