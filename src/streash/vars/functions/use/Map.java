package streash.vars.functions.use;

import streash.vars.LambdaVar;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.MapStream;

public class Map extends AbstractFunction{
	
	public Map() {
		super(2);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof LambdaVar) {
			return MapStream.getVar((StreamVar) args[0], (LambdaVar) args[1]);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "map";
	}
}