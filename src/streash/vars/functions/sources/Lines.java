package streash.vars.functions.sources;

import streash.vars.CharChain;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.LinesStream;

public class Lines extends AbstractFunction{
	
	public Lines() {
		super(1);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof CharChain)
			return new LinesStream((CharChain) args[0]);

		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "lines";
	}
}
