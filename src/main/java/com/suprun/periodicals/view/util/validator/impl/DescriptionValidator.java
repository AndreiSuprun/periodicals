package com.suprun.periodicals.view.util.validator.impl;

public class DescriptionValidator extends RegExValidator {
    private final static int MAX_LENGTH = 510;

    private static final String DESCRIPTION_REGEX = "^[\\p{L}\\p{Digit}\\p{Punct}\\p{javaWhitespace}]+$";

    public DescriptionValidator() {
        super(DESCRIPTION_REGEX, MAX_LENGTH);
    }
}
