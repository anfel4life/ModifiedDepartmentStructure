package com.liashenko.departments.services.mainDBService.dao;


import org.hibernate.Session;

import java.io.Serializable;

public abstract class EntityDAO {
    protected Session session;

    public EntityDAO(Session session) {
        this.session = session;
    }

    public boolean deleteById(Class<?> type, Serializable id) {
        Object persistentInstance = session.load(type, id);
        if (persistentInstance != null) {
            session.delete(persistentInstance);
            return true;
        }
        return false;
    }
}
