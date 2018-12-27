package streash.vars.stream;

import java.util.function.BiFunction;

import streash.vars.CharChain;
import streash.vars.LambdaVar;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.Number;
import streash.vars.Primitive;

public abstract class FilterStream implements StreamVar{
	private LambdaVar lambda;
	private StreamVar s;
	private Operator operator;
	private Primitive operand;
	private Value current;
	
	private FilterStream(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
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
				return new FilterNumberStream(s, lambda, operator, operand);
			else if (v instanceof CharChain && operand instanceof CharChain)
				return new FilterStringStream(s, lambda, operator, operand);
			else throw new IllegalArgumentException("Signature of lambda does not match with operand's signature or is not an allowed type");
		}
		throw new IllegalArgumentException("Trying to map an empty stream");
	}
	
	@Override
	public String getConsoleString() {
		return "Filtered stream of "+s.getConsoleString();
	}
	
	public static class FilterNumberStream extends FilterStream implements NumberStreamVar {
		public FilterNumberStream(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
			super(s, lambda, operator, operand);
		}
	}
	public static class FilterStringStream extends FilterStream implements StringStreamVar {
		public FilterStringStream(StreamVar s, LambdaVar lambda, CharChain operator, Primitive operand) {
			super(s, lambda, operator, operand);
		}
	}
	
	private static enum Operator {
		SUP(">", (x, y) -> { return (x.compareTo(y) > 0); } ),
		DIF("<>", (x, y) -> { return (x.compareTo(y) != 0); }),
		INF("<", (x, y) -> { return (x.compareTo(y) < 0); }),
		EQU("=", (x, y) -> { return (x.compareTo(y) == 0); }),
		SUPE(">=", (x, y) -> { return (x.compareTo(y) >= 0); }),
		INFE("<=", (x, y) -> { return (x.compareTo(y) <= 0); });
		
		private BiFunction<Value, Value, Boolean> comparator;
		private String call;
		
		private Operator(String call, BiFunction<Value, Value, Boolean> comparator) {
			this.call = call;
			this.comparator = comparator;
		}
		public static Operator getInstance(String call) {
			for (Operator o : Operator.values())
				if (o.call.equals((call)))
					return o;
			throw new IllegalArgumentException("Unknown operator");
		}
		
		public boolean match(Value v1, Value v2) {
			return comparator.apply(v1,  v2);
		}
		
		public String getCall() {
			return call;
		}
	}
	
}
