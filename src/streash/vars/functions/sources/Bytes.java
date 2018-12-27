package streash.vars.functions.sources;

import streash.vars.CharChain;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.BytesStream;

public class Bytes extends AbstractFunction{
	
	public Bytes() {
		super(1);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof CharChain)
			return new BytesStream((CharChain) args[0]);

		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "bytes";
	}
}
