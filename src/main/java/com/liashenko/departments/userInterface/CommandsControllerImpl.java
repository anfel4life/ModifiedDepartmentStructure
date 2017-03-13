package com.liashenko.departments.userInterface;


import com.liashenko.departments.dao.DepartmentDAO;
import com.liashenko.departments.dao.EmployeeDAO;
import com.liashenko.departments.dao.mysql.DepartmentDAOimpl;
import com.liashenko.departments.dao.mysql.EmployeeDAOimpl;
import com.liashenko.departments.services.database.entities.DepartmentDataSet;
import com.liashenko.departments.services.database.entities.EmployeeDataSet;
import com.liashenko.departments.services.nodes.Node;
import com.liashenko.departments.services.nodes.NodeGeneratorUtil;
import com.liashenko.departments.services.nodes.VisitedNodesStack;
import com.liashenko.departments.userInterface.utils.CommandsHolderUtils;
import com.liashenko.departments.userInterface.utils.StringConstructorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommandsControllerImpl implements CommandsController {

    private static final Logger classLogger = LogManager.getLogger(CommandsControllerImpl.class);
    private static final String COMMAND_IS_NOT_ALLOWED_MSG = "Command is not allowed.\n";
    private DepartmentDAO departmentDao;
    private EmployeeDAO employeeDao;

    public CommandsControllerImpl() {
        departmentDao = new DepartmentDAOimpl();
        employeeDao = new EmployeeDAOimpl();
    }

    @Override
    public String createNewDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.CREATE_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        if (!isDepartmentExists(departmentName)) {
            DepartmentDataSet department = new DepartmentDataSet(departmentName);
            if (departmentDao.insertEntity(department)) {
                classLogger.info("Created new department: " + departmentName + "\n");
                return StringConstructorUtils.departmentList(departmentDao.getEntities());
            }
        } else {
            classLogger.info("User tried to create department that had been already created: " + departmentName + "\n");
            return "Department with name " + departmentName + " exists.";
        }
        classLogger.info("Couldn't create new department with name " + departmentName + "\n");
        return "Couldn't create new department with name " + departmentName;
    }

    @Override
    public String openDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.OPEN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        DepartmentDataSet department = departmentDao.getEntity(departmentName);
        if (department != null) {
            Node node = new Node(NodeGeneratorUtil.getNodeTypeByClassName(department), department.getId(), department.getName());
            VisitedNodesStack.getInstance().setNode(node);
            ArrayList<EmployeeDataSet> employeesList = departmentDao.getEntityChildren(department.getId());
            classLogger.info("User opened department " + departmentName + "\n");
            return StringConstructorUtils.employeesList(employeesList);
        }
        classLogger.info("User tried to open absent department - " + departmentName + "\n");
        return "Department with name " + departmentName + " is absent.\n";
    }

    @Override
    public String removeDepartment(String departmentName) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.REMOVE_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        DepartmentDataSet departmentToRemove = departmentDao.getEntity(departmentName);
        if (departmentToRemove != null) {
            if (departmentDao.removeEntity(departmentToRemove.getId())) {
                classLogger.info("User deleted department " + departmentName + "\n");
                return "Department " + departmentName + " was deleted.\n";
            }
        }
        classLogger.info("User tried to delete department " + departmentName + " unsuccessfully.\n");
        return "Deleting department " + departmentName + " wasn't successful.";
    }

    @Override
    public String departmentsList() {
        VisitedNodesStack.getInstance().clear();
        classLogger.info("User has got departments list.\n");
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
        if (department != null) {
            departmentId = department.getId();
            return createEmployee(departmentId, departmentName, employeeName, employeeType, language, methodology,
                    employeeAge);
        }
        classLogger.info("User tried to new employee department unsuccessfully.\n");
        return "Operation wasn't successful.\n";
    }

    private String createEmployee(int departmentId, String departmentName, String employeeName, String employeeType,
                                  String language, String methodology, String employeeAge) {
        ArrayList<EmployeeDataSet> employeesList = null;
        CheckParameters cp = new CheckParameters(employeeName, employeeType, language, methodology, employeeAge);
        EmployeeDataSet newEmployee = new EmployeeDataSet(cp.getName(), cp.getType(), cp.getAge(), departmentId,
                cp.getMethodology(), cp.getLanguage());

        if (cp.isCorrect() && employeeDao.insertEntity(newEmployee)) {
            employeesList = departmentDao.getEntityChildren(departmentId);
            classLogger.info("User created employee " + newEmployee.getName() + " in department " + departmentName);
            return "Employee " + employeeName + " was created in department " + departmentName + ":\n"
                    + StringConstructorUtils.employeesList(employeesList);
        } else {
            classLogger.info("User tried to new employee department unsuccessfully. " + cp.getMessage() + "\n");
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
        if (employee != null) {
            classLogger.info("User opened info about employee with id: " + employeeId);
            return StringConstructorUtils.getEmployeeInfo(employee);
        }
        classLogger.info("User couldn't open info about employee with id: " + employeeId);
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
        int id = new CheckParameters().checkId(employeeId);
        EmployeeDataSet employeeToUpdate = employeeDao.getEntity(id);
        CheckParameters cp = new CheckParameters(employeeId, employeeName, skillKey, skill, employeeAge,
                employeeToUpdate);
        employeeToUpdate = cp.getEmployeeToUpdate();
        employeeToUpdate.setDepartmentId(departmentId);
        if (cp.isCorrect() && employeeDao.updateEntity(employeeToUpdate)) {
            employeesList = departmentDao.getEntityChildren(departmentId);
            classLogger.info("Employee id: " + employeeId + " was updated.");
            return "Employee id:" + employeeId + " was updated:\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
        employeesList = departmentDao.getEntityChildren(departmentId);
        classLogger.info("Employee id: " + employeeId + " was updated. " + cp.getMessage() + "\n");
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
        int id = new CheckParameters().checkId(employeeId);
        int departmentId = lastNode.getNodeId();
        ArrayList<EmployeeDataSet> employeesList;
        if (employeeDao.removeEntity(id)) {
            employeesList = departmentDao.getEntityChildren(departmentId);
            classLogger.info("Employee id: " + employeeId + " was removed.");
            return "Employee id:" + employeeId + " was deleted from department: " + lastNode.getNodeName() + "\n"
                    + StringConstructorUtils.employeesList(employeesList);
        }
        employeesList = departmentDao.getEntityChildren(departmentId);
        classLogger.info("Employee id: " + employeeId + " was deleted.");
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
        classLogger.info("User called command \"help\"");
        return strings.toString();
    }

    @Override
    public String all() {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.ALL_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        classLogger.info("User called command \"all\"");
        return StringConstructorUtils.allEmployeeView(getAllEmployeeView());
    }

    @Override
    public String searchEmployeeInDepartmentByAge(String departmentName, String age) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.SEARCH_EMPLOYEE_IN_DEPARTMENT_BY_AGE_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        CheckParameters cp = new CheckParameters();
        String employeeAge = cp.checkAge(age);
        classLogger.info("User searched employee by age (" + age + ")");
        return StringConstructorUtils.getEmployeeList(getEmployeesFromDepartmentByAge(departmentName, employeeAge));
    }

    @Override
    public String searchDepartmentWithTopEmployees(String employeeType) {
        Node lastNode = VisitedNodesStack.getInstance().peekLast();
        if (!CommandsHolderUtils.isCommandAllowed(lastNode.getNodeType(),
                CommandsHolderUtils.MAX_EMPLOYEES_IN_DEPARTMENT_COM)) {
            return COMMAND_IS_NOT_ALLOWED_MSG;
        }
        switch (employeeType) {
            case "m":
                employeeType = NodeGeneratorUtil.MANAGER_NODE_TYPE;
                break;
            case "d":
                employeeType = NodeGeneratorUtil.DEVELOPER_NODE_TYPE;
                break;
        }
        classLogger.info("User searched department with top employees (" + employeeType + ")");
        return StringConstructorUtils.topDepartmentsList(getEmployeeCountWithType(employeeType), employeeType);
    }

    private boolean isDepartmentExists(String dapartmentName) {
        DepartmentDataSet department = null;
        department = departmentDao.getEntity(dapartmentName);
        return department != null;
    }

    private LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> getAllEmployeeView() {
        LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> result =
                new LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>>();
        DepartmentDAOimpl departmentDAO = new DepartmentDAOimpl();
        ArrayList<DepartmentDataSet> departments = departmentDAO.getEntities();
        if (departments != null && !departments.isEmpty()) {
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
        DepartmentDAOimpl departmentDAO = new DepartmentDAOimpl();
        TreeMap<Integer, DepartmentDataSet> sortedByCountMap =
                new TreeMap<Integer, DepartmentDataSet>(Collections.reverseOrder());
        ArrayList<DepartmentDataSet> departments = departmentDAO.getEntities();
        if (departments != null && !departments.isEmpty()) {
            for (DepartmentDataSet department : departments) {
                ArrayList<EmployeeDataSet> employeesByType = new ArrayList<EmployeeDataSet>();
                ArrayList<EmployeeDataSet> employees = departmentDAO.getEntityChildren(department.getId());
                if (employees != null && !employees.isEmpty()) {
                    Integer employeesCount = 0;
                    for (EmployeeDataSet employee : employees) {
                        if (employee.getType().equals(employeeType)) {
                            employeesCount = employeesCount + 1;
                            employeesByType.add(employee);
                        }
                    }
                    sortedByCountMap.put(employeesCount, department);
                }
            }
        }

        if (sortedByCountMap != null && !sortedByCountMap.isEmpty()) {
            int maxCount = sortedByCountMap.firstKey();
            for (Map.Entry<Integer, DepartmentDataSet> entry : sortedByCountMap.entrySet()) {
                int count = entry.getKey();
                if (count >= maxCount) {
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
        DepartmentDAOimpl departmentDAO = new DepartmentDAOimpl();
        DepartmentDataSet department = departmentDAO.getEntity(departmentName);
        ArrayList<EmployeeDataSet> employeesList = departmentDAO.getEntityChildren(department.getId());
        if (employeesList != null && !employeesList.isEmpty()) {
            for (EmployeeDataSet employee : employeesList) {
                if (employee.getAge().equals(age)) {
                    resultList.add(employee);
                }
            }
        }
        return resultList;
    }
}
