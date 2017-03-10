package com.liashenko.departments.services.dbService.dao;


public interface EntityDAO<T> {
    boolean removeEntity (int entityId);

    boolean insertEntity (T entity);
}
