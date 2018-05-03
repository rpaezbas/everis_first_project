package validations;

import java.sql.Timestamp;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import Logger.Log;

//This class is the implementation of the validation -ValidDate-. It must override the methods initialize and isValid. 
//The parameter of the method isValid is the value of the propertie of the validated entity. 

public class DateValidation implements ConstraintValidator<ValidDate, Timestamp> {

	@Override
	public void initialize(ValidDate constraint) {
	}

	@Override
	public boolean isValid(Timestamp date, ConstraintValidatorContext arg1) {

		Log.logger.info("Enters DateValidation.isValid");

		LocalDate convertedTimestamp = date.toLocalDateTime().toLocalDate();
		LocalDate today = LocalDate.now();
		long years = java.time.temporal.ChronoUnit.YEARS.between(convertedTimestamp, today);

		System.out.println(convertedTimestamp);
		System.out.println(today);
		System.out.println(years);

		Log.logger.info("Exits DateValidation.isValid");

		return years < 5;
	}
}
