package com.suprun.periodicals.view.util.validator;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.view.command.impl.PostRegisterCommand;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.util.validator.impl.EmailValidator;
import com.suprun.periodicals.view.util.validator.impl.PasswordValidator;
import com.suprun.periodicals.view.util.validator.impl.UserNameValidator;

import java.io.Serializable;
import java.util.Map;

/**
 * Validation data in {@link PostRegisterCommand#execute}
 *
 * @author Andrei Suprun
 */
public class RegisterValidator extends EntityValidator {

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
        validateField(new UserNameValidator(),
                user.getFirstName(),
                Attributes.ERROR_FIRST_NAME,
                errors);
        validateField(new UserNameValidator(),
                user.getLastName(),
                Attributes.ERROR_LAST_NAME,
                errors);
    }
}
