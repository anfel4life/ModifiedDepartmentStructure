package com.liashenko.departments.services.mainDBService.dataSets;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "DEPARTMENT")
public class DepartmentDataSet implements Serializable  {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = false, updatable = true, length = 45)
    private String name;

    @OneToMany(mappedBy = "departmentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<EmployeeDataSet> employeesSet;

    public DepartmentDataSet() {
    }

    public DepartmentDataSet(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeDataSet> getEmployeesSet() {
        return employeesSet;
    }

    public void setEmployeesSet(Set<EmployeeDataSet> employeesSet) {
        this.employeesSet = employeesSet;
    }

    @Override
    public String toString() {
        return "DepartmentDataSet{" +
                "id=" + id + "', name='" + name + '\'' + '}';
    }
}
