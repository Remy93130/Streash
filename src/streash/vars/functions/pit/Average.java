package streash.vars.functions.pit;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.NumberStreamVar;

public class Average extends AbstractFunction{
	public Average() {
		super(1);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar) {
			StreamVar s = (StreamVar) args[0];
			if (!(s instanceof NumberStreamVar))
				throw new IllegalArgumentException("Cannot compute average on an other generical Stream than Number");
			return ((StreamVar) args[0]).average();
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "average";
	}
}
