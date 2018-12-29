package streash.vars.stream;

import streash.vars.Primitive;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Single extends AbstractFunction{
	
	public Single() {
		super(1);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Primitive) {
			return SingleStream.getVar((Value) args[0]);
		}
		super.illegalTypesException();
		return null;
	}

	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "single";
	}
}
