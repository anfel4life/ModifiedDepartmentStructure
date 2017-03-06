package com.liashenko.departments.services.mainDBService.dao;


import org.hibernate.Session;

import java.io.Serializable;

public abstract class EntityDAO {
    protected Session session;

    public EntityDAO(Session session) {
        this.session = session;
    }

}
