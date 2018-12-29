package streash.errors;

public class UnknownVariableNameExcpetion extends Exception{
	private static final long serialVersionUID = 1L;

	public UnknownVariableNameExcpetion(String varName) {
		super("Unknown variable name "+varName);
	}
}
