package validations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

//This is the declaration of a annotation. Message, group and Payload are mandatory properties of a Bean Validation annotation 

@Documented
@Constraint(validatedBy = DateValidation.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface ValidDate {
	String message() default "The date must no be older than 5 years";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
