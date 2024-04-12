package com.demoapp;

import com.demoapp.exception.mapper.ClientExceptionMapper;
import com.demoapp.exception.mapper.SystemException;
import com.demoapp.exception.mapper.SystemExceptionMapper;
import com.demoapp.exception.mapper.ValidationExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class DemoAppApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ClientExceptionMapper.class);
        classes.add(SystemExceptionMapper.class);
        classes.add(ValidationExceptionMapper.class);
        return classes;
    }
}