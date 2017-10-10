package br.com.collegesmaster.rest.security.factory;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class KeyGeneratorFactory {

	@Produces
	public Key buildKey() throws NoSuchAlgorithmException {
		final KeyGenerator generator = KeyGenerator.getInstance("HmacSHA512");
		return generator.generateKey();
		
	}
}
