package br.com.collegesmaster.bdd.features.manage_users.add_new_user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.ApplicationBehaviorStructure.ManageUsers;
import br.com.collegesmaster.bdd.features.manage_users.add_new_user.steps.AddNewUserStorySteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@Story(ManageUsers.AddNewUser.class)
public class AddNewUserTest {
	
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
	private AddNewUserStorySteps validPasswordSteps;
	
	@Before
	public void before() {
		validPasswordSteps.givenPasswordRule(AT_LEAST_LENGHT, AT_LEAST_1_LETTER_REGEX, 
				AT_LEAST_1_NUMBER_REGEX, AT_LEAST_1_SPECIAL_CHAR_REGEX);
	}

	@Title("The user signing up for the system with a password without at least six characters")
	@Test
	public void passwordWithoutAtLeastSixCharactersTest() {
		validPasswordSteps.whenPasswordIs(PASSWORD_WITH_LESS_THAN_SIX_CHARS);
		validPasswordSteps.thenThisAInvalidPassword();
	}

	@Title("The user signing up for the system with a password without at least one number")
	@Test
	public void passwordWithoutAtLeastOneNumberTest() {
		validPasswordSteps.whenPasswordIs(PASSWORD_WITHOUT_NUMBERS);
		validPasswordSteps.thenThisAInvalidPassword();
	}
	
	@Title("The user signing up for the system with a password without at least one letter")
	@Test
	public void passwordWithoutAtLeastOneLetterTest() {
		validPasswordSteps.whenPasswordIs(PASSWORD_WITHOUT_LETTERS);
		validPasswordSteps.thenThisAInvalidPassword();
	}
	
	@Title("The user signing up for the system with a password without at least one special character")
	@Test
	public void passwordWithoutAtLeastOneSpecialCharacterTest() {
		validPasswordSteps.whenPasswordIs(PASSWORD_WITHOUT_SPECIAL_CHAR);
		validPasswordSteps.thenThisAInvalidPassword();
	}
	
	@Title("The user signing up for the system with a password with at least six characters, "
			+ "one letter, one number and one special character")
	@Test
	public void validPasswordTest() {
		validPasswordSteps.whenPasswordIs(VALID_PASSWORD);
		validPasswordSteps.thenThisIsAValidPassword();
	}
}
