package streash.vars.functions.pit;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Get extends AbstractFunction implements PitStreamFunction{
	public Get() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number) {
			Number.requireNonFloat((Number) args[1], "Unable to call get with a floating index");
			return ((StreamVar) args[0]).get(((Number) args[1]).getValue());
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "get";
	}
}
