package com.app.springbootjwtpostgres.security;

import org.slf4j.Logger; // This class provides methods for logging messages with different levels of severity.
import org.slf4j.LoggerFactory; // This class provides methods for obtaining logger instances.
import org.springframework.security.core.AuthenticationException; // This exception is thrown when an authentication request fails.
import org.springframework.security.web.AuthenticationEntryPoint; // This interface provides methods for handling unauthorized requests.
import org.springframework.stereotype.Component; // This annotation marks this class as a component that will be managed by Spring.

import jakarta.servlet.http.HttpServletRequest; // This class represents an HTTP request.
import jakarta.servlet.http.HttpServletResponse; // This class represents an HTTP response.
import java.io.IOException; // This exception is thrown when an input/output operation fails.

@Component // This annotation marks this class as a component that will be managed by Spring.
public class AuthEntryPointJwt implements AuthenticationEntryPoint { // This class implements AuthenticationEntryPoint interface and overrides its methods.

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class); // This line creates a logger instance for this class using LoggerFactory class.

    @Override // This annotation indicates that this method overrides a method from the interface.
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException { // This method handles unauthorized requests and sends back an error response.
        logger.error("Unauthorized error: {}", authException.getMessage()); // This line logs the error message using the logger instance with the error level.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized"); // This line sends an error response with the status code 401 (Unauthorized) and the error message using the response object.
    }

}