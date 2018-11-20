package streash.vars.functions.use;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.ShuffleStream;

public class Shuffle extends AbstractFunction{
	public Shuffle() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number) {
			Number.requireNonFloat((Number) args[1], "Needing an integer seed");
			return new ShuffleStream((StreamVar) args[0], ((Number) args[1]).getValue());
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "shuffle";
	}
}
