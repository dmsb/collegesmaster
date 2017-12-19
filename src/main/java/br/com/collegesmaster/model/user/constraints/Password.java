package br.com.collegesmaster.model.user.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface Password {
	
	String message() default "{br.com.collegesmaster.annotations.Password.message}";
    
	Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
