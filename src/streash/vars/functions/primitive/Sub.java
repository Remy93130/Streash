package streash.vars.functions.primitive;

import streash.vars.CharChain;
import streash.vars.Number;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Sub extends AbstractFunction{

	public Sub() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number)
			return ((Number) args[0]).sub((Number) args[1]);
		if (args[0] instanceof CharChain && args[1] instanceof CharChain)
			return ((CharChain) args[0]).subSuffix((CharChain) args[1]);
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "sub";
	}
}
