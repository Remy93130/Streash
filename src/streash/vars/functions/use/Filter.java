package streash.vars.functions.use;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.LambdaVar;
import streash.vars.Number;
import streash.vars.Primitive;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.Operator;
import streash.vars.stream.StringStreamVar;

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
			return Stream.getVar((StreamVar) args[0], (LambdaVar) args[1], (CharChain) args[2], (Primitive) args[3]);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "filter";
	}
	
	public static abstract class Stream extends AbstractStreamVar{
		private LambdaVar lambda;
		private StreamVar s;
		private Operator operator;
		private Primitive operand;
		private Value current;
		
		private Stream(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
			this.lambda = lambda;
			this.s = s.duplicate();
			this.operand = operand;
			this.operator = Operator.getInstance(operator.toString());
		}
		
		@Override
		public boolean hasNext() {
			if (current != null)
				return true;
			if (!(s.hasNext()))
				return false;
			current = s.next();
			if (operator.match(current, operand))
				return true;
			return hasNext();
		}
		
		@Override
		public Value next() {
			Value to = current;
			if (current == null)
				throw new IllegalStateException("Asking for next without asking if has next");
			current = null;
			return to;
		}
		
		@Override
		public StreamVar duplicate() {
			return getVar(s.duplicate(), lambda, new CharChain(operator.getCall()), operand);
		}
		
		public static StreamVar getVar(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
			StreamVar t = s.duplicate();
			if (t.hasNext()) {
				Value v = lambda.produce(t.next());
				if (v instanceof Number && operand instanceof Number)
					return new NumberStream(s, lambda, operator, operand);
				else if (v instanceof CharChain && operand instanceof CharChain)
					return new StringStream(s, lambda, operator, operand);
				else throw new IllegalArgumentException("Signature of lambda does not match with operand's signature or is not an allowed type");
			}
			throw new IllegalArgumentException("Trying to map an empty stream");
		}
		
		@Override
		public String getConsoleString() {
			return "Filtered stream of "+s.getConsoleString();
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			array.put(lambda.getJSONObject());
			array.put(new CharChain(operator.getCall()).getJSONObject());
			array.put(operand.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Filter.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
				super(s, lambda, operator, operand);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
				super(s, lambda, operator, operand);
			}
		}
	}
}