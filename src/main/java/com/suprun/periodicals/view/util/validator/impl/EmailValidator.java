package com.suprun.periodicals.view.util.validator.impl;

public class EmailValidator extends RegExValidator {
    private final static int MAX_LENGTH = 255;

    private static final String EMAIL_REGEX = "^(.+@.+\\..+)$";

    public EmailValidator() {
        super(EMAIL_REGEX, MAX_LENGTH);
    }
}
