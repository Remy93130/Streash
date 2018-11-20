package streash.vars.functions.use;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.RepeatStream;

public class Repeat extends AbstractFunction{
	public Repeat() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number) {
			Number a = ((Number) args[1]);
			StreamVar s = (StreamVar) args[0];
			
			return new RepeatStream(s, a.getFloatingValue());
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "repeat";
	}
}
