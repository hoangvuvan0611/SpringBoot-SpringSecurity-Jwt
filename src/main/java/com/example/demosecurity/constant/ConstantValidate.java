package com.example.demosecurity.constant;

import java.util.regex.Pattern;

public class ConstantValidate {
    public static final String VALID_EMAIL_ADDRESS_REGEX =
            "^[0-9a-zA-Z]+\\w+@\\w+(\\.\\w+)*(\\.[a-zA-Z]{2,6})$";

    public static final Pattern VALID_EMAIL_ADDRESS =
            Pattern.compile(VALID_EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);

    public static final String VALID_PASSWORD_REGEX = ".{8,50}";
    public static final Pattern VALID_PASSWORD =
            Pattern.compile(VALID_PASSWORD_REGEX, Pattern.CASE_INSENSITIVE);
}
