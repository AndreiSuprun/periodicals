package com.suprun.periodicals.view.util.validator;

/**
 * Interface for entity fields validation
 *
 * @author Andrei Suprun
 */
public interface Validator<T> {

    /**
     * Check if input message is valid
     *
     * @param obj that need to check
     * @return {@code true} if input is valid
     * {@code false} if input is not valid
     */
    boolean isValid(T obj);
}
