package com.suprun.periodicals.view.util.validator.impl;

/**
 * Class for validating user first and last name
 */
public class UserNameValidator extends RegExValidator {

    private static final int MAX_LENGTH = 255;
    private static final String USER_NAME_REGEX = "^\\p{Lu}[\\p{L}&&[^\\p{Lu}]]+$";

    public UserNameValidator() {
        super(USER_NAME_REGEX, MAX_LENGTH);
    }
}
