package com.demoapp.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String role;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String role, String name, String surname, int age, String email, String password) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void bcryptPassword() {
        this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    public boolean isPasswordEqual(String password) {
        return BCrypt.checkpw(password, this.password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
