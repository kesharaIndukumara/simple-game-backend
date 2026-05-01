package com.navishkadarshana.smileygame.config.security;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */

public class SecurityConstants {

    public static final String ADMIN_ID = "admin";
    public static final String PLAYER_ID = "player";


    protected static final int ADMIN_ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 24 * 60 * 60;
    protected static final int ADMIN_REFRESH_TOKEN_VALIDITY_SECONDS = 90 * 24 * 60 * 60;

    protected static final int PLAYER_ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 24 * 60 * 60;
    protected static final int PLAYER_REFRESH_TOKEN_VALIDITY_SECONDS = 90 * 24 * 60 * 60;



    protected static final String GRANT_TYPE_PASSWORD = "password";
    protected static final String AUTHORIZATION_CODE = "authorization_code";
    protected static final String REFRESH_TOKEN = "refresh_token";
    protected static final String IMPLICIT = "implicit";
    protected static final String SCOPE_READ = "read";
    protected static final String SCOPE_WRITE = "write";
    protected static final String TRUST = "trust";
    protected static final String TOKEN_SIGN_IN_KEY = "9ebe3fcb950668a2dcaea81bf12ae8ba/ctn@22";

}
