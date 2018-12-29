package streash.errors;

public class TooManyArgumentsException extends Exception {
	private static final long serialVersionUID = 1L;
	String functionName;
	public TooManyArgumentsException(String functionName) {
		super("Too many arguments for "+functionName);
	}
	public TooManyArgumentsException() {
		super("Too many arguments");
	}
}