package br.com.collegesmaster.model.entities.constraints.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
	
	public Integer atLeastLenght;
	public String atLeast1LetterRegex;
	public String atLeast1NumberRegex;
	public String atLeast1SpecialCharRegex;
	
	@Override
	public void initialize(Password password) {
		atLeastLenght = 6;
		atLeast1LetterRegex = "[a-zA-z]";
		atLeast1NumberRegex = "[0-9]";
		atLeast1SpecialCharRegex = "[^A-Za-z0-9 ]";
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if(password != null) {
			return isValidPasswordEntry(password);
		}
		return false;
	}
	
	public Boolean isValidPasswordEntry(final String password) {
		
		final Boolean isAtLeast6 = password.length() >= atLeastLenght;
		final Matcher containsLetter = Pattern.compile(atLeast1LetterRegex).matcher(password);
		final Matcher containsNumber = Pattern.compile(atLeast1NumberRegex).matcher(password);
		final Matcher containsSpecialCharacter =  Pattern.compile(atLeast1SpecialCharRegex).matcher(password);
		
		final Boolean hasLetter = containsLetter.find();
		final Boolean hasNumber =containsNumber.find();
		final Boolean hasEspecialCharacter = containsSpecialCharacter.find();
		
		return isAtLeast6 && hasEspecialCharacter && hasLetter && hasNumber;
	}

}
