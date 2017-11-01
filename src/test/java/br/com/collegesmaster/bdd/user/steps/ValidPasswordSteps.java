package br.com.collegesmaster.bdd.user.steps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import net.thucydides.core.annotations.Step;

public class ValidPasswordSteps {

	private String password;
	private Integer lenght;
	private String atLeast1LetterRegex;
	private String atLeast1NumberRegex;
	private String atLeast1SpecialCharRegex;
	
	@Step("Given a password rule with contains at least {0} characters and "
			+ "the regexes: \"{1}\" (at least 1 letter), \"{2}\" (at least 1 number) and "
			+ "\"{3}\" (at least 1 special character).")
	public void given_the_password_rule(final Integer lenght, final String atLeast1Letter,
			final String atLeast1Number, final String atLeast1SpecialChar) {
		
		this.lenght = lenght;
		this.atLeast1LetterRegex = atLeast1Letter;
		this.atLeast1NumberRegex = atLeast1Number;
		this.atLeast1SpecialCharRegex = atLeast1SpecialChar;
	}
	
	@Step("When a password contains only non numbers: {0}")
	public void when_a_password_contains_only_non_numbers(final String nonNumericPassword) {
		password = nonNumericPassword;
	}

	@Step("When a password contains only non letters: {0}")
	public void when_a_password_contains_only_non_letters(final String nonLettersPassword) {
		password = nonLettersPassword;
	}

	@Step("When a password contains only non special characters: {0}")
	public void when_a_password_contains_only_non_especial_characters(final String nonSpecialCharactersPassword) {
		password = nonSpecialCharactersPassword;
	}

	@Step("When a password contains less than six characters: {0}")
	public void when_a_password_contains_less_than_six_characters(final String lessThanSixCharactersPassword) {
		password = lessThanSixCharactersPassword;
	}

	@Step("When a password is: {0}")
	public void when_a_password_is(final String validPassword) {
		password = validPassword;
	}

	@Step("Then the valid password status is {0}")
	public void then_is_a_valid_password(final Boolean validPassword) {
		Assert.assertTrue(isAValidPassword().equals(validPassword));
	}

	private Boolean isAValidPassword() {
		
		final Boolean isAtLeast6 = password.length() >= lenght;
		final Matcher containsLetter = Pattern.compile(atLeast1LetterRegex).matcher(password);
		final Matcher containsNumber = Pattern.compile(atLeast1NumberRegex).matcher(password);
		final Matcher containsSpecialCharacter =  Pattern.compile(atLeast1SpecialCharRegex).matcher(password);
		
		final Boolean hasLetter = containsLetter.find();
		final Boolean hasNumber =containsNumber.find();
		final Boolean hasEspecialCharacter = containsSpecialCharacter.find();
		
		return isAtLeast6 && hasEspecialCharacter && hasLetter && hasNumber;
	}

}
