package streash.errors;

public class SyntaxErrorException extends Exception{
	private static final long serialVersionUID = 1L;

	public SyntaxErrorException() {
		super("Syntax error");
	}
}
