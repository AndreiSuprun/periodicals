package com.suprun.periodicals.view.util.validator.impl;

public class UserNameValidator extends RegExValidator {
    private final static int MAX_LENGTH = 255;
    private static final String USER_NAME_REGEX = "^\\p{Lu}[\\p{L}&&[^\\p{Lu}]]+$";

    public UserNameValidator() {
        super(USER_NAME_REGEX, MAX_LENGTH);
    }
}
