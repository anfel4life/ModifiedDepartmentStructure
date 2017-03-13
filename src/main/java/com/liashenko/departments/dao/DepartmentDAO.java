package com.liashenko.departments.dao;


import com.liashenko.departments.services.database.entities.DepartmentDataSet;
import com.liashenko.departments.services.database.entities.EmployeeDataSet;

import java.util.ArrayList;

public interface DepartmentDAO extends EntityDAO {
    boolean removeEntity(int entityId);

    ArrayList<DepartmentDataSet> getEntities();

    ArrayList<EmployeeDataSet> getEntityChildren(int entityId);

    DepartmentDataSet getEntity(String entityName);
}
