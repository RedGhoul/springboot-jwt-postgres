package com.app.springbootjwtpostgres.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // This annotation marks this class as a component that will be managed by Spring.
public class JwtUtils {

    @Value("${jwt.secret}") // This annotation injects the value of jwt.secret property from application properties file into this field.
    private String jwtSecret; // This field stores the secret key used to sign the JWT tokens.

    @Value("${jwt.expiration}") // This annotation injects the value of jwt.expiration property from application properties file into this field.
    private String jwtExpirationMs; // This field stores the expiration time of the JWT tokens in milliseconds.
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    public String generateJwtToken(Authentication authentication) { // This method generates a JWT token based on an Authentication object and returns it as a string.

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal(); // This line gets the UserDetailsImpl object from the Authentication object.

        return Jwts.builder() // This line creates a new JWT builder object using Jwts class.
                .setSubject((userPrincipal.getUsername())) // This line sets the subject (username) of the JWT token using the UserDetailsImpl object.
                .setIssuedAt(new Date()) // This line sets the issued at date (current date) of the JWT token using Date class.
                .setExpiration(new Date((new Date()).getTime() + Long.parseLong(jwtExpirationMs))) // This line sets the expiration date (current date plus expiration time) of the JWT token using Date class.
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // This line signs the JWT token with HS512 algorithm and the secret key.
                .compact(); // This line compacts the JWT token into a string and returns it.
    }

    public String getUserNameFromJwtToken(String token) { // This method extracts the username from a JWT token and returns it as a string.
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject(); // This line parses the JWT token using the secret key, gets the claims body, and gets the subject (username) and returns it.
    }

    public boolean validateJwtToken(String authToken) { // This method validates a JWT token and returns true or false.
        try { // This block tries to parse the JWT token using the secret key and catch any exception that may occur.
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken); // This line parses the JWT token using the secret key.
            return true; // If no exception occurs, it returns true.
        } catch (SignatureException e) { // This block catches a SignatureException, which occurs when the signature of the JWT token is invalid.
            logger.error("Invalid JWT signature: {}", e.getMessage()); // This line prints the error message to the standard error stream.
        } catch (MalformedJwtException e) { // This block catches a MalformedJwtException, which occurs when the format of the JWT token is invalid.
            logger.error("Invalid JWT token: {}", e.getMessage()); // This line prints the error message to the standard error stream.
        } catch (ExpiredJwtException e) { // This block catches an ExpiredJwtException, which occurs when the expiration date of the JWT token is past.
            logger.error("JWT token is expired: {}", e.getMessage()); // This line prints the error message to the standard error stream.
        } catch (UnsupportedJwtException e) { // This block catches an UnsupportedJwtException, which occurs when the type of the JWT token is not supported.
            logger.error("JWT token is unsupported: {}", e.getMessage()); // This line prints the error message to the standard error stream.
        } catch (IllegalArgumentException e) { // This block catches an IllegalArgumentException, which occurs when the argument of the JWT token is invalid or null.
            logger.error("JWT claims string is empty: {}", e.getMessage()); // This line prints the error message to the standard error stream.
        }

        return false; // If any exception occurs, it returns false.
    }

}
