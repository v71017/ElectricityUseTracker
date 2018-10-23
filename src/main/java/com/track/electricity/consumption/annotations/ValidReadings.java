package com.track.electricity.consumption.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.track.electricity.consumption.validator.ReadingsValidator;

/**
 * @author VIDYA ValidReadings is custom annotation for validating electricity
 *         readings
 */
@Constraint(validatedBy = { ReadingsValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface ValidReadings {

	String message() default "INVALID METER READING";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}