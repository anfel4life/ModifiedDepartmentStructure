package com.liashenko.departments.entities;

public class Employee extends Entity {
    protected short age = 0;
//    protected int id = 0;
//    protected String name = "";
    protected String type;
    protected String skill;
    protected String nodeType;

    public Employee(int id, String name, short age, String employeeSkill) {
        this.age = age;
        this.id = id;
        this.name = name;
        this.skill = employeeSkill;
        this.nodeType = EMPLOYEE_NODE_TYPE;
    }

    @Override
    public int getNodeId() {//remove
        return id;
    }

    @Override
    public String getNodeType() {
        return nodeType;
    }

    public String getName() {
        return name;
    }

    public short getAge() {
        return age;
    }
//
//    public int getId() {
//        return id;
//    }

    public String getType() {
        return type;
    }
}
