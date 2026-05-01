package com.navishkadarshana.smileygame.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse {

    private boolean success;
    private String message;
    private int code;
}
