package se.enbohms.hhcib.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link Email} annotation.
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

	public void initialize(Email constraintAnnotation) {
	}

	/**
	 * @return {@code true} if the specified email is valid (contains an
	 *         '@'character),{@code false} otherwise
	 */
	public boolean isValid(String object,
			ConstraintValidatorContext constraintContext) {
		return object != null && object.contains("@");
	}
}