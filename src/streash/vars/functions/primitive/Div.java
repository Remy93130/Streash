package streash.vars.functions.primitive;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.DivStream;
import streash.vars.stream.NumberStreamVar;

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
		if (args[0] instanceof NumberStreamVar && args[1] instanceof NumberStreamVar)
			return new DivStream((StreamVar) args[0], (StreamVar) args[1]);
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "div";
	}
}
