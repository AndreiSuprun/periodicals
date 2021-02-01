package com.suprun.periodicals.view.util.validator;

public class EntityValidatorFactory {
    public static EntityValidator getSignInValidator() {
        return new SignInValidator();
    }

    public static EntityValidator getRegisterValidator() {
        return new RegisterValidator();
    }

    public static EntityValidator getPeriodicalValidator() {
        return new PeriodicalValidator();
    }

    public static EntityValidator getPublisherValidator() {
        return new PublisherValidator();
    }
}
