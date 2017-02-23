package com.liashenko.departments.entities;


public class Developer extends Employee {
    public static final String DEVELOPER_EMPLOYEE = "Developer";

    public Developer(int employeeId, String employeeName, short employeeAge, String employeeSkill) {
        super(employeeId, employeeName, employeeAge, employeeSkill);
        type = DEVELOPER_EMPLOYEE;
    }

    public String getLanguage() {
        return skill;
    }
}
