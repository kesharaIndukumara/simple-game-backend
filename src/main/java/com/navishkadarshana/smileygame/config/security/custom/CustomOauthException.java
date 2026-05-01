package com.navishkadarshana.smileygame.config.security.custom;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    public CustomOauthException(String msg) {
        super(msg);
    }
}
