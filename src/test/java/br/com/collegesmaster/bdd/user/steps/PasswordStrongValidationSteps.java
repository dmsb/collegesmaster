package br.com.collegesmaster.bdd.user.steps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import net.thucydides.core.annotations.Step;

public class PasswordStrongValidationSteps {

	private String password;
	private Integer atLeastLenght;
	private String atLeast1LetterRegex;
	private String atLeast1NumberRegex;
	private String atLeast1SpecialCharRegex;
	
	@Step("Given the rule with the password must contains at least {0} characters "
			+ "1 letter (regex: \"{1}\"), "
			+ "1 number (regex: \"{2}\") and  "
			+ "1 special character (regex: \"{3}\")")
	public void given_the_password_rule(final Integer atLeastLenght, final String atLeast1Letter,
			final String atLeast1Number, final String atLeast1SpecialChar) {
		
		this.atLeastLenght = atLeastLenght;
		this.atLeast1LetterRegex = atLeast1Letter;
		this.atLeast1NumberRegex = atLeast1Number;
		this.atLeast1SpecialCharRegex = atLeast1SpecialChar;
	}

	@Step("When a password is: {0}")
	public void when_a_password_is(final String validPassword) {
		password = validPassword;
	}

	@Step("Then this is a valid password")
	public void then_this_is_a_valid_password() {
		Assert.assertTrue(isAValidPassword());
	}
	
	@Step("Then this is a invalid password")
	public void then_this_is_a_invalid_password() {
		Assert.assertFalse(isAValidPassword());
	}

	private Boolean isAValidPassword() {
		
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
