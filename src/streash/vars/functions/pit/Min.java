package streash.vars.functions.pit;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Min extends AbstractFunction implements PitStreamFunction{
	public Min() {
		super(1);
	}

	/**
	 * Caculate the minimum of the stream
	 * 
	 * @return the minimum of the stream
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar) return ((StreamVar) args[0]).min();
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "min";
	}
}