package streash.vars.functions.use;

import streash.vars.CharChain;
import streash.vars.LambdaVar;
import streash.vars.Primitive;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.FilterStream;

public class Filter extends AbstractFunction{
	
	public Filter() {
		super(4);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && 
			args[1] instanceof LambdaVar &&
			args[2] instanceof CharChain &&
			args[3] instanceof Primitive) {
			return FilterStream.getVar((StreamVar) args[0], (LambdaVar) args[1], (CharChain) args[2], (Primitive) args[3]);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "filter";
	}
}