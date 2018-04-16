package resources;

public class Error {

	String message;
	public final static String nullResource = "The requested resource doesn´t exist";
	public final static String invalidInput = "The resource couldn´t be created. Please make sure every field has a correct name and value";
	
	public Error(String message) {
		this.message = message;
	}

	/**
	 * @return the mesage
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @param mesage
	 * the mesage to set
	 */
	public final void setMessage(String mesage) {
		this.message = message;
	}

}
