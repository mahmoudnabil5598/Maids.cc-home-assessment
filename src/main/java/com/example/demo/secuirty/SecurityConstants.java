package com.example.demo.secuirty;

import io.jsonwebtoken.SignatureAlgorithm;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

public class SecurityConstants {
    public static final String SECRET_KEY = String.valueOf(secretKeyFor(SignatureAlgorithm.HS256)); //Your secret should always be strong (uppercase, lowercase, numbers, symbols) so that nobody can potentially decode the signature.
    public static final long AUTH_TOKEN_EXPIRATION = 300000L;
    public static final String AUTH_TOKEN_PATH = "/";
    public static final String AUTHENTICATE_PATH = "/authenticate"; // Public path that clients can use to Authenticate.
    public static final String AUTHENTICATE_PATTERN = "/authenticate/**";
    public static final String H2_PATTERN = "/h2-console/**";
    public static final String H2_DATABASE = "/h2-console/";
    public static final String AUTH_TOKEN = "AUTH-TOKEN";
    public static final String[] SWAGGER_PATTERN = {"/v3/api-docs/swagger-config/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui.html"};
    public static final String SWAGGER_CONFIG = "/v3/api-docs";
    public static final String SWAGGER = "/swagger";


}
