package com.suprun.periodicals.view.util.validator;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.view.command.impl.PostSignInCommand;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.util.validator.impl.EmailValidator;
import com.suprun.periodicals.view.util.validator.impl.PasswordValidator;

import java.io.Serializable;
import java.util.Map;

/**
 * Validation data in {@link PostSignInCommand#execute}
 *
 * @author Andrei Suprun
 */
public class SignInValidator extends EntityValidator {

    @Override
    protected void validateObject(Map<String, Boolean> errors, Serializable object) {
        if (!(object instanceof User)) {
            throw new IllegalArgumentException("The object is not a type of User");
        }
        User user = (User) object;
        validateField(new EmailValidator(),
                user.getEmail(),
                Attributes.ERROR_EMAIL,
                errors);
        validateField(new PasswordValidator(),
                user.getPassword(),
                Attributes.ERROR_PASSWORD,
                errors);
    }
}
