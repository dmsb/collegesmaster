package br.com.collegesmaster.util;

import static java.util.Base64.getEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.jboss.logging.Logger;

public abstract class CryptoUtils {
	
	private static final Logger LOGGER = Logger.getLogger(CryptoUtils.class);
	
	public static String generateHashedPassword(final String password, final String salt) {

		MessageDigest digest = null;

		try {
			
			digest = MessageDigest.getInstance("SHA-512");
			String hashedPassword = getEncoder().encodeToString(digest.digest(password.getBytes()));
			digest.reset();
			
			hashedPassword = hashedPassword.concat(salt);
			
			final String hashedPasswordWithSalt = getEncoder()
					.encodeToString(digest.digest(hashedPassword.getBytes()));
			
			return hashedPasswordWithSalt;
			
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SHA-512 algorithm not founded.", e);
		}
		
		return null;
	}

	public static String generateSalt() {
		
		SecureRandom secureRandom = null;
		
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");			
			final byte[] seed = secureRandom.generateSeed(32); 						

			final String generatedSalt = getEncoder().encodeToString(seed);
			return generatedSalt;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SHA1PRNG algorithm not founded.", e);
		}
		
		return null;
	}
}
