package com.liashenko.departments.userInterface;


import com.liashenko.departments.services.mainDBService.MainServiceImpl;
import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import com.liashenko.departments.services.mainService.MainService;
import com.liashenko.departments.services.nodesService.Node;
import com.liashenko.departments.services.nodesService.NodeGenerator;
import com.liashenko.departments.services.nodesService.VisitedNodesStack;

import java.util.ArrayList;

public class CommandsControllerImpl implements CommandsController {

    private static final String COMMAND_IS_NOT_ALLOWED_MSG = "Command is not allowed.";
    private MainService mainService;

    public CommandsControllerImpl() {
        mainService = new MainServiceImpl();
    }

    @Override
    public String createNewDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.CREATE_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        if (mainService.createNewDepartment(departmentName)){
            return StringConstructorUtils.departmentList(mainService.getDepartmentsList());
        }
        return "Couldn't create new department with name " + departmentName;
    }

    @Override
    public String openDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.OPEN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        DepartmentDataSet department  = mainService.getDepartmentByName(departmentName);
        if (department != null){
            Node node = new Node(NodeGenerator.getNodeTypeByClassName(department), department.getId(), department.getName());
            VisitedNodesStack.getInstance().setNode(node);
//            System.out.println(">>CommandsControllerImpl() openDepartment " + department.getNodeType() +"//"+department.getNodeId());
            ArrayList<EmployeeDataSet> employeesList = mainService.getDepartmentEmployeesList(department.getId());
            return StringConstructorUtils.employeesList(employeesList);
        }
        return "Department with name " + departmentName + " is absent.";
    }

    @Override
    public String removeDepartment(String departmentName) {
        return null;
    }

    @Override
    public String departmentsList() {
        VisitedNodesStack.getInstance().clear();
        return StringConstructorUtils.departmentList(mainService.getDepartmentsList());
    }

    @Override
    public String createEmployee(String employeeName, String employeeType, String language, String methodology,
                                 String employeeAge) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.CREATE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        ArrayList<EmployeeDataSet> employeesList = null;
        int departmentId = lastNode.getNodeId();
        String departmentName = lastNode.getNodeName();
        CheckParameters cp = new CheckParameters(employeeName, employeeType, language, methodology, employeeAge);
        if (cp.isCorrect() && mainService.createNewEmployee(cp.getName(), departmentId, cp.getType(), cp.getLanguage(),
                cp.getMethodology(), cp.getAge())) {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Employee " + employeeName + " was created in department " + departmentName + ":\n"
                    + StringConstructorUtils.employeesList(employeesList);
        } else {
            return "Creating new employee in department " + departmentName + " wasn't successful:" + "\n"
                    + cp.getMessage() + "\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
    }

    @Override
    public String createEmployee(String departmentName, String employeeName, String employeeType, String language,
                                 String methodology, String employeeAge) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.CREATE_EMPLOYEE_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        return null;
    }

    @Override
    public String openEmployee(String employeeId) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.OPEN_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        EmployeeDataSet employee = mainService.getEmployeeById(new CheckParameters().checkId(employeeId));
        if (employee != null){
            return StringConstructorUtils.getEmployeeInfo(employee);
        }
        return "Employee with id " + employeeId + " is absent.";
    }

    @Override
    public String updateEmployee(String employeeId, String employeeName, String skillKey, String skill,
                                 String employeeAge) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.UPDATE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }

        ArrayList<EmployeeDataSet> employeesList;
        int departmentId = lastNode.getNodeId();
        int id  = new CheckParameters().checkId(employeeId);
        EmployeeDataSet employeeToUpdate = mainService.getEmployeeById(id);
        CheckParameters cp = new CheckParameters(employeeId, employeeName, skillKey, skill, employeeAge,
                employeeToUpdate);
        employeeToUpdate = cp.getEmployeeToUpdate();
        employeeToUpdate.setDepartmentId(departmentId);
        if (cp.isCorrect() && mainService.updateEmployee(employeeToUpdate)) {
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Employee id:" + employeeId + " was updated:\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
            employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Updating employee id:" + employeeId + " wasn't successful:\n"
                    + cp.getMessage() + "\n"
                    + StringConstructorUtils.employeesList(employeesList);
    }

    @Override
    public String removeEmployee(String employeeId) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.REMOVE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        int id  = new CheckParameters().checkId(employeeId);
        int departmentId = lastNode.getNodeId();
        ArrayList<EmployeeDataSet> employeesList;
         if (mainService.removeEmployee(id)){
             employeesList = mainService.getDepartmentEmployeesList(departmentId);
            return "Employee id:" + employeeId + " was deleted from department: " + lastNode.getNodeName() +"\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
        employeesList = mainService.getDepartmentEmployeesList(departmentId);
        return "Deleting employee id:" + employeeId + " wasn't successful:\n"
                + StringConstructorUtils.employeesList(employeesList);
    }

    @Override
    public String help() {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.HELP_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        StringBuilder strings = new StringBuilder();
        ArrayList<String> commandsList = CommandsHolderUtils.getCommands(
                VisitedNodesStack.getInstance().peekLast().getNodeType());
        for (String com : commandsList) {
            strings.append(com).append("\n");
        }
        return strings.toString();
    }

    @Override
    public String all() {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.ALL_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        return null;
    }

    @Override
    public String searchEmployeeInDepartmentByAge(String departmentName, String age) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.SEARCH_EMPLOYEE_IN_DEPARTMENT_BY_AGE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        return null;
    }

    @Override
    public String searchDepartmentWithTopEmployees(String employeeType) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.MAX_EMPLOYEES_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        return null;
    }
}
