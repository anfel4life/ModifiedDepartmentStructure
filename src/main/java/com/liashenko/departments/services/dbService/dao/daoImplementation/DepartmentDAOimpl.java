package com.liashenko.departments.services.dbService.dao.daoImplementation;

import com.liashenko.departments.services.dbService.HibernateFactoryUtil;
import com.liashenko.departments.services.dbService.dao.DepartmentDAO;
import com.liashenko.departments.services.dbService.dataSets.DepartmentDataSet;
import com.liashenko.departments.services.dbService.dataSets.EmployeeDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;

public class DepartmentDAOimpl extends EntityDAOimpl implements DepartmentDAO {

    public boolean removeEntity(int entityId) {
        try {
            startOperation();
            session.delete(session.get(DepartmentDataSet.class, entityId));
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            HibernateFactoryUtil.close(session);
        }
        return true;
    }

    public ArrayList<DepartmentDataSet> getEntities() {
        ArrayList<DepartmentDataSet> entitiesList = null;
        try {
            startOperation();
            Criteria cr = session.createCriteria(DepartmentDataSet.class);
            entitiesList = (ArrayList<DepartmentDataSet>) cr.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactoryUtil.close(session);
        }
        return entitiesList;
    }

    public ArrayList<EmployeeDataSet> getEntityChildren(int entityId) {
        ArrayList<EmployeeDataSet> entityChildrenList = null;
        try {
            openSession();
            Criteria cr = session.createCriteria(EmployeeDataSet.class);
            entityChildrenList = (ArrayList<EmployeeDataSet>) cr.add(Restrictions
                    .eq("departmentId", entityId)).list();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactoryUtil.close(session);
        }
        return entityChildrenList;
    }

    public DepartmentDataSet getEntity(String entityName) {
        DepartmentDataSet entity = null;
        try {
            openSession();
            Criteria cr = session.createCriteria(DepartmentDataSet.class);
            entity = ((DepartmentDataSet) cr.add(Restrictions.eq("name", entityName)).uniqueResult());
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactoryUtil.close(session);
        }
        return entity;
    }
}
