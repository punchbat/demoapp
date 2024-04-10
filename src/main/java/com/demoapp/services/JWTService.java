package com.demoapp.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.demoapp.models.User;

import java.util.Date;

public class JWTService {
    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 900_000; // 15 минут

    public static String createJWT(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("role", user.getRole())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static DecodedJWT verifyJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            // Невалидный токен
            return null;
        }
    }
}
