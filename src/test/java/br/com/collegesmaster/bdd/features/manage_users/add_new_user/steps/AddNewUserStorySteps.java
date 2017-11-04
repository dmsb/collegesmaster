package br.com.collegesmaster.bdd.features.manage_users.add_new_user.steps;

import org.junit.Assert;

import br.com.collegesmaster.model.entities.constraints.password.PasswordConstraintValidator;
import net.thucydides.core.annotations.Step;

public class AddNewUserStorySteps {

	private PasswordConstraintValidator passwordValidation;
	private String password;
	
	@Step("Given The rule with the password must contains at least {0} characters "
			+ "1 letter (regex: \"{1}\"), "
			+ "1 number (regex: \"{2}\") and  "
			+ "1 special character (regex: \"{3}\")")
	public void givenPasswordRule(final Integer atLeastLenght, final String atLeast1Letter,
			final String atLeast1Number, final String atLeast1SpecialChar) {
		
		passwordValidation = new PasswordConstraintValidator();
		
		passwordValidation.atLeastLenght = atLeastLenght;
		passwordValidation.atLeast1LetterRegex = atLeast1Letter;
		passwordValidation.atLeast1NumberRegex = atLeast1Number;
		passwordValidation.atLeast1SpecialCharRegex = atLeast1SpecialChar;
	}

	@Step("When a password is: {0}")
	public void whenPasswordIs(final String validPassword) {
		password = validPassword;
	}

	@Step("Then this is a valid password")
	public void thenThisIsAValidPassword() {
		Assert.assertTrue(passwordValidation.isValidPasswordEntry(password));
	}
	
	@Step("Then this is a invalid password")
	public void thenThisAInvalidPassword() {
		Assert.assertFalse(passwordValidation.isValidPasswordEntry(password));
	}

}
