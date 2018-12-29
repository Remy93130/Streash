package streash.vars.functions.pit;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.NumberStreamVar;

public class Sum extends AbstractFunction implements PitStreamFunction{
	public Sum() {
		super(1);
	}

	/**
	 * Caculate the sum of the values of the stream if the values are Number
	 * 
	 * @return the sum of the values of the stream
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar) {
			StreamVar s = (StreamVar) args[0];
			if (!(s instanceof NumberStreamVar))
				throw new IllegalArgumentException("Cannot compute sum on an other generical Stream than Number");
			return s.sum();
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "sum";
	}
}
