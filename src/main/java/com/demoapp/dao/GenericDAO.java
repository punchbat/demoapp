package com.demoapp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import com.demoapp.util.HibernateUtil;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

public abstract class GenericDAO<T, ID extends Serializable> {
    private Class<T> entityClass;

    public GenericDAO() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public void update(T o) {
        getSession().update(o);
    }

    public void delete(T o) {
        getSession().delete(o);
    }

    public void saveOrUpdate(T o) {
        getSession().saveOrUpdate(o);
    }

    public ID save(T o) {
        return (ID) getSession().save(o);
    }

    public Long count() {
        return (Long) getSession().createCriteria(getEntityClass().getName()).setProjection(Projections.rowCount()).uniqueResult();
    }

    public T find(ID id) {
        return (T) getSession().get(getEntityClass(), id);
    }

    public T findOne(T example) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Example.create(example));
        return (T) criteria.uniqueResult(); 
    }

    public List<T> findAll() {
        Query query = getSession().createQuery("from " + getEntityClass().getName());
        return query.list();
    }

    public List<T> find(Integer limit, Integer offset, String order, String sort) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        addLimitOffsetSortOrder(criteria, limit, offset, sort, order);
        return criteria.list();
    }

    public List<T> find(T example) {
        List<T> list = getSession().createCriteria(getEntityClass()).add(Example.create(example)).list();
        return list;
    }

    protected void addLimitOffsetSortOrder(Criteria criteria, Integer limit, Integer offset, String sort, String order) {
        if (limit != null)
        {
            criteria.setMaxResults(limit);
        }
        if (offset != null)
        {
            criteria.setFirstResult(offset);
        }

        if (order != null && sort != null)
        {
            if (order.equalsIgnoreCase("asc"))
            {
                criteria.addOrder(Order.asc(sort));
            }
            else if (order.equalsIgnoreCase("desc"))
            {
                criteria.addOrder(Order.desc(sort));
            }
        }
    }
}