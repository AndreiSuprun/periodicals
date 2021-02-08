package com.suprun.periodicals.view.util.validator.impl;

/**
 * Class for validating user email
 */
public class EmailValidator extends RegExValidator {

    private static final int MAX_LENGTH = 255;
    private static final String EMAIL_REGEX = "^(.+@.+\\..+)$";

    public EmailValidator() {
        super(EMAIL_REGEX, MAX_LENGTH);
    }
}
