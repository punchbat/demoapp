package com.demoapp.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAO<T> {
    private Session session;
    private Class<T> entityClass;

    public GenericDAO(Session session) {
        this.session = session;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return session;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public T find(Object id) {
        return session.get(entityClass, (java.io.Serializable) id);
    }

    public List<T> findAll() {
        return session.createQuery("from " + entityClass.getName(), entityClass).list();
    }

    public void save(T entity) {
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
    }

    public T update(T entity) {
        Transaction tx = session.beginTransaction();
        T updatedEntity = (T) session.merge(entity);
        tx.commit();
        return updatedEntity;
    }

    public void delete(T entity) {
        Transaction tx = session.beginTransaction();
        session.delete(entity);
        tx.commit();
    }
}
