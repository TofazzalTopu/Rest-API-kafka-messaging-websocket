package com.info.demo.constant;

import org.springframework.stereotype.Component;

/**
 * @author Tofazzal
 */

@Component
public class AppConstants {
    public static final String API_VERSION = "v1";

    public static final String JWT_SECRET = "SECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITY";
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String LOGIN_PATH = "/api/v1/users/login";
    public static final String REGISTRATION_PATH = "/api/v1/users";
    public static final String SYSTEM_EMAIL = "manik.mmanik@gmail.com";
    public static final String EMAIL_SUBJECT = "Welcome to Square Bear";
    public static final String EMAIL_CONTENT = "You have registered successfully!";
    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            LOGIN_PATH,
            REGISTRATION_PATH
    };

    public static final String USER_REGISTERED_SUCCESS = "User saved successfully.";
    public static final String USER_NAME_ALREADY_EXIST = "User already exist with the username: ";
    public static final String USER_EMAIL_ALREADY_EXIST = "User already exist with the email: ";
    public static final String USER_FETCH_SUCCESS = "User fetch successfully.";
    public static final String LOGOUT_SUCCESS = "You have been logged out successfully.";
}
