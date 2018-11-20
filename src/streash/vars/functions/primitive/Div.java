package streash.vars.functions.primitive;

import streash.vars.Number;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Div extends AbstractFunction{

	public Div() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number)
			return ((Number) args[0]).div((Number) args[1]);
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "div";
	}
}
