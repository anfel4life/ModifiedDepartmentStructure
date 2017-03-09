package com.liashenko.departments.userInterface;


import com.liashenko.departments.services.mainDBService.dao.DepartmentDAO;
import com.liashenko.departments.services.mainDBService.dao.EmployeeDAO;
import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import com.liashenko.departments.services.nodesService.Node;
import com.liashenko.departments.services.nodesService.NodeGenerator;
import com.liashenko.departments.services.nodesService.VisitedNodesStack;

import java.util.*;

public class CommandsControllerImpl implements CommandsController {

    private static final String COMMAND_IS_NOT_ALLOWED_MSG = "Command is not allowed.";
    private DepartmentDAO departmentDao;
    private EmployeeDAO employeeDao;

    public CommandsControllerImpl() {
        departmentDao = new DepartmentDAO();
        employeeDao = new EmployeeDAO();
    }

    @Override
    public String createNewDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.CREATE_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }

        DepartmentDataSet department = new DepartmentDataSet(departmentName);
         if (departmentDao.insertEntity(department)){
            return StringConstructorUtils.departmentList(departmentDao.getEntities());
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
        DepartmentDataSet department  = departmentDao.getEntity(departmentName);
        if (department != null){
            Node node = new Node(NodeGenerator.getNodeTypeByClassName(department), department.getId(), department.getName());
            VisitedNodesStack.getInstance().setNode(node);
            ArrayList<EmployeeDataSet> employeesList = departmentDao.getEntityChildren(department.getId());
            return StringConstructorUtils.employeesList(employeesList);
        }
        return "Department with name " + departmentName + " is absent.";
    }

    @Override
    public String removeDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.REMOVE_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        DepartmentDataSet departmentToRemove = departmentDao.getEntity(departmentName);
        if (departmentToRemove != null){
            if (departmentDao.removeEntity(departmentToRemove.getId())){
                return "Department " + departmentName + " was deleted.";
            }
        }
        return "Deleting department " + departmentName + " wasn't successful.";
    }

    @Override
    public String departmentsList() {
        VisitedNodesStack.getInstance().clear();
        return StringConstructorUtils.departmentList(departmentDao.getEntities());
    }

    @Override
    public String createEmployee(String employeeName, String employeeType, String language, String methodology,
                                 String employeeAge) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.CREATE_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        int departmentId = lastNode.getNodeId();
        String departmentName = lastNode.getNodeName();
        return createEmployee(departmentId, departmentName, employeeName, employeeType, language, methodology,
                employeeAge);
    }

    @Override
    public String createEmployee(String departmentName, String employeeName, String employeeType, String language,
                                 String methodology, String employeeAge) {
        if (!CommandsHolderUtils.isCommandAllowed(VisitedNodesStack.getInstance().peekLast().getNodeType(),
                CommandsHolderUtils.CREATE_EMPLOYEE_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        DepartmentDataSet department = departmentDao.getEntity(departmentName);
        int departmentId = 0;
        if (department != null){
            departmentId = department.getId();
            return createEmployee(departmentId, departmentName, employeeName, employeeType, language, methodology,
                    employeeAge);
        }
        return "Operation wasn't successful.";
    }

    private String createEmployee(int departmentId, String departmentName, String employeeName, String employeeType,
                                  String language, String methodology, String employeeAge){
        ArrayList<EmployeeDataSet> employeesList = null;
        CheckParameters cp = new CheckParameters(employeeName, employeeType, language, methodology, employeeAge);
        EmployeeDataSet newEmployee = new EmployeeDataSet(cp.getName(), cp.getType(), cp.getAge(), departmentId,
                cp.getMethodology(), cp.getLanguage());

        if (cp.isCorrect() && employeeDao.insertEntity(newEmployee)) {
            employeesList = departmentDao.getEntityChildren(departmentId);
            return "Employee " + employeeName + " was created in department " + departmentName + ":\n"
                    + StringConstructorUtils.employeesList(employeesList);
        } else {
            return "Creating new employee in department " + departmentName + " wasn't successful:" + "\n"
                    + cp.getMessage() + "\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
    }

    @Override
    public String openEmployee(String employeeId) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.OPEN_EMPLOYEE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        EmployeeDataSet employee = employeeDao.getEntity(new CheckParameters().checkId(employeeId));
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
        EmployeeDataSet employeeToUpdate = employeeDao.getEntity(id);
        CheckParameters cp = new CheckParameters(employeeId, employeeName, skillKey, skill, employeeAge,
                employeeToUpdate);
        employeeToUpdate = cp.getEmployeeToUpdate();
        employeeToUpdate.setDepartmentId(departmentId);
        if (cp.isCorrect() && employeeDao.updateEntity(employeeToUpdate)) {
            employeesList = departmentDao.getEntityChildren(departmentId);
            return "Employee id:" + employeeId + " was updated:\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
            employeesList = departmentDao.getEntityChildren(departmentId);
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
         if (employeeDao.removeEntity(id)){
             employeesList = departmentDao.getEntityChildren(departmentId);
            return "Employee id:" + employeeId + " was deleted from department: " + lastNode.getNodeName() +"\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
        employeesList = departmentDao.getEntityChildren(departmentId);
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
        return StringConstructorUtils.allEmployeeView(getAllEmployeeView());
    }

    @Override
    public String searchEmployeeInDepartmentByAge(String departmentName, String age) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.SEARCH_EMPLOYEE_IN_DEPARTMENT_BY_AGE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        CheckParameters cp  = new CheckParameters();
        String employeeAge  = cp.checkAge(age);
        return StringConstructorUtils.getEmployeeList(getEmployeesFromDepartmentByAge(departmentName, employeeAge));
    }

    @Override
    public String searchDepartmentWithTopEmployees(String employeeType) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.MAX_EMPLOYEES_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        switch(employeeType){
            case "m":
                employeeType = NodeGenerator.MANAGER_NODE_TYPE;
                break;
            case "d":
                employeeType = NodeGenerator.DEVELOPER_NODE_TYPE;
                break;
        }
        return StringConstructorUtils.topDepartmentsList(getEmployeeCountWithType(employeeType), employeeType);
    }

    private LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> getAllEmployeeView() {
        LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> result =
                new LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>>();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        ArrayList<DepartmentDataSet> departments = departmentDAO.getEntities();
        if (departments != null && !departments.isEmpty()){
            for (DepartmentDataSet department : departments) {
                ArrayList<EmployeeDataSet> employees = new ArrayList<EmployeeDataSet>();
                employees = departmentDAO.getEntityChildren(department.getId());
                result.put(department, employees);
            }
        }
        return result;
    }

    private HashSet<DepartmentDataSet> getEmployeeCountWithType(String employeeType) {
        HashSet<DepartmentDataSet> result = new HashSet<DepartmentDataSet>();

        DepartmentDAO departmentDAO = new DepartmentDAO();
        TreeMap<Integer, DepartmentDataSet> sortedByCountMap =
                new TreeMap<Integer, DepartmentDataSet>(Collections.reverseOrder());
        ArrayList<DepartmentDataSet> departments = departmentDAO.getEntities();
        if (departments != null && !departments.isEmpty()){
            for (DepartmentDataSet department : departments) {
                ArrayList<EmployeeDataSet> employeesByType = new ArrayList<EmployeeDataSet>();
                ArrayList<EmployeeDataSet> employees = departmentDAO.getEntityChildren(department.getId());
                if (employees != null && !employees.isEmpty()){
                    Integer employeesCount = 0;
                    for (EmployeeDataSet employee : employees){
                        if (employee.getType().equals(employeeType)){
                            employeesCount = employeesCount + 1;
                            employeesByType.add(employee);
                        }
                    }
                    sortedByCountMap.put(employeesCount, department);
                }
            }
        }

        if (sortedByCountMap != null && !sortedByCountMap.isEmpty()){

            int maxCount = sortedByCountMap.firstKey();
            for (Map.Entry<Integer, DepartmentDataSet> entry : sortedByCountMap.entrySet()) {
                int count = entry.getKey();
                if (count >= maxCount){
                    result.add(entry.getValue());
                } else {
                    break;
                }
            }
        }
        return result;
    }

    private ArrayList<EmployeeDataSet> getEmployeesFromDepartmentByAge(String departmentName, String age) {
        ArrayList<EmployeeDataSet> resultList = new ArrayList<EmployeeDataSet>();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        DepartmentDataSet department = departmentDAO.getEntity(departmentName);
        ArrayList<EmployeeDataSet> employeesList = departmentDAO.getEntityChildren(department.getId());
        if (employeesList != null && !employeesList.isEmpty()){
            for (EmployeeDataSet employee : employeesList){
                if (employee.getAge().equals(age)){
                    resultList.add(employee);
                }
            }
        }
        return resultList;
    }
}
