package streash.vars.functions.pit;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.CharChain;
import streash.vars.Number;

public class Print extends AbstractFunction{
	public Print() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof CharChain) {
			StreamVar s = (StreamVar) args[0];
			CharChain source = (CharChain) args[1];
			StreamVar backup = s.duplicate();
			long size = s.print(source.toString());
			Number to = new Number(size);
			s = backup;
			return to;
		}
		super.illegalTypesException();
		return null;
	}
	@Override
	public String getName() {
		return "print";
	}
}
