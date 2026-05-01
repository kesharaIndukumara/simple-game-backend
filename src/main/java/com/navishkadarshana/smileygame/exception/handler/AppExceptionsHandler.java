package com.navishkadarshana.smileygame.exception.handler;


import com.navishkadarshana.smileygame.exception.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */

@ControllerAdvice
@Log4j2
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomServiceException.class})
    public ResponseEntity<ErrorMessageResponse> handleServiceException(CustomServiceException ex, WebRequest webRequest) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponse(false, ex.getMessage(), ex.getCode()), HttpStatus.OK);
    }

    @ExceptionHandler(value = {OtpValidException.class})
    public ResponseEntity<ErrorMessageResponse> handleOtpException(OtpValidException ex, WebRequest webRequest) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponse(true, ex.getMessage(), ex.getCode()), HttpStatus.OK);
    }


    @ExceptionHandler(value = {CustomAuthenticationException.class})
    public ResponseEntity<ErrorMessageResponse> handleAuthenticationException(CustomAuthenticationException ex, WebRequest webRequest) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageResponse(false, ex.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class, CustomAccessDeniedException.class})
    public ResponseEntity<ErrorMessageResponse> handleAccessDeniedException(CustomAccessDeniedException ex, WebRequest webRequest) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageResponse(false, ex.getMessage() != null ?
                ex.getMessage() : "Access denied", HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ErrorMessageResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest webRequest) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageResponse(false, "User not found", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<?> handleAllExceptions(MethodArgumentTypeMismatchException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    //handling custom validation error
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        String msg = errorList.isEmpty() ? "Invalid request data" : errorList.get(0);
        return new ResponseEntity<>(new ErrorMessageResponse(false, msg , HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
