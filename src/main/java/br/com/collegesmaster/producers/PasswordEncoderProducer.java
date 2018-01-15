package br.com.collegesmaster.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import br.com.collegesmaster.model.security.business.impl.PasswordEncoderWithSalt;

@ApplicationScoped
public class PasswordEncoderProducer {

	@Produces
	public PasswordEncoderWithSalt getPasswordEncoder() {
		return new PasswordEncoderWithSalt();
	}
}
