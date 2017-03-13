package com.liashenko.departments.userInterface;


import com.liashenko.departments.services.database.entities.EmployeeDataSet;
import com.liashenko.departments.services.nodes.NodeGeneratorUtil;

public class CheckParameters {

    public static final String EMPLOYEE_NAME_FIELD_LENGTH = "25";
    public static final String EMPLOYEE_LANGUAGE_FIELD_LENGTH = "20";
    public static final String EMPLOYEE_METHODOLOGY_FIELD_LENGTH = "20";
    public static final String DEPARTMENT_NAME_FIELD_LENGTH = "45";
    private boolean isCorrect = true;
    private int id;
    private String message = "";
    private String age;
    private String name;
    private String type;
    private String methodology;
    private String language;
    private String departmentName;
    private EmployeeDataSet employeeToUpdate;

    public CheckParameters() {
    }

    public CheckParameters(String departmentName, String age) {
        this.age = checkAge(age);
        this.departmentName = checkDepartmentName(departmentName);
    }

    public CheckParameters(String employeeId, String employeeName, String skillKey, String skill, String employeeAge,
                           EmployeeDataSet employeeToUpdate) {
        this.isCorrect = true;
        this.message = "";
        this.employeeToUpdate = employeeToUpdate;
        this.id = checkId(employeeId, employeeToUpdate);
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

    public int getId() {
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

    public EmployeeDataSet getEmployeeToUpdate() {
        employeeToUpdate = new EmployeeDataSet(id, name, employeeToUpdate.getType(), age, 0,
                methodology, language);
        return employeeToUpdate;
    }

    public String getLanguage() {
        return language;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    private String checkName(String name) {
        if (name.length() > Integer.parseInt(EMPLOYEE_NAME_FIELD_LENGTH)) {
            isCorrect = false;
            message += "Employee's name has more than " + EMPLOYEE_NAME_FIELD_LENGTH + "  symbols.\n";
            return "";
        }
        return name;
    }

    private String checkName(String name, EmployeeDataSet employeeToUpdate) {
        if (name.length() > Integer.parseInt(EMPLOYEE_NAME_FIELD_LENGTH)) {
            isCorrect = false;
            message += "Employee's name has more than " + EMPLOYEE_NAME_FIELD_LENGTH + " symbols.\n";
            return employeeToUpdate.getName();
        } else if (name.isEmpty()) {
            return employeeToUpdate.getName();
        }
        return name;
    }

    private int checkId(String id, EmployeeDataSet employeeToUpdate) {
        int result = checkId(id);
        if (employeeToUpdate != null) {
            return result;
        } else {
            this.isCorrect = false;
            this.message = "Wrong id.";
            return result;
        }
    }

    public int checkId(String id) {
        int result;
        try {
            result = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    private String checkType(String type) {
        String result;
        switch (type) {
            case "d":
                result = NodeGeneratorUtil.DEVELOPER_NODE_TYPE;
                break;
            case "m":
                result = NodeGeneratorUtil.MANAGER_NODE_TYPE;
                break;
            default:
                result = "";
                isCorrect = false;
                message += "Employee's type is wrong.\n";
        }
        return result;
    }

    private String checkDepartmentName(String departmentName) {
        if (departmentName.length() > Integer.parseInt(DEPARTMENT_NAME_FIELD_LENGTH)) {
            isCorrect = false;
            message += "Departments's name has more than " + DEPARTMENT_NAME_FIELD_LENGTH + " symbols.\n";
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
        } else if (methodology.length() > Integer.parseInt(EMPLOYEE_METHODOLOGY_FIELD_LENGTH)) {
            isCorrect = false;
            message += "The methodology field has more than " + EMPLOYEE_METHODOLOGY_FIELD_LENGTH + " symbols";
            return "";
        }
        return methodology;
    }

    private String checkMethodology(String skillKey, String skill, EmployeeDataSet employeeToUpdate) {
        if (skill.length() > Integer.parseInt(EMPLOYEE_METHODOLOGY_FIELD_LENGTH)) {
            isCorrect = false;
            message += "The methodology field has more than " + EMPLOYEE_METHODOLOGY_FIELD_LENGTH + " symbols";
            return "";
        } else if (!skillKey.isEmpty() && !skillKey.equals("-m")
                && employeeToUpdate.getType().equals(NodeGeneratorUtil.MANAGER_NODE_TYPE)) {
            isCorrect = false;
            message += "Manager doesn't have such field.\n";
            return "";
        }
        return "";
    }

    private String checkLanguage(String type, String language) {
        if (type.equals(NodeGeneratorUtil.MANAGER_NODE_TYPE) && !language.isEmpty()) {
            isCorrect = false;
            message += "Manager doesn\'t have language field.\n";
            return "";
        } else if (methodology.length() > Integer.parseInt(EMPLOYEE_LANGUAGE_FIELD_LENGTH)) {
            isCorrect = false;
            message += "The language field has more than " + EMPLOYEE_LANGUAGE_FIELD_LENGTH + " symbols";
            return "";
        }
        return language;
    }

    private String checkLanguage(String skillKey, String skill, EmployeeDataSet employeeToUpdate) {
        if (skill.length() > Integer.parseInt(EMPLOYEE_LANGUAGE_FIELD_LENGTH)) {
            isCorrect = false;
            message = "The language field has more than " + EMPLOYEE_LANGUAGE_FIELD_LENGTH + " symbols";
            return "";
        } else if (!skillKey.isEmpty() && !skillKey.equals("-l")
                && employeeToUpdate.getType().equals(NodeGeneratorUtil.DEVELOPER_NODE_TYPE)) {
            isCorrect = false;
            message = "Developer doesn't have such field.\n";
            return "";
        }
        return "";
    }

    public String checkAge(String age) {
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

    private String checkAge(String age, EmployeeDataSet employeeToUpdate) {
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



