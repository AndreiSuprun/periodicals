package com.suprun.periodicals.view.util.validator.impl;

import com.suprun.periodicals.view.util.validator.Validator;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class for validating periodical price
 */
public class PeriodicalPriceValidator implements Validator<BigDecimal> {

    private final static BigDecimal MIN_VALUE = new BigDecimal(0);
    private final static BigDecimal MAX_VALUE = new BigDecimal(100000000L);

    @Override
    public boolean isValid(BigDecimal price) {
        return price != null && MIN_VALUE.compareTo(price) <= 0 && MAX_VALUE.compareTo(price) > 0;
    }
}
