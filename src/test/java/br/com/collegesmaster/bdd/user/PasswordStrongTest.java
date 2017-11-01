package br.com.collegesmaster.bdd.user;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.user.steps.ValidPasswordSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

@Narrative(text={"In the user creation or update, the user password must have the rule",                      
        "That a password must contain at least 6 characters",
        "Being a letter, a number and a special character"})
@RunWith(SerenityRunner.class)
public class PasswordStrongTest {
	
	private final String PASSWORD_WITH_LESS_THAN_SIX_CHARS = "#la12";
	private final String PASSWORD_WITHOUT_NUMBERS = "#eadCd";
	private final String PASSWORD_WITHOUT_LETTERS = "#2$412";
	private final String PASSWORD_WITHOUT_SPECIAL_CHAR = "l24a12";
	private final String VALID_PASSWORD = "!d10g0";
	
	private final String AT_LEAST_1_LETTER_REGEX = "[a-zA-z]";
	private final String AT_LEAST_1_NUMBER_REGEX = "[0-9]";
	private final String AT_LEAST_1_SPECIAL_CHAR_REGEX = "[^A-Za-z0-9 ]";
	private final Integer PASSWORD_LENGHT = 6;
	
	@Steps
	private ValidPasswordSteps validPasswordSteps;
	
	@Before
	public void before() {
		validPasswordSteps.given_the_password_rule(PASSWORD_LENGHT, AT_LEAST_1_LETTER_REGEX, 
				AT_LEAST_1_NUMBER_REGEX, AT_LEAST_1_SPECIAL_CHAR_REGEX);
	}
	
	@Test
	public void passwordWithoutAtLeastSixCharactersTest() {
		validPasswordSteps.when_a_password_contains_less_than_six_characters(PASSWORD_WITH_LESS_THAN_SIX_CHARS);
		validPasswordSteps.then_is_a_valid_password(FALSE);
	}
	
	@Test
	public void passwordWithoutAtLeastOneNumberTest() {
		validPasswordSteps.when_a_password_contains_only_non_numbers(PASSWORD_WITHOUT_NUMBERS);
		validPasswordSteps.then_is_a_valid_password(FALSE);
	}
	
	@Test
	public void passwordWithoutAtLeastOneLetterTest() {
		validPasswordSteps.when_a_password_contains_only_non_letters(PASSWORD_WITHOUT_LETTERS);
		validPasswordSteps.then_is_a_valid_password(FALSE);
	}
	
	@Test
	public void passwordWithoutAtLeastOneSpecialCharacterTest() {
		validPasswordSteps.when_a_password_contains_only_non_especial_characters(PASSWORD_WITHOUT_SPECIAL_CHAR);
		validPasswordSteps.then_is_a_valid_password(FALSE);
	}
	
	@Test
	public void validPasswordTest() {
		validPasswordSteps.when_a_password_is(VALID_PASSWORD);
		validPasswordSteps.then_is_a_valid_password(TRUE);
	}
	
}
