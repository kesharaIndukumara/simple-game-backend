package com.navishkadarshana.smileygame.exception.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */


@Getter
@Setter
@ToString
public class CustomAccessDeniedException extends AccessDeniedException {

    private int status;
    private String message;

    public CustomAccessDeniedException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
