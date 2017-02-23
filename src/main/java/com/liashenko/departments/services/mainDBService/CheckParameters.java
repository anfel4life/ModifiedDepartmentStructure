package com.liashenko.departments.services.mainDBService;


import com.liashenko.departments.entities.Developer;
import com.liashenko.departments.entities.Employee;
import com.liashenko.departments.entities.Manager;

public class CheckParameters {

    private boolean isCorrect = true;
    private String id;
    private String message = "";
    private String age;
    private String name;
    private String type;
    private String methodology;
    private String language;
    private String departmentName;
    private Employee employeeToUpdate;

    public CheckParameters(String departmentName, String age) {
        this.age = checkAge(age);
        this.departmentName = checkDepartmentName(departmentName);
    }

    public CheckParameters(String id, String employeeName, String skillKey, String skill, String employeeAge,
                           Employee employeeToUpdate) {
        this.isCorrect = true;
        this.message = "";
        this.employeeToUpdate = employeeToUpdate;
        this.id = checkId(id, employeeToUpdate);
        this.age = checkAge(employeeAge, employeeToUpdate);
        this.name = checkName(employeeName, employeeToUpdate);
        this.methodology = checkMethodology(skillKey, skill, employeeToUpdate);
        this.language = checkLanguage(skillKey, skill, employeeToUpdate);
    }

    public CheckParameters(String employeeName, String employeeType, String language, String methodology,
                           String employeeAge) {
        this.isCorrect = true;
        this.message = "";
        this.age = checkAge(employeeAge);
        this.name = checkName(employeeName);
        this.type = checkType(employeeType);
        this.methodology = checkMethodology(employeeType, methodology);
        this.language = checkLanguage(employeeType, language);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMethodology() {
        return methodology;
    }

    public String getLanguage() {
        return language;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    private String checkName(String name) {
        if (name.length() > Integer.parseInt(QueryBuilder.EMPLOYEE_NAME_FIELD_LENGTH)) {
            isCorrect = false;
            message += "Employee's name has more than " + QueryBuilder.EMPLOYEE_NAME_FIELD_LENGTH + "  symbols.\n";
            return "";
        }
        return name;
    }

    private String checkName(String name, Employee employeeToUpdate) {
        if (name.length() > Integer.parseInt(QueryBuilder.EMPLOYEE_NAME_FIELD_LENGTH)) {
            isCorrect = false;
            message += "Employee's name has more than " + QueryBuilder.EMPLOYEE_NAME_FIELD_LENGTH + " symbols.\n";
            return employeeToUpdate.getName();
        } else if (name.isEmpty()) {
            return employeeToUpdate.getName();
        }
        return name;
    }

    private String checkId(String id, Employee employeeToUpdate) {
        if (employeeToUpdate != null) {
            return String.valueOf(employeeToUpdate.getId());
        } else {
            this.isCorrect = false;
            this.message = "Wrong id.";
            return id;
        }
    }

    private String checkType(String type) {
        String result;
        switch (type) {
            case "d":
                result = Developer.DEVELOPER_EMPLOYEE;
                break;
            case "m":
                result = Manager.MANAGER_EMPLOYEE;
                break;
            default:
                result = "";
                isCorrect = false;
                message += "Employee's type is wrong.\n";
        }
        return result;
    }

    private String checkDepartmentName(String departmentName) {
        if (departmentName.length() > Integer.parseInt(QueryBuilder.DEPARTMENT_NAME_FIELD_LENGTH)) {
            isCorrect = false;
            message += "Departments's name has more than " + QueryBuilder.DEPARTMENT_NAME_FIELD_LENGTH + " symbols.\n";
            return "";
        } else if (departmentName.isEmpty()) {
            return "";
        }
        return departmentName;
    }


    private String checkMethodology(String type, String methodology) {
        if (type.equals("d") && !methodology.isEmpty()) {
            isCorrect = false;
            message += "The developer doesn\'t have methodology field.\n";
            return "";
        } else if (methodology.length() > Integer.parseInt(QueryBuilder.EMPLOYEE_METHODOLOGY_FIELD_LENGTH)) {
            isCorrect = false;
            message += "The methodology field has more than " + QueryBuilder.EMPLOYEE_METHODOLOGY_FIELD_LENGTH + " symbols";
            return "";
        }
        return methodology;
    }

    private String checkMethodology(String skillKey, String skill, Employee employeeToUpdate) {
        if (skill.length() > Integer.parseInt(QueryBuilder.EMPLOYEE_METHODOLOGY_FIELD_LENGTH)) {
            isCorrect = false;
            message += "The methodology field has more than " + QueryBuilder.EMPLOYEE_METHODOLOGY_FIELD_LENGTH + " symbols";
            return "";
        } else if (skillKey != null && !skillKey.equals("-m")
                && employeeToUpdate.getType().equals(Manager.MANAGER_EMPLOYEE)) {
            isCorrect = false;
            message = "Manager doesn't have such field.\n";
            return "";
        }
        if (skillKey != null && (skillKey.equals("-m") || skillKey.isEmpty())
                && employeeToUpdate.getType().equals(Manager.MANAGER_EMPLOYEE)) {
            Manager manager = (Manager) employeeToUpdate;
            return skill.isEmpty() ? manager.getMethodology() : skill;
        }
        return "";
    }

    private String checkLanguage(String type, String language) {
        if (type.equals(Manager.MANAGER_EMPLOYEE) && !language.isEmpty()) {
            isCorrect = false;
            message += "The manager doesn\'t have language field.\n";
            return "";
        } else if (methodology.length() > Integer.parseInt(QueryBuilder.EMPLOYEE_LANGUAGE_FIELD_LENGTH)) {
            isCorrect = false;
            message += "The language field has more than " + QueryBuilder.EMPLOYEE_LANGUAGE_FIELD_LENGTH + " symbols";
            return "";
        }
        return language;
    }

    private String checkLanguage(String skillKey, String skill, Employee employeeToUpdate) {
        if (skill.length() > Integer.parseInt(QueryBuilder.EMPLOYEE_LANGUAGE_FIELD_LENGTH)) {
            isCorrect = false;
            message = "The language field has more than " + QueryBuilder.EMPLOYEE_LANGUAGE_FIELD_LENGTH + " symbols";
            return "";
        } else if (skillKey != null && !skillKey.equals("-l")
                && employeeToUpdate.getType().equals(Developer.DEVELOPER_EMPLOYEE)) {
            isCorrect = false;
            message = "Developer doesn't have such field.\n";
            return "";
        }
        if (skillKey != null && (skillKey.equals("-l") || skillKey.isEmpty())
                && employeeToUpdate.getType().equals(Developer.DEVELOPER_EMPLOYEE)) {
            Developer developer = (Developer) employeeToUpdate;
            return skill.isEmpty() ? developer.getLanguage() : skill;
        }
        return "";
    }

    private String checkAge(String age) {
        short ageShort = 0;
        if (age.isEmpty()) {
            message += "Employee's age isn't defined.\n";
            return "0";
        }
        try {
            ageShort = Short.valueOf(age);
        } catch (NumberFormatException e) {
            isCorrect = false;
            message += "Employee's age has wrong format.\n";
            return "0";
        }
        if (ageShort < 1 || 170 < ageShort) {
            isCorrect = false;
            message += "Employee's age is wrong.\n";
            return "0";
        }
        return age;
    }

    private String checkAge(String age, Employee employeeToUpdate) {
        short ageShort = 0;
        if (age.isEmpty()) {
            return String.valueOf(employeeToUpdate.getAge());
        }
        try {
            ageShort = Short.valueOf(age);
        } catch (NumberFormatException e) {
            isCorrect = false;
            message += "Employee's age has wrong format.\n";
            return String.valueOf(employeeToUpdate.getAge());
        }
        if (ageShort < 1 || 170 < ageShort) {
            isCorrect = false;
            message += "Employee's age is wrong.\n";
            return String.valueOf(employeeToUpdate.getAge());
        }
        return age;
    }
}



