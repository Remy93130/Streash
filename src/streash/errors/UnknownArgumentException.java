package streash.errors;

public class UnknownArgumentException extends Exception{
	private static final long serialVersionUID = 1L;

	public UnknownArgumentException() {
		super("Unknown argument");
	}
}
