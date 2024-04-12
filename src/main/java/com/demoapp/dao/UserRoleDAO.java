package com.demoapp.dao;

import com.demoapp.entity.UserRoleEntity;
import org.hibernate.Session;

public class UserRoleDAO extends GenericDAO<UserRoleEntity> {
    public UserRoleDAO(Session session) {
        super(session);
    }
}
