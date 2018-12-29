package streash.vars.functions.pit;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Max extends AbstractFunction implements PitStreamFunction{
	public Max() {
		super(1);
	}

	/**
	 * Find the maximum of the stream
	 * 
	 * @return the maximum of the stream
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar) return ((StreamVar) args[0]).max();
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return "max";
	}
}
