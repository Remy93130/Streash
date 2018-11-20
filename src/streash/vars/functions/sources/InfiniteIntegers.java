package streash.vars.functions.sources;

import streash.vars.Number;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.InfiniteIntegersStream;

public class InfiniteIntegers extends AbstractFunction{
	private boolean reversed;
	
	public InfiniteIntegers(boolean reversed) {
		super(1);
		this.reversed = reversed;
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number) {
			Number n = (Number) args[0];
			if (!n.isInteger()) { throw new IllegalArgumentException("Cannot use integer() with a floating Number"); }
			return new InfiniteIntegersStream(n.getValue(), reversed);
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "integers";
	}
}
