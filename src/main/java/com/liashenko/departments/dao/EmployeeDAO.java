package com.liashenko.departments.dao;


import com.liashenko.departments.services.database.entities.EmployeeDataSet;

public interface EmployeeDAO extends EntityDAO {
    EmployeeDataSet getEntity(int entityId);

    boolean updateEntity(EmployeeDataSet entityToUpdate);

    boolean removeEntity(int entityId);
}
