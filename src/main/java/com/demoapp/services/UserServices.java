package com.demoapp.services;

import com.demoapp.dto.UserDTO;
import com.demoapp.models.User;
import com.demoapp.repository.UserRepository;
import com.demoapp.dto.UserAndTokenDTO;

public class UserServices {
    private UserRepository userRepository = new UserRepository();

    public UserAndTokenDTO signUp(User user) {
        user.bcryptPassword();
        int newId = userRepository.add(user);
        user = userRepository.get(newId);
        if (user != null) {
            String token = JWTService.createJWT(user);
            return new UserAndTokenDTO(new UserDTO(user), token);
        }

        return null;
    }
    public UserAndTokenDTO signIn(String email, String password) {
        for (User user : userRepository.listAll()) {
            if (user.getEmail().equals(email) && user.isPasswordEqual(password)) {
                String token = JWTService.createJWT(user);
                return new UserAndTokenDTO(new UserDTO(user), token);
            }
        }

        return null;
    }
    public UserDTO updateProfile(int id, User user) {
        user.setId(id);
        return new UserDTO(userRepository.update(user));
    }

}
