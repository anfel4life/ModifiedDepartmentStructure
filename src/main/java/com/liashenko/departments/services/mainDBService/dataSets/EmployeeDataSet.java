package com.liashenko.departments.services.mainDBService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeDataSet  implements Serializable {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "department_id", updatable = true)
        private int departmentId;

        @Column(name = "age")
        private String age;

        @Column(name = "type",  updatable = false)
        private String type;

        @Column(name = "name", length = 25)
        private String name;

        @Column(name = "language", length = 25)
        private String language;

        @Column(name = "methodology", length = 25)
        private String methodology;

        public EmployeeDataSet() {
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
        return "EmployeeDataSet{" +
                " id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", type=" + type +
                ", departmentId=" + departmentId +
                ", language=" + language +
                ", methodology=" + methodology + " }\n";
    }
}
