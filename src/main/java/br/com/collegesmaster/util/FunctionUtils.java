package br.com.collegesmaster.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.dbunit.util.Base64;

public abstract class FunctionUtils {
	
	public static <T> void showInvalidColumnsValues(final T object) {
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    	final Validator validator = factory.getValidator();

    	final Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

    	if (constraintViolations.size() > 0 ) {
    	System.out.println("Constraint Violations occurred..");
    	for (final ConstraintViolation<T> contraints : constraintViolations) {
    	System.out.println(contraints.getRootBeanClass().getSimpleName()+
    	"." + contraints.getPropertyPath() + " " + contraints.getMessage());
    	  }
    	}
	}
	
	public static String getHashedPassword(final String password, final String salt) {
        
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
        digest.reset();
        digest.update(salt.getBytes());        
        final String hashedPasswordWithSalt = Base64.encodeBytes(digest.digest()); 
        
        return hashedPasswordWithSalt;        
    }
	
	public static String generateSalt() {        
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
		final byte[] salt = new byte[128];
        sr.nextBytes(salt);        
        
        final String saltGenerated = Base64.encodeBytes(salt); 
        return saltGenerated;
    }
}
