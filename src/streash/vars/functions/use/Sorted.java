package streash.vars.functions.use;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.SortedStream;

public class Sorted extends AbstractFunction{
	public Sorted() {
		super(1);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar)
			return new SortedStream((StreamVar) args[0]);
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "sorted";
	}
}
