package streash.console;

public class StreashErrors {
	
	public class TooFewArgumentsException extends Exception {
		private static final long serialVersionUID = 1L;
		String functionName;
		public TooFewArgumentsException(String functionName) {
			super("Too few arguments for "+functionName);
		}
		public TooFewArgumentsException() {
			super("Too few arguments");
		}
	}
	
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
}
