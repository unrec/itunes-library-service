package com.unrec.ituneslibrary.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RatingValidator.class})
@Documented
public @interface Rating {

    String message() default "Invalid rating value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
