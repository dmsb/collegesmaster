package br.com.collegesmaster.bdd.features.manage_users.steps;

import org.junit.Assert;

import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.model.security.business.impl.PasswordValidator;
import br.com.collegesmaster.model.security.impl.UserImpl;
import net.thucydides.core.annotations.Step;

public class PasswordValidationSteps {

	private PasswordValidator passwordValidation;
	private User user;
	
	@Step("Given user is signing up for College's Master\n"
			+ "And at password field, the password must contains at least 6 characters "
			+ "1 letter, 1 number and 1 special character")
	public void givenPasswordRule() {
		user = new UserImpl();
		passwordValidation = new PasswordValidator();
	}

	@Step("When a user provide the password {0}")
	public void whenPasswordIs(final String validPassword) {
		user.setPassword(validPassword);
	}

	@Step("Then this is a valid password")
	public void thenThisIsAValidPassword() {
		Assert.assertTrue(passwordValidation.isValidPasswordEntry(user.getPassword()));
	}
	
	@Step("Then a College's Master account is not created\n"
			+ "And the 'The password must be at least 6 characters, being 1 special, 1 letter and 1 number.' "
			+ "message is displayed")
	public void thenThisAInvalidPassword() {
		Assert.assertFalse(passwordValidation.isValidPasswordEntry(user.getPassword()));
	}
}
