package br.com.collegesmaster.bdd.features.manage_users;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.ApplicationBehaviorStructure.ManageUsers;
import br.com.collegesmaster.bdd.features.manage_users.steps.PasswordValidationSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@Story(ManageUsers.AddNewUser.class)
public class PasswordValidationScenariosTest {
	
	private final String PASSWORD_WITH_LESS_THAN_SIX_CHARS = "#la12";
	private final String PASSWORD_WITHOUT_NUMBERS = "#eadCd";
	private final String PASSWORD_WITHOUT_LETTERS = "#2$412";
	private final String PASSWORD_WITHOUT_SPECIAL_CHAR = "l24a12";
	private final String VALID_PASSWORD = "!d10g0";
	
	@Steps
	private PasswordValidationSteps passwordValidatorScenarioSteps;

	@Title("The user signing up for the system with a password without at least 6 characters")
	@Test
	public void passwordWithoutAtLeastSixCharactersTest() {
		passwordValidatorScenarioSteps.givenPasswordRule();
		passwordValidatorScenarioSteps.whenPasswordIs(PASSWORD_WITH_LESS_THAN_SIX_CHARS);
		passwordValidatorScenarioSteps.thenThisAInvalidPassword();
	}

	@Title("The user signing up for the system with a password without at least 1 number")
	@Test
	public void passwordWithoutAtLeastOneNumberTest() {
		passwordValidatorScenarioSteps.givenPasswordRule();
		passwordValidatorScenarioSteps.whenPasswordIs(PASSWORD_WITHOUT_NUMBERS);
		passwordValidatorScenarioSteps.thenThisAInvalidPassword();
	}
	
	@Title("The user signing up for the system with a password without at least 1 letter")
	@Test
	public void passwordWithoutAtLeastOneLetterTest() {
		passwordValidatorScenarioSteps.givenPasswordRule();
		passwordValidatorScenarioSteps.whenPasswordIs(PASSWORD_WITHOUT_LETTERS);
		passwordValidatorScenarioSteps.thenThisAInvalidPassword();
	}
	
	@Title("The user signing up for the system with a password without at least 1 special character")
	@Test
	public void passwordWithoutAtLeastOneSpecialCharacterTest() {
		passwordValidatorScenarioSteps.givenPasswordRule();
		passwordValidatorScenarioSteps.whenPasswordIs(PASSWORD_WITHOUT_SPECIAL_CHAR);
		passwordValidatorScenarioSteps.thenThisAInvalidPassword();
	}
	
	@Title("The user signing up for the system with a password having at least 6 characters, "
			+ "1 letter, one number and 1 special character")
	@Test
	public void validPasswordTest() {
		passwordValidatorScenarioSteps.givenPasswordRule();
		passwordValidatorScenarioSteps.whenPasswordIs(VALID_PASSWORD);
		passwordValidatorScenarioSteps.thenThisIsAValidPassword();
	}
}
