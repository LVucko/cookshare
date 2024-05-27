package com.lvucko.cookshare.exceptions;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message){
        super(message);
    }
}
