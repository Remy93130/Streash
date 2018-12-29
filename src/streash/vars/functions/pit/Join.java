package streash.vars.functions.pit;

import streash.vars.CharChain;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.StringStreamVar;

public class Join extends AbstractFunction implements PitStreamFunction{
	public Join() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StringStreamVar && args[1] instanceof CharChain) {
			return ((StreamVar) args[0]).join(((CharChain) args[1]).toString());
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "join";
	}
}
