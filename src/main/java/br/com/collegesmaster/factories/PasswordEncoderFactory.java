package br.com.collegesmaster.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import br.com.collegesmaster.utils.PasswordEncoderWithSalt;

@ApplicationScoped
public class PasswordEncoderFactory {

	@Produces
	public PasswordEncoderWithSalt getPasswordEncoder() {
		return new PasswordEncoderWithSalt();
	}
}
