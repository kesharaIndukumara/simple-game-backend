package com.navishkadarshana.smileygame.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReCaptchaInvalidException extends RuntimeException{
    private static final long serialVersionUID = 5861310537366287163L;
    private int status;
    private String message;

    public ReCaptchaInvalidException(String message) {
        this.message = message;
    }
}
