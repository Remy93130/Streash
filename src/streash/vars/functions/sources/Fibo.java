package streash.vars.functions.sources;

import streash.vars.Number;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.FiboStream;

public class Fibo extends AbstractFunction{
	public Fibo() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number) {
			Number.requireNonFloat((Number) args[0], "Cannot use fibo() with floating Number");
			Number.requireNonFloat((Number) args[1], "Cannot use fibo() with floating Number"); 
			return new FiboStream(((Number) args[0]).getValue(), ((Number) args[1]).getValue());
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "fibo";
	}
}
