package br.com.collegesmaster.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
	
	public enum SecurityLevel {
		NORMAL, ADVANCED;
	}
	SecurityLevel securityLevel();
	
}
