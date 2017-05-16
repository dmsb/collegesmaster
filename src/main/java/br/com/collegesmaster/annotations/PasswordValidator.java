package br.com.collegesmaster.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	
	
	@Override
	public void initialize(Password password) {
		
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		
		if(password != null) {			
			final Boolean hasUppercase = password.equals(password.toLowerCase()) == false; 
			final Boolean hasLowercase = password.equals(password.toUpperCase()) == false;
			final Boolean isAtLeast6 = password.length() >= 6;
			final Boolean hasEspecialCharacter = password.matches("[A-Za-z0-9 ]*") == false;		
		
			if(hasUppercase && hasLowercase && isAtLeast6 && hasEspecialCharacter) {
				return true;			
			} else {
				return false;
			}
		}
		return false;
	}

}
