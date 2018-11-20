package streash.vars.functions.primitive;

import streash.vars.CharChain;
import streash.vars.Number;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Mul extends AbstractFunction{

	public Mul() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number)
			return ((Number) args[0]).mul((Number) args[1]);
		if (args[0] instanceof CharChain && args[1] instanceof Number) {
			Number n = (Number) args[1];
			if (!n.isInteger()) { throw new IllegalArgumentException("Cannot time a String by a floating Number"); }
			return ((CharChain) args[0]).time(n.getValue());
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "mul";
	}
}
