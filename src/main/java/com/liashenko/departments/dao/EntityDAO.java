package com.liashenko.departments.dao;


public interface EntityDAO<T> {
    boolean removeEntity(int entityId);

    boolean insertEntity(T entity);
}
