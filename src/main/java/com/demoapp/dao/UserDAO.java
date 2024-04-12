package com.demoapp.dao;

import com.demoapp.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO extends GenericDAO<UserEntity> {
    public UserDAO(Session session) {
        super(session);
    }

    public UserEntity findByEmail(String email) {
        String hql = "FROM UserEntity WHERE email = :email";
        Query<UserEntity> query = this.getSession().createQuery(hql, UserEntity.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }
}
