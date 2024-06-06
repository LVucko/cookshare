package com.lvucko.cookshare.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceNotFoundException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {FileUploadException.class})
    public ResponseEntity<Object> handleFileUploadException(FileUploadException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {UserRegistrationException.class})
    public ResponseEntity<Object> handleUserRegistrationException(UserRegistrationException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<Object> handleSQLException(SQLException e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {InstanceNotFoundException.class, EmptyResultDataAccessException.class, UserNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(Exception e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException(Exception e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {UserLoginException.class})
    public ResponseEntity<Object> handleUserLoginException(UserLoginException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<Object> handleJwtException(JwtException e){
        System.out.println("handling JWT exception: ");
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<Object> handleSignatureException(SignatureException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> HandleAllOtherExceptions(Exception e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
