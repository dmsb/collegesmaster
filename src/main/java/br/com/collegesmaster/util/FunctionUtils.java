package br.com.collegesmaster.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
}
