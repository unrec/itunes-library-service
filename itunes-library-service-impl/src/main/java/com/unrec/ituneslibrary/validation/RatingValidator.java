package com.unrec.ituneslibrary.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RatingValidator implements ConstraintValidator<Rating, Integer> {

    private static final List<Integer> RATINGS = List.of(0, 20, 40, 60, 80, 100);

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return RATINGS.contains(value);
    }

    @Override
    public void initialize(Rating constraintAnnotation) {
        // NOP
    }
}