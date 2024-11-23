package com.firozkhan.server.jwt;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.firozkhan.server.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

    private final String SECRET_KEY = "015075a96887af668c916a1994c43d818468baa29025347670479f6ea2924485";

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("firozkhan")
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("email", user.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60))
                .withJWTId(UUID.randomUUID().toString())
                .sign(getAlgorithm());
    }

    private JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm())
                .withIssuer("firozkhan")
                .build();
    }

    public Map<String, Claim> extractAllClaims(String token) {
        JWTVerifier verifier = getVerifier();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaims();
        } catch (JWTVerificationException e) {
            log.error("Token verification failed for token: {}", token, e);
            return null;
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token, DecodedJWT::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, DecodedJWT::getExpiresAt);
    }

    private <T> T extractClaims(String token, java.util.function.Function<DecodedJWT, T> claimsResolver) {
        DecodedJWT decodedJWT = getVerifier().verify(token);
        return claimsResolver.apply(decodedJWT);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
