package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ContactMustHaveEmailValidator.class})
public @interface ContactMustHaveEmail {
    String message() default "The primary contact must provide an email address.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
