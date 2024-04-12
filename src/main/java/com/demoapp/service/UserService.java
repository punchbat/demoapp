package com.demoapp.service;

import com.demoapp.middleware.LoggingFilter;
import com.demoapp.util.HibernateUtil;
import com.demoapp.dao.UserRoleDAO;
import com.demoapp.dto.request.SignInRequestDTO;
import com.demoapp.dto.request.SignUpRequestDTO;
import com.demoapp.dto.request.UpdateProfileRequestDTO;
import com.demoapp.dto.response.UserResponseDTO;
import com.demoapp.entity.UserEntity;
import com.demoapp.dao.UserDAO;
import com.demoapp.dto.response.UserAndTokenResponseDTO;
import com.demoapp.entity.UserRoleEntity;
import com.demoapp.exception.DatabaseException;
import com.demoapp.exception.NotFoundException;
import com.demoapp.exception.ServiceException;
import com.demoapp.exception.SignInException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import java.net.HttpURLConnection;
import java.util.UUID;

public class UserService {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    private UserDAO userDAO;
    private UserRoleDAO userRoleDAO;

    public UserService() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        this.userDAO = new UserDAO(session);
        this.userRoleDAO = new UserRoleDAO(session);
    }

    public UserAndTokenResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        UserRoleEntity userRole = userRoleDAO.find(UUID.fromString(signUpRequestDTO.getRoleID()));
        if (userRole == null) {
            Error err = new Error("User role not found with ID: " + signUpRequestDTO.getRoleID());
            throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
        }

        String hashedPassword = UserEntity.bcryptPassword(signUpRequestDTO.getPassword());

        UserEntity user = UserEntity
                .builder()
                .role(userRole)
                .name(signUpRequestDTO.getName())
                .surname(signUpRequestDTO.getSurname())
                .age(signUpRequestDTO.getAge())
                .email(signUpRequestDTO.getEmail())
                .password(hashedPassword)
                .build();

        try {
            userDAO.save(user);
            logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":created new user");
        } catch (Exception e) {
            Error err = new Error("Error saving user to the database");
            throw new DatabaseException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
        }

        String token;
        try {
            token = JWTService.createJWT(user);
            logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":created new jwt token");
        } catch (Exception e) {
            Error err = new Error("Error creating JWT for the user");
            throw new ServiceException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
        }

        logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
        return new UserAndTokenResponseDTO(new UserResponseDTO(user), token);
    }

    public UserAndTokenResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        UserEntity user = userDAO.findByEmail(signInRequestDTO.getEmail());

        if (user == null) {
            Error err = new Error("User not found with email: " + signInRequestDTO.getEmail());
            throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
        }

        if (user.isPasswordEqual(signInRequestDTO.getPassword())) {
            String token;
            try {
                token = JWTService.createJWT(user);
                logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":created new jwt token");
            } catch (Exception e) {
                Error err = new Error("Error creating JWT for the user");
                throw new ServiceException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
            }

            logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
            return new UserAndTokenResponseDTO(new UserResponseDTO(user), token);
        }

        Error err = new Error("Email or Password is not correct");
        throw new SignInException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
    }

    public UserResponseDTO updateProfile(UpdateProfileRequestDTO updateProfileRequestDTO) {
        logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        UserEntity existingUser = userDAO.find(UUID.fromString(updateProfileRequestDTO.getId()));
        if (existingUser == null) {
            Error err = new Error("User not found with email: " + updateProfileRequestDTO.getId());
            throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
        }

        boolean isUpdated = false;

        String roleID = updateProfileRequestDTO.getRoleID();
        if (roleID != null && !roleID.isEmpty()) {
            UserRoleEntity userRole = userRoleDAO.find(UUID.fromString(roleID));
            if (userRole == null) {
                Error err = new Error("User role not found with ID: " + roleID);
                throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
            }

            existingUser.setRole(userRole);
            isUpdated = true;
        }

        String name = updateProfileRequestDTO.getName();
        if (name != null && !name.isEmpty()) {
            existingUser.setName(name);
            isUpdated = true;
        }

        String surname = updateProfileRequestDTO.getSurname();
        if (surname != null && !surname.isEmpty()) {
            existingUser.setSurname(surname);
            isUpdated = true;
        }

        int age = updateProfileRequestDTO.getAge();
        if (age > 0) {
            existingUser.setAge(age);
            isUpdated = true;
        }

        if (isUpdated) {
            try {
                userDAO.update(existingUser);
                logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":updated user");
            } catch (Exception e) {
                Error err = new Error("Error updating user");
                throw new DatabaseException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
            }
        }

        logger.info(UserService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
        return new UserResponseDTO(existingUser);
    }
}
