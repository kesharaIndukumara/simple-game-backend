package com.navishkadarshana.smileygame.exception;

public class ReCaptchaUnavailableException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public ReCaptchaUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
