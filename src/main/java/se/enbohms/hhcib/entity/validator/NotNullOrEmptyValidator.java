package se.enbohms.hhcib.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link NotNullOrEmpty} annotation.
 */
public class NotNullOrEmptyValidator implements
		ConstraintValidator<NotNullOrEmpty, String> {

	public void initialize(NotNullOrEmpty constraintAnnotation) {
	}

	/**
	 * @return {@code true} if the specified string is valid (length > 0)
	 */
	public boolean isValid(String object,
			ConstraintValidatorContext constraintContext) {
		return object != null && object.trim().length() > 0;
	}
}