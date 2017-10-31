package br.com.collegesmaster.bdd.user.steps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import net.thucydides.core.annotations.Step;

public class ValidPasswordSteps {

	private String password;

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

	@Step("Then {0} is a valid password")
	public void then_is_a_valid_password(final String validPassword) {
		if (validPassword.equals(password)) {
			Assert.assertTrue(isAValidPassword());
		} else {
			Assert.fail("The validPassword is not equals to given password");
		}
	}

	@Step("Then {0} is a invalid password")
	public void then_is_a_invalid_password(final String invalidPassword) {
		if (invalidPassword.equals(password)) {
			Assert.assertFalse(isAValidPassword());
		} else {
			Assert.fail("The invalidPassword is not equals to given password");
		}
	}

	private Boolean isAValidPassword() {
		final Boolean isAtLeast6 = password.length() >= 6;
		
		final Matcher containsLetter = Pattern.compile("([a-zA-Z])").matcher(password);
		final Matcher containsNumber = Pattern.compile("([0-9])").matcher(password);
		final Matcher containsSpecialCharacter =  Pattern.compile("[^A-Za-z0-9 ]").matcher(password);
		
		final Boolean hasLetter = containsLetter.find();
		final Boolean hasNumber =containsNumber.find();
		final Boolean hasEspecialCharacter = containsSpecialCharacter.find();
		
		return isAtLeast6 && hasEspecialCharacter && hasLetter && hasNumber;
	}

}
