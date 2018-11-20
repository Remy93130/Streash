package streash.vars.functions.use;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.ConcatStream;

public class Concat extends AbstractFunction{
	public Concat() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof StreamVar)
			return new ConcatStream((StreamVar) args[0], (StreamVar) args[1]);
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "concat";
	}
}
