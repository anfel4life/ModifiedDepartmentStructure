package com.liashenko.departments.services.mainDBService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeDataSet  implements Serializable {
//        private static final long serialVersionUID = -8706689714326132798L;
//    private String nodeType = EMPLOYEE_NODE_TYPE;
//    private DepartmentDataSet departmentDataSet;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }

        @Column(name = "department_id", unique = false, updatable = true)
        private int departmentId;

        @Column(name = "age", unique = false, updatable = true)
        private String age;

        @Column(name = "type", unique = false, updatable = false)
        private String type;

        @Column(name = "name", unique = false, updatable = true, length = 25)
        private String name;

        @Column(name = "language", unique = false, updatable = true, length = 25)
        private String language;


        @Column(name = "methodology", unique = false, updatable = true, length = 25)
        private String methodology;

        @SuppressWarnings("UnusedDeclaration")
        public EmployeeDataSet() {
//            this.nodeType = NodeGenerator.EMPLOYEE_NODE_TYPE;
        }

        public EmployeeDataSet(String name, String type, String age, int department_id, String methodology,
                               String language) {
            this.setName(name);
            this.setType(type);
            this.setAge(age);
            this.setDepartmentId(department_id);
            this.setMethodology(methodology);
            this.setLanguage(language);
        }

        public EmployeeDataSet(int id, String name, String type, String age, int department_id, String methodology,
                               String language) {
            this.setId(id);
            this.setName(name);
            this.setType(type);
            this.setAge(age);
            this.setDepartmentId(department_id);
            this.setMethodology(methodology);
            this.setLanguage(language);
        }

//    @ManyToOne
//    @JoinColumn(name = "departmentId")
//    public DepartmentDataSet getDepartment() {
//        return category;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + name + "', age='" + age +
                ", type='" + departmentId + '\'' +
                '}';
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
