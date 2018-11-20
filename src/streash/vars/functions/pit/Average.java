package streash.vars.functions.pit;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Average extends AbstractFunction{
	public Average() {
		super(1);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar) return ((StreamVar) args[0]).average();
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "average";
	}
}
