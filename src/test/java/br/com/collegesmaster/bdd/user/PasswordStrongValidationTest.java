package br.com.collegesmaster.bdd.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.user.steps.PasswordStrongValidationSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

@Narrative(text={"In the user creation or update, the user password must have the rule",                      
        "That a password must contain at least 6 characters",
        "Being a letter, a number and a special character"})
@RunWith(SerenityRunner.class)
public class PasswordStrongValidationTest {
	
	private final String PASSWORD_WITH_LESS_THAN_SIX_CHARS = "#la12";
	private final String PASSWORD_WITHOUT_NUMBERS = "#eadCd";
	private final String PASSWORD_WITHOUT_LETTERS = "#2$412";
	private final String PASSWORD_WITHOUT_SPECIAL_CHAR = "l24a12";
	private final String VALID_PASSWORD = "!d10g0";
	
	private final String AT_LEAST_1_LETTER_REGEX = "[a-zA-z]";
	private final String AT_LEAST_1_NUMBER_REGEX = "[0-9]";
	private final String AT_LEAST_1_SPECIAL_CHAR_REGEX = "[^A-Za-z0-9 ]";
	private final Integer AT_LEAST_LENGHT = 6;
	
	@Steps
	private PasswordStrongValidationSteps validPasswordSteps;
	
	@Before
	public void before() {
		validPasswordSteps.given_the_password_rule(AT_LEAST_LENGHT, AT_LEAST_1_LETTER_REGEX, 
				AT_LEAST_1_NUMBER_REGEX, AT_LEAST_1_SPECIAL_CHAR_REGEX);
	}

	@Test
	public void passwordWithoutAtLeastSixCharactersTest() {
		validPasswordSteps.when_a_password_is(PASSWORD_WITH_LESS_THAN_SIX_CHARS);
		validPasswordSteps.then_this_is_a_invalid_password();
	}
	
	@Test
	public void passwordWithoutAtLeastOneNumberTest() {
		validPasswordSteps.when_a_password_is(PASSWORD_WITHOUT_NUMBERS);
		validPasswordSteps.then_this_is_a_invalid_password();
	}
	
	@Test
	public void passwordWithoutAtLeastOneLetterTest() {
		validPasswordSteps.when_a_password_is(PASSWORD_WITHOUT_LETTERS);
		validPasswordSteps.then_this_is_a_invalid_password();
	}
	
	@Test
	public void passwordWithoutAtLeastOneSpecialCharacterTest() {
		validPasswordSteps.when_a_password_is(PASSWORD_WITHOUT_SPECIAL_CHAR);
		validPasswordSteps.then_this_is_a_invalid_password();
	}
	
	@Test
	public void validPasswordTest() {
		validPasswordSteps.when_a_password_is(VALID_PASSWORD);
		validPasswordSteps.then_this_is_a_valid_password();
	}
}
