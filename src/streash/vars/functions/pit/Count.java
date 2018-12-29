package streash.vars.functions.pit;

import streash.vars.Primitive;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Count extends AbstractFunction implements PitStreamFunction{
	public Count() {
		super(2);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Primitive) {
			return ((StreamVar) args[0]).count(((Value) args[1]));
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "count";
	}
}
