package com.navishkadarshana.smileygame.exception.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */


@Getter
@Setter
@ToString
public class OtpValidException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;
    private int code;

    public OtpValidException(String message) {
        super(message);
        this.message = message;
    }

    public OtpValidException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public OtpValidException(int code, String message, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    public OtpValidException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public OtpValidException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
