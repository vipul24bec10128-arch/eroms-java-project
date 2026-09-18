package com.eroms.util;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPositiveNumber(double value) {
        return value > 0.0;
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}