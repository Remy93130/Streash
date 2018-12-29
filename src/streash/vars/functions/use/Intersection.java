package streash.vars.functions.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Intersection extends AbstractFunction{
	
	public Intersection() {
		super(2);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof StreamVar)
			return Stream.getVar((StreamVar) args[0], (StreamVar) args[1]);
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "inter";
	}
	
	public static class Stream extends AbstractStreamVar{
		private StreamVar s1;
		private StreamVar s2;
		private Iterator<Value> it;
		
		private Stream(StreamVar s1, StreamVar s2) {
			this.s1 = s1.duplicate();
			this.s2 = s2.duplicate();
			this.it = initIterator();
		}
		
		public static StreamVar getVar(StreamVar s1, StreamVar s2) {
			if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
				return new NumberStream(s1, s2);
			if (s1 instanceof StringStreamVar && s2 instanceof StringStreamVar)
				return new StringStream(s1, s2);
			throw new IllegalStateException("Cannot call inter on Streams of differents generics");
		}
		
		private Iterator<Value> initIterator() {
			List<Value> l1 = s1.collect();
			List<Value> l2 = s2.collect();
			List<Value> to = new ArrayList<Value>();
			for (Value v : l1) {
				if (l2.contains(v))
					to.add(v);
			}
			return to.iterator();
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(s1, s2);
		}
		
		@Override
		public String getConsoleString() {
			return "Intersection of "+s1.getConsoleString()+" and "+s2.getConsoleString();
		}
		
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		
		@Override
		public Value next() {
			return it.next();
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s1.getJSONObject());
			array.put(s2.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Intersection.getJSONName();
		}
		
		private static class NumberStream extends Stream implements NumberStreamVar{
			public NumberStream(StreamVar s1, StreamVar s2) {
				super(s1, s2);
			}
		}
		private static class StringStream extends Stream implements NumberStreamVar{
			public StringStream(StreamVar s1, StreamVar s2) {
				super(s1, s2);
			}
		}
	}
}
