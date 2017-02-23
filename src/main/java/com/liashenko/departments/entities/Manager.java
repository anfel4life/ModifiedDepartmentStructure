package com.liashenko.departments.entities;


public class Manager extends Employee {

    public static final String MANAGER_EMPLOYEE = "Manager";

    public Manager(int employeeId, String employeeName, short employeeAge, String employeeSkill) {
        super(employeeId, employeeName, employeeAge, employeeSkill);
        type = MANAGER_EMPLOYEE;
    }

    public String getMethodology() {
        return skill;
    }
}
