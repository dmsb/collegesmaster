package br.com.collegesmaster.bdd.user;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.user.steps.ValidPasswordSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

@RunWith(SerenityRunner.class)
public class PasswordStrongTest {
	
	private String password;
	
	@Steps
	private ValidPasswordSteps validPasswordSteps;
	
	@Test
	public void passwordWithoutAtLeastSixCharactersTest() {
		password = "#la12";
		validPasswordSteps.when_a_password_contains_less_than_six_characters(password);
		validPasswordSteps.then_is_a_invalid_password(password);
	}
	
	@Test
	public void passwordWithoutAtLeastOneNumberTest() {
		password = "#eadCd";
		validPasswordSteps.when_a_password_contains_only_non_numbers(password);
		validPasswordSteps.then_is_a_invalid_password(password);
	}
	
	@Test
	public void passwordWithoutAtLeastOneLetterTest() {
		password = "#2$412";
		validPasswordSteps.when_a_password_contains_only_non_letters(password);
		validPasswordSteps.then_is_a_invalid_password(password);
	}
	
	@Test
	public void passwordWithoutAtLeastOneSpecialCharacterTest() {
		password = "l24a12";
		validPasswordSteps.when_a_password_contains_only_non_especial_characters(password);
		validPasswordSteps.then_is_a_invalid_password(password);
	}
	
	@Test
	public void validPasswordTest() {
		password = "!d10g0";
		validPasswordSteps.when_a_password_is(password);
		validPasswordSteps.then_is_a_valid_password(password);
	}
	
}
