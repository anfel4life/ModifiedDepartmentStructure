package com.liashenko.departments.services.mainDBService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "DEPARTMENT")
public class DepartmentDataSet implements Serializable  {
//    private static final long serialVersionUID = -8706689714326132798L;
//private String nodeType = DEPARTMENT_NODE_TYPE;

    @SuppressWarnings("UnusedDeclaration")
    public DepartmentDataSet() {
    }

    public DepartmentDataSet(String name) {
//        this.setId(-1);
        this.setName(name);
    }

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = false, updatable = true, length = 45)
    private String name;

    @OneToMany(mappedBy = "departmentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EmployeeDataSet> employeesSet;

    public Set<EmployeeDataSet> getEmployeesSet() {
        return employeesSet;
    }

//    public void setEmployeesSet(Set<EmployeeDataSet> employeesSet) {
//        this.employeesSet = employeesSet;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id + "', name='" + name + '\'' + '}';
    }

//    @Override
//    public int getNodeId() {
//        return id;
//    }
//
//    @Override
//    public String getNodeType() {
//        return nodeType;
//    }
}
