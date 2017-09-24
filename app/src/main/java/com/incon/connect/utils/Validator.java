package com.incon.connect.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Za-z])(?=\\S+$).{8,15})";
    private static final String NAME_PATTERN = "^[a-zA-Z]+$";

    public static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUserName(String userName) {
        return userName.length() >= 6;
    }

    public static boolean isNameValid(String userName) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }
}

