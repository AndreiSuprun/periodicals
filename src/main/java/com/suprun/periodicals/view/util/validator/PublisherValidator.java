package com.suprun.periodicals.view.util.validator;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.util.validator.impl.DescriptionValidator;
import com.suprun.periodicals.view.util.validator.impl.PeriodicalPriceValidator;
import com.suprun.periodicals.view.util.validator.impl.TitleValidator;

import java.io.Serializable;
import java.util.Map;

public class PublisherValidator extends EntityValidator {

    @Override
    protected void validateObject(Map<String, Boolean> errors, Serializable object) {
        if (!(object instanceof Publisher)) {
            throw new IllegalArgumentException("The object has not Periodical type");
        }
        Publisher publisher = (Publisher) object;
        validateField(new TitleValidator(),
                publisher.getName(),
                Attributes.ERROR_PUBLISHER_NAME,
                errors);
    }
}
