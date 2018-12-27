package streash.vars.functions.primitive;

import streash.vars.CharChain;
import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.SubStream;

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
		if (args[0] instanceof StreamVar && args[1] instanceof StreamVar) {
			StreamVar to = SubStream.getVar((StreamVar) args[0], (StreamVar) args[1]);
			if (to == null)
				super.illegalTypesException();
			return to;
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "sub";
	}
}
