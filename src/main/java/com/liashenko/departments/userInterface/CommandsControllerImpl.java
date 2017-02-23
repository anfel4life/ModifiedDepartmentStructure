package com.liashenko.departments.userInterface;

import com.liashenko.departments.entities.Department;
import com.liashenko.departments.entities.Developer;
import com.liashenko.departments.entities.Employee;
import com.liashenko.departments.entities.Manager;
import com.liashenko.departments.services.mainDBService.CheckParameters;
import com.liashenko.departments.services.mainDBService.MainDBServiceImpl;
import com.liashenko.departments.services.mainDBService.utils.StringConstructorUtils;
import com.liashenko.departments.services.mainService.MainService;
import com.liashenko.departments.services.nodesService.VisitedNodesStack;

import java.util.ArrayList;

public class CommandsControllerImpl implements CommandsController {

    private static final String COMMAND_IS_NOT_ALLOWED_MSG = "Command is not allowed.";
    private MainService mainService;

    public CommandsControllerImpl() {
        mainService = new MainDBServiceImpl();
    }

    @Override
    public String createNewDepartment(String departmentName) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.CREATE_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }

        if (mainService.createNewDepartment(departmentName)) {
            return "The department with name " + departmentName + " was created.\n"
                    + StringConstructorUtils.departmentList(mainService.getDepartmentsList());
        }
        return "Operation wasn't successful.\n"
                + StringConstructorUtils.departmentList(mainService.getDepartmentsList());
    }

    @Override
    public String openDepartment(String departmentName) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.OPEN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }

        Department department = mainService.getDepartmentByName(departmentName);
        if (department != null && department.getId() != 0) {
            VisitedNodesStack.getInstance().setNode(department);
            ArrayList<Employee> employeesList = mainService.getDepartmentEmployeesList(department.getId());
            return "The department with name " + departmentName + " was opened:\n" +
                    StringConstructorUtils.employeesList(employeesList) + "\n";
        }
        return "The department with name " + departmentName + " didn't found.\n";
    }

    @Override
    public String removeDepartment(String departmentName) {
        if (mainService.removeDepartment(departmentName)) {
            return "The department with name " + departmentName + " was removed.\n" +
                    StringConstructorUtils.departmentList(mainService.getDepartmentsList());
        }
        return "Operation wasn't successful."
                + StringConstructorUtils.departmentList(mainService.getDepartmentsList());
    }

    @Override
    public String departmentsList() {
        VisitedNodesStack.getInstance().clear();
        ArrayList<Department> departmentsList = mainService.getDepartmentsList();
        return StringConstructorUtils.departmentList(departmentsList);
    }

    @Override
    public String createEmployee(String employeeName, String employeeType, String language, String methodology,
                                 String employeeAge) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.CREATE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        int departmentId = VisitedNodesStack.getInstance().peekLast().getNodeId();
        String departmentName = mainService.getDepartmentById(departmentId).getName();
        return createEmployee(departmentId, departmentName, employeeName, employeeType, language, methodology, employeeAge);
    }

    @Override
    public String createEmployee(String departmentName, String employeeName, String employeeType, String language,
                                 String methodology, String employeeAge) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.CREATE_EMPLOYEE_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        Department department = mainService.getDepartmentByName(departmentName);
        int departmentId = department != null ? department.getId() : 0;
        return createEmployee(departmentId, departmentName, employeeName, employeeType, language, methodology, employeeAge);
    }

    private String createEmployee(int departmentId, String departmentName, String employeeName, String employeeType,
                                  String language, String methodology, String employeeAge) {
        ArrayList<Employee> employeesList;
        CheckParameters cp = new CheckParameters(employeeName, employeeType, language, methodology, employeeAge);
        if (cp.isCorrect() && mainService.createNewEmployee(cp.getName(), departmentId, cp.getType(), cp.getLanguage(),
                cp.getMethodology(), cp.getAge())) {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Employee " + cp.getName() + " was created in department " + departmentName + ":\n"
                    + StringConstructorUtils.employeesList(employeesList);
        } else {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Creating new employee in department " + departmentName + " wasn't successful:" + "\n"
                    + cp.getMessage() + "\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
    }

    @Override
    public String updateEmployee(String employeeId, String employeeName, String skillKey, String skill,
                                 String employeeAge) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.UPDATE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        int departmentId = VisitedNodesStack.getInstance().peekLast().getNodeId();
        Employee employeeToUpdate = mainService.getEmployeeById(employeeId);
        ArrayList<Employee> employeesList;
        CheckParameters cp = new CheckParameters(employeeId, employeeName, skillKey, skill, employeeAge, employeeToUpdate);
        if (cp.isCorrect() && mainService.updateEmployee(cp.getId(), cp.getName(), cp.getLanguage(), cp.getMethodology(),
                cp.getAge())) {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Employee id:" + employeeId + " was updated:\n"
                    + StringConstructorUtils.employeesList(employeesList);
        } else {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Updating employee id:" + employeeId + " wasn't successful:\n"
                    + cp.getMessage() + "\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
    }

    @Override
    public String removeEmployee(String employeeId) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.REMOVE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        int departmentId = VisitedNodesStack.getInstance().peekLast().getNodeId();
        ArrayList<Employee> employeesList;
        if (mainService.removeEmployee(employeeId)) {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Employee id:" + employeeId + " was removed:\n"
                    + StringConstructorUtils.employeesList(employeesList);
        } else {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Removing employee id:" + employeeId + " wasn't successful:\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
    }

    @Override
    public String openEmployee(String employeeId) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.OPEN_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        Employee employee = mainService.getEmployeeById(employeeId);
        if (employee != null) {
            return StringConstructorUtils.getEmployeeInfo(employee);
        }
        return "There isn't an employee with id:" + employeeId + "\n";
    }

    @Override
    public String help() {
        StringBuilder strings = new StringBuilder();
        ArrayList<String> commandsList = VisitedNodesStack.getInstance().peekLast().getNodeCommands(
                VisitedNodesStack.getInstance().peekLast().getNodeType());
        for (String com : commandsList) {
            strings.append(com).append("\n");
        }
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.HELP_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        return strings.toString();
    }

    @Override
    public String all() {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.ALL_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        return StringConstructorUtils.allEmployeeView(mainService.getAllEmployeeView());
    }

    @Override
    public String searchEmployeeInDepartmentByAge(String departmentName, String age) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.SEARCH_EMPLOYEE_IN_DEPARTMENT_BY_AGE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        CheckParameters cp = new CheckParameters(departmentName, age);
        if (cp.isCorrect()) {
            return StringConstructorUtils.getEmployeeList(mainService.getEmployeesFromDepartmentByAge(
                    cp.getDepartmentName(), cp.getAge()));
        }
        return "There aren't " + age + "-year old employees in " + departmentName;
    }

    @Override
    public String searchDepartmentWithTopEmployees(String employeeType) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.MAX_EMPLOYEES_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }

        switch (employeeType) {
            case "d":
                employeeType = Developer.DEVELOPER_EMPLOYEE;
                break;
            case "m":
                employeeType = Manager.MANAGER_EMPLOYEE;
                break;
            default:
                return "Wrong type!";
        }
        return StringConstructorUtils.topDepartmentsList(mainService.getEmployeeCountWithType(employeeType), employeeType);
    }
}
