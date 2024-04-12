package com.demoapp.dao;

import com.demoapp.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.UUID;

public class UserDAO extends GenericDAO<UserEntity, UUID> {
}
