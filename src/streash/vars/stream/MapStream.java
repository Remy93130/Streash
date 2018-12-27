package streash.vars.stream;

import streash.vars.CharChain;
import streash.vars.LambdaVar;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.Number;

public abstract class MapStream implements StreamVar{
	private LambdaVar lambda;
	private StreamVar s;
	
	private MapStream(StreamVar s, LambdaVar lambda) {
		this.lambda = lambda;
		this.s = s.duplicate();
	}
	
	@Override
	public boolean hasNext() {
		return s.hasNext();
	}
	
	@Override
	public Value next() {
		return lambda.produce(s.next());
	}
	
	@Override
	public StreamVar duplicate() {
		return getVar(s.duplicate(), lambda);
	}
	
	public static StreamVar getVar(StreamVar s, LambdaVar lambda) {
		StreamVar t = s.duplicate();
		if (t.hasNext()) {
			Value v = lambda.produce(t.next());
			if (v instanceof Number)
				return new MapNumberStream(s, lambda);
			else if (v instanceof CharChain)
				return new MapStringStream(s, lambda);
			else throw new IllegalArgumentException("Return type of mapping function is not a primitive");
		}
		throw new IllegalArgumentException("Trying to map an empty stream");
	}
	
	@Override
	public String getConsoleString() {
		return "Mapped stream of "+s.getConsoleString();
	}
	
	@Override
	public long len() {
		return s.len();
	}
	
	public static class MapNumberStream extends MapStream implements NumberStreamVar {
		public MapNumberStream(StreamVar s, LambdaVar lambda) {
			super(s, lambda);
		}
	}
	public static class MapStringStream extends MapStream implements StringStreamVar {
		public MapStringStream(StreamVar s, LambdaVar lambda) {
			super(s, lambda);
		}
	}
	
}
