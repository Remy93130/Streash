package streash.vars.functions.use;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.SliceStream;

public class Slice extends AbstractFunction{
	
	public Slice() {
		super(3);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number && args[2] instanceof Number) {
			Number a = Number.requireNonFloat((Number) args[1], "Cannot slice a stream with floating Number");
			Number b = Number.requireNonFloat((Number) args[2], "Cannot slice a stream with floating Number");
			StreamVar s = (StreamVar) args[0];
			
			return SliceStream.getVar(s, a.getValue(), b.getValue());
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "slice";
	}
}
