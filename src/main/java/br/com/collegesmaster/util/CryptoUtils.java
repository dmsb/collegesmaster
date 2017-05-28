package br.com.collegesmaster.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public abstract class CryptoUtils {

	public static String getHashedPassword(final String password, final String salt) {

		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		digest.reset();
		digest.update(password.getBytes());
		String hashedPassword = Base64.getEncoder().encodeToString(digest.digest());

		hashedPassword = hashedPassword.concat(salt);

		digest.reset();
		digest.update(hashedPassword.getBytes());

		final String hashedPasswordWithSalt = Base64.getEncoder().encodeToString(digest.digest());

		return hashedPasswordWithSalt;
	}

	public static String generateSalt() {
		
		SecureRandom secureRandom = null;
		
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		final byte[] seed = secureRandom.generateSeed(64);
		secureRandom.setSeed(seed);
		secureRandom.nextBytes(seed);

		final String generatedSalt = Base64.getEncoder().encodeToString(seed);
		return generatedSalt;
	}
}
