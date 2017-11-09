package br.com.collegesmaster.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.jboss.logging.Logger;

public class PasswordEncoderWithSalt {

	private static final Logger LOGGER = Logger.getLogger(PasswordEncoderWithSalt.class);
	private MessageDigest digester;

	public String generateSalt() {
		final SecureRandom secureRandom = getSecureRandomInstanceSHA1PRNG();
		final byte[] seed = secureRandom.generateSeed(32);
		final String generatedSalt = Base64.getEncoder().encodeToString(seed);
		return generatedSalt;
	}

	public String generateHashedPassword(final String password, final String salt) {
		digester = getDigestInstanceSHA512();
		final String hashedPassword = buildHashedPassword(password, salt);
		final byte[] hashedPasswordBytes = digester.digest(hashedPassword.getBytes());
		final String hashedPasswordWithSalt = convertToBase64(hashedPasswordBytes);
		return hashedPasswordWithSalt;
	}

	private String buildHashedPassword(final String password, final String salt) {
		String hashedPassword = convertToBase64(digester.digest(password.getBytes()));
		digester.reset();
		hashedPassword = hashedPassword.concat(salt);
		return hashedPassword;
	}

	private String convertToBase64(final byte[] passwordBytes) {
		return Base64.getEncoder().encodeToString(passwordBytes);
	}

	public SecureRandom getSecureRandomInstanceSHA1PRNG() {
		try {
			return SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SHA1PRNG algorithm not founded.", e);
			return null;
		}
	}

	public MessageDigest getDigestInstanceSHA512() {
		try {
			return MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SHA-512 algorithm not founded.", e);
			return null;
		}
	}
}
