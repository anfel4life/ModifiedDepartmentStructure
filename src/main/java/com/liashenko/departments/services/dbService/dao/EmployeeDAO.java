package com.liashenko.departments.services.dbService.dao;


import com.liashenko.departments.services.dbService.dataSets.EmployeeDataSet;

public interface EmployeeDAO extends EntityDAO {
    EmployeeDataSet getEntity(int entityId);

    boolean updateEntity(EmployeeDataSet entityToUpdate);

    boolean removeEntity(int entityId);
}
