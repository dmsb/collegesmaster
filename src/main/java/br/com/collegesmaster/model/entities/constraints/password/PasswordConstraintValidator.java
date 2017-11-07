package br.com.collegesmaster.model.entities.constraints.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
	
	public PasswordValidator passwordValidator;
	
	@Override
	public void initialize(Password password) {
		passwordValidator = new PasswordValidator();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if(password != null) {
			return passwordValidator.isValidPasswordEntry(password);
		}
		return false;
	}
}
