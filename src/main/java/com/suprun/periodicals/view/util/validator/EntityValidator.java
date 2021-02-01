package com.suprun.periodicals.view.util.validator;

import com.suprun.periodicals.view.command.Command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Manager for validation data in {@link Command#execute} methods
 *
 * @author Andrei Suprun
 */
public abstract class EntityValidator {

    public Map<String, Boolean> validate(Serializable object) {
        Map<String, Boolean> errors = new HashMap<>();
        validateObject(errors, object);
        return errors;
    }

    protected abstract void validateObject(Map<String, Boolean> errors,
                                           Serializable object);

    /**
     * Performs validation of given field with provided validator.
     * If error occurs add {@code ValidationResult} object with error message to list of validation results.
     *
     * @param validator      implementation of {@code Validator}
     * @param field          field for validation
     * @param errorAttribute attribute name for presentation
     * @param errors         container for all {@code ValidationResult}
     * @param <T>            type of field for validation
     * @see Validator
     */
    protected <T> void validateField(Validator<T> validator,
                                     T field,
                                     String errorAttribute,
                                     Map<String, Boolean> errors) {
        if (!validator.isValid(field)) {
            errors.put(errorAttribute, true);
        }
    }
}
