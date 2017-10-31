package br.com.collegesmaster.integration;

import static java.util.Base64.getEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.jboss.logging.Logger;

public class CryptoTest {
	
	private static final Logger LOGGER = Logger.getLogger(CryptoTest.class);
	
	public static void main(String[] args) {
		while(true) {			
			final String salt = saltAlgorithm();
			getHashedPassword("D10g0!", salt);
		}
	}

	private static String saltAlgorithm() {
		SecureRandom secureRandom = null;

		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			final byte[] seed = secureRandom.generateSeed(32);
			final String generatedSalt = getEncoder().encodeToString(seed);
			LOGGER.info("Generated Salt: " + generatedSalt);
			
			return generatedSalt;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void getHashedPassword(final String password, final String salt) {

		MessageDigest digest = null;

		try {
			
			digest = MessageDigest.getInstance("SHA-512");

			String hashedPassword = getEncoder().encodeToString(digest.digest(password.getBytes()));
			digest.reset();
			
			hashedPassword = hashedPassword.concat(salt);
			
			final String hashedPasswordWithSalt = getEncoder()
					.encodeToString(digest.digest(hashedPassword.getBytes()));
						
			LOGGER.info("Generated hash of password: " + hashedPasswordWithSalt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}				
	}
}
