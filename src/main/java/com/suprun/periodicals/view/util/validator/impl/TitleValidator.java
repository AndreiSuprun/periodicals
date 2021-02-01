package com.suprun.periodicals.view.util.validator.impl;

public class TitleValidator extends RegExValidator {
    private final static int MAX_LENGTH = 255;

    private static final String TITLE_REGEX = "^[\\p{L}\\p{Digit}\\p{javaWhitespace}]+$";

    public TitleValidator() {
        super(TITLE_REGEX, MAX_LENGTH);
    }
}
