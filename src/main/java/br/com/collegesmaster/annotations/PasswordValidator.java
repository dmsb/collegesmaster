package br.com.collegesmaster.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	
	
	@Override
	public void initialize(Password password) {
		
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		return true;
	}

}
