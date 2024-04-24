package com.lvucko.cookshare.validators;

import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserValidator {
    public static boolean isValidUsername(String username){
        Pattern VALID_USERNAME_REGEX = Pattern.compile("^[A-Za-z]\\w{5,29}$");
        Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
        return matcher.matches();
    }
    public static boolean isValidPassword(String password){
        Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$");
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }
    public static boolean isValidPhone(String phone){
        Pattern VALID_PHONE_REGEX = Pattern.compile("((\\(\\d{3}\\) ?)|(\\d{3}-))?\\d{3}-\\d{4}");
        Matcher matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.matches();
    }
    public static boolean isValidEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}
