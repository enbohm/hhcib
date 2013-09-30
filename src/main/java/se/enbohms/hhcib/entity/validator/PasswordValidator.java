package se.enbohms.hhcib.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link NotNullOrEmpty} annotation.
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

	public void initialize(Password constraintAnnotation) {
	}

	/**
	 * @return {@code true} if the specified password string is valid (4 <
	 *         length < 50) and not null
	 */
	public boolean isValid(String object,
			ConstraintValidatorContext constraintContext) {
		return object != null && object.trim().length() > 3
				&& object.trim().length() < 50;
	}
}