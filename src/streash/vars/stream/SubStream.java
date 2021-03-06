package streash.vars.stream;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.CharChain;
import streash.vars.Number;

public abstract class SubStream implements StreamVar{
	private StreamVar s1;
	private StreamVar s2;
	
	private SubStream(StreamVar s1, StreamVar s2) {
		this.s1 = s1.duplicate();
		this.s2 = s2.duplicate();
	}
	
	public static StreamVar getVar(StreamVar s1, StreamVar s2) {
		if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
			return new SubNumberStream(s1, s2);
		if (s1 instanceof StringStreamVar && s2 instanceof StringStreamVar)
			return new SubStringStream(s1, s2);
		return null;
	}
	
	@Override
	public StreamVar duplicate() {
		return getVar(s1, s2);
	}
	
	@Override
	public String getConsoleString() {
		return s1.getConsoleString()+" and "+s2.getConsoleString();
	}
	
	@Override
	public boolean hasNext() {
		return (s1.hasNext() || s2.hasNext());
	}
	
	@Override
	public Value next() {
		if (s1.hasNext() && s2.hasNext())
			return operate(s1.next(), s2.next());
		if (s1.hasNext())
			return s1.next();
		if (s2.hasNext())
			return operate(null, s2.next());
		throw new IllegalStateException("No such element");
	}
	
	public abstract Value operate(Value v1, Value v2);
	
	@Override
	public long len() {
		long l1, l2;
		l1 = s1.len();
		l2 = s2.len();
		if (l1 > l2)
			return l1;
		return l2;
	}
	
	public static class SubNumberStream extends SubStream implements NumberStreamVar {
		public SubNumberStream(StreamVar s1, StreamVar s2) {
			super(s1, s2);
		}
		@Override
		public String getConsoleString() {
			return "Substraction of "+super.toString();
		}
		
		@Override
		public Value operate(Value v1, Value v2) {
			if (v1 == null)
				v1 = new Number(0);
			return ((Number)v1).sub((Number) v2);
		}
	}
	public static class SubStringStream extends SubStream implements StringStreamVar {
		public SubStringStream(StreamVar s1, StreamVar s2) {
			super(s1, s2);
		}
		@Override
		public String getConsoleString() {
			return "Substraction by suffix of "+super.toString();
		}
		
		@Override
		public Value operate(Value v1, Value v2) {
			if (v1 == null)
				v1 = new CharChain("");
			return ((CharChain)v1).subSuffix((CharChain) v2);
		}
	}
}
