package com.liashenko.departments.services.dbService.dao;


import com.liashenko.departments.services.dbService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.dbService.dataSets.EmployeeDataSet;

import java.util.ArrayList;

public interface DepartmentDAO extends EntityDAO {
    boolean removeEntity(int entityId);

    ArrayList<DepartmentDataSet> getEntities();

    ArrayList<EmployeeDataSet> getEntityChildren(int entityId);

    DepartmentDataSet getEntity(String entityName);
}
