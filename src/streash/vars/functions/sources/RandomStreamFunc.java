package streash.vars.functions.sources;

import streash.vars.Number;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.RandomStream;

public class RandomStreamFunc extends AbstractFunction{
	
	public RandomStreamFunc() {
		super(3);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number && args[2] instanceof Number) {
			Number a = Number.requireNonFloat((Number) args[0], "Cannot use random() with floating Number");
			Number b = Number.requireNonFloat((Number) args[1], "Cannot use random() with floating Number");
			Number seed = Number.requireNonFloat((Number) args[2], "Cannot use random() with floating Number");
			
			return new RandomStream(a.getValue(), b.getValue(), seed.getValue());
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "random";
	}
}
