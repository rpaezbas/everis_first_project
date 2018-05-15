package utils;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import car.entity.Car;
import logger.Log;

public class ValidatorUtil {

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();

	public static ArrayList<String> validate(Car car) {
		
		Log.logger.info("Enters ValidatorUtil.validate");
		
		Set<ConstraintViolation<Car>> violations = validator.validate(car);
		ArrayList<String> violantionMessages = new ArrayList<String>();
		for (ConstraintViolation<Car> violation : violations) {
			violantionMessages.add(violation.getMessage());
		}
		
		Log.logger.info("Exits ValidatorUtil.validate");
		
		return violantionMessages;
	}

}
