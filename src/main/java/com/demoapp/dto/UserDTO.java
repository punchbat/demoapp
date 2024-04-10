package com.demoapp.dto;

import com.demoapp.models.User;

public class UserDTO {
    private int id;
    private String role;
    private String name;
    private String surname;
    private int age;
    private String email;

    public UserDTO() {}

    public UserDTO(int id) {
        this.id = id;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.age = user.getAge();
        this.email = user.getEmail();
    }

    public UserDTO(int id, String role, String name, String surname, int age, String email) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


