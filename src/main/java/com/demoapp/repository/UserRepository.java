package com.demoapp.repository;

import com.demoapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static List<User> data = new ArrayList<>();

    static {
        data.add(new User(1, "ADMIN", "Zawur", "Abdu", 22, "admin@gmial.com", "admin"));
        data.add(new User(2, "USER", "Maksat", "Eles", 32, "example@gmial.com", "12345678"));
    }

    public UserRepository() {
    }

    public List<User> listAll() {
        return new ArrayList<User>(data);
    }

    public int add(User User) {
        int newId = data.size() + 1;
        User.setId(newId);
        data.add(User);

        return newId;
    }

    public User get(int id) {
        User UserToFind = new User(id);
        int index = data.indexOf(UserToFind);
        if (index >= 0) {
            return data.get(index);
        }
        return null;
    }

    public boolean delete(int id) {
        User UserToFind = new User(id);
        int index = data.indexOf(UserToFind);
        if (index >= 0) {
            data.remove(index);
            return true;
        }

        return false;
    }

    public User update(User User) {
        int index = data.indexOf(User);
        if (index >= 0) {
            User user = data.set(index, User);
            return user;
        }
        return null;
    }
}
