package com.example.reactburgerapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${jwt.token.secret}")
    private String TOKEN_SECRET;
    @Value("${jwt.token.validity}")
    public Long TOKEN_VALIDITY;

    private final MongoUserService userService;

    @Autowired
    public TokenService(MongoUserService userService) {
        this.userService = userService;
    }

    public String createToken(String userId, String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Date now = new Date();
            return "Bearer " + JWT.create()
                    .withClaim("username", email)
                    .withClaim("userId", userId)
                    .withClaim("createdAt", now)
                    .withExpiresAt(new Date(now.getTime() + TOKEN_VALIDITY))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getUserDataFromToken(String token) {
        Map<String, String> result = new HashMap<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            result.put("email", jwt.getClaim("username").asString());
            result.put("id", jwt.getClaim("userId").asString());
            return result;
        } catch (UnsupportedEncodingException | JWTVerificationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Authentication getAuthentication(String token) {
        Map<String, String> userData = getUserDataFromToken(token);
        UserDetails userDetails = userService.loadUserByUsername(userData.get("email"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
