package se.enbohms.hhcib.entity.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Represent an NotNullOrEmpty validator used by beans validation
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullOrEmptyValidator.class)
@Documented
public @interface NotNullOrEmpty {

	String message() default "{invalid.nullorempty}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}