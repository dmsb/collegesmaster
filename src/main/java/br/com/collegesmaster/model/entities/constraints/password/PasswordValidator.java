package br.com.collegesmaster.model.entities.constraints.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	public static final Integer atLeastLenght = 6;
	public static final String atLeast1LetterRegex = "[a-zA-z]";
	public static final String atLeast1NumberRegex = "[0-9]";
	public static final String atLeast1SpecialCharRegex = "[^A-Za-z0-9 ]";
	
	public Boolean isValidPasswordEntry(final String password) {
		
		final Boolean isAtLeast6 = haveAtLeast6Characters(password);
		final Boolean hasLetter = hasAtLeast1Letter(password);
		final Boolean hasNumber = hasAtLeast1Number(password);
		final Boolean hasEspecialCharacter = hasAtLeast1SpecialCharacter(password);
		
		return isAtLeast6 && hasEspecialCharacter && hasLetter && hasNumber;
	}

	private Boolean hasAtLeast1SpecialCharacter(final String password) {
		final Matcher containsSpecialCharacter =  Pattern.compile(atLeast1SpecialCharRegex).matcher(password);
		final Boolean hasEspecialCharacter = containsSpecialCharacter.find();
		return hasEspecialCharacter;
	}

	private Boolean hasAtLeast1Number(final String password) {
		final Matcher containsNumber = Pattern.compile(atLeast1NumberRegex).matcher(password);
		final Boolean hasNumber =containsNumber.find();
		return hasNumber;
	}

	private Boolean hasAtLeast1Letter(final String password) {
		final Matcher containsLetter = Pattern.compile(atLeast1LetterRegex).matcher(password);
		final Boolean hasLetter = containsLetter.find();
		return hasLetter;
	}

	private Boolean haveAtLeast6Characters(final String password) {
		return password.length() >= atLeastLenght;
	}
}
