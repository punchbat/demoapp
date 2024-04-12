package com.demoapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.demoapp.entity.UserEntity;
import com.demoapp.middleware.LoggingFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class JWTService {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 900_000; // 15 минут

    public static String createJWT(UserEntity user) {
        logger.info(JWTService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":create jwt token");

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("roleName", user.getRole().getName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static DecodedJWT verifyJWT(String token) {
        logger.info(JWTService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":verify jwt token");

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            // Невалидный токен
            return null;
        }
    }
}
