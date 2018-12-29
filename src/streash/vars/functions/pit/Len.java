package streash.vars.functions.pit;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;

public class Len extends AbstractFunction implements PitStreamFunction{
	public Len() {
		super(1);
	}

	/**
	 * Caculate the length of the stream
	 * 
	 * @return the length of the stream
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar) return new Number(((StreamVar) args[0]).len());
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "len";
	}
}
