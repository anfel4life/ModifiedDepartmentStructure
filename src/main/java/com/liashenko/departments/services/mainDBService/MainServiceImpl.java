package com.liashenko.departments.services.mainDBService;


import com.liashenko.departments.services.mainDBService.dao.DepartmentDAO;
import com.liashenko.departments.services.mainDBService.dao.EmployeeDAO;
import com.liashenko.departments.services.mainDBService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.mainDBService.dataSets.EmployeeDataSet;
import com.liashenko.departments.services.mainService.MainService;
import java.util.*;


public class MainServiceImpl implements MainService {

    @Override
    public ArrayList<EmployeeDataSet> getDepartmentEmployeesList(int departmentId) {
        DepartmentDAO departmentDao = new DepartmentDAO();
        return departmentDao.getEmployees(departmentId);
    }

    @Override
    public boolean createNewDepartment(String newDepartmentName) {
        DepartmentDAO departmentDao = new DepartmentDAO();
        return departmentDao.insertDepartment(newDepartmentName);
    }

    @Override
    public boolean removeDepartment(int departmentId) {
        DepartmentDAO departmentDao = new DepartmentDAO();
        return departmentDao.removeDepartment(departmentId);
    }

    @Override
    public ArrayList<DepartmentDataSet> getDepartmentsList() {
        DepartmentDAO departmentDao = new DepartmentDAO();
        return departmentDao.getDepartments();
    }

    @Override
    public DepartmentDataSet getDepartmentByName(String departmentName) {
        DepartmentDAO departmentDao = new DepartmentDAO();
        return departmentDao.getDepartment(departmentName);
    }

    @Override
    public DepartmentDataSet getDepartmentById(int departmentId) {
        return null;
    }

    @Override
    public EmployeeDataSet getEmployeeById(int employeeId) {
        EmployeeDAO employeeDao = new EmployeeDAO();
        return employeeDao.getEmployee(employeeId);
    }

    @Override
    public boolean updateEmployee(EmployeeDataSet newEmployee) {
        EmployeeDAO employeeDao = new EmployeeDAO();
        return employeeDao.updateEmployee(newEmployee);
    }

    @Override
    public boolean removeEmployee(int id) {
        EmployeeDAO employeeDao = new EmployeeDAO();
        return employeeDao.removeEmployee(id);
    }

    @Override
    public boolean createNewEmployee(String employeeName, int departmentId, String employeeType, String language,
                                     String methodology, String employeeAge) {

        EmployeeDAO employeeDao = new EmployeeDAO();
            EmployeeDataSet newEmployee = new EmployeeDataSet(employeeName, employeeType, employeeAge, departmentId,
                    methodology, language);
        return employeeDao.insertEmployee(newEmployee);
    }

    @Override
    public LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> getAllEmployeeView() {
        LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>> result =
                new LinkedHashMap<DepartmentDataSet, ArrayList<EmployeeDataSet>>();
        DepartmentDAO departmentDAO = new DepartmentDAO();
            ArrayList<DepartmentDataSet> departments = departmentDAO.getDepartments();
            if (departments != null && !departments.isEmpty()){
                for (DepartmentDataSet department : departments) {
                    ArrayList<EmployeeDataSet> employees = new ArrayList<EmployeeDataSet>();
                    employees = departmentDAO.getEmployees(department.getId());
                    result.put(department, employees);
                }
            }
        return result;
    }

    @Override
    public HashSet<DepartmentDataSet>  getEmployeeCountWithType(String employeeType) {
        HashSet<DepartmentDataSet> result = new HashSet<DepartmentDataSet>();

        DepartmentDAO departmentDAO = new DepartmentDAO();
            TreeMap<Integer, DepartmentDataSet> sortedByCountMap =
                    new TreeMap<Integer, DepartmentDataSet>(Collections.reverseOrder());
            ArrayList<DepartmentDataSet> departments = departmentDAO.getDepartments();
            if (departments != null && !departments.isEmpty()){
                for (DepartmentDataSet department : departments) {
                    ArrayList<EmployeeDataSet> employeesByType = new ArrayList<EmployeeDataSet>();
                    ArrayList<EmployeeDataSet> employees = departmentDAO.getEmployees(department.getId());
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

    @Override
    public ArrayList<EmployeeDataSet> getEmployeesFromDepartmentByAge(String departmentName, String age) {
        ArrayList<EmployeeDataSet> resultList = new ArrayList<EmployeeDataSet>();
        DepartmentDAO departmentDAO = new DepartmentDAO();
            DepartmentDataSet department = departmentDAO.getDepartment(departmentName);
            ArrayList<EmployeeDataSet> employeesList = departmentDAO.getEmployees(department.getId());
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
