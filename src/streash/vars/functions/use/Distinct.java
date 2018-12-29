package streash.vars.functions.use;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Distinct extends AbstractFunction{
	
	public Distinct() {
		super(1);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar)
			return Stream.getVar((StreamVar) args[0]);
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "distinct";
	}
	
	public static class Stream extends AbstractStreamVar{
		private StreamVar s;
		private Set<Value> set;
		private Value current;
		
		private Stream(StreamVar s) {
			this.s = s.duplicate();
			set = new HashSet<Value>();
			current = null;
		}
		
		@Override
		public boolean hasNext() {
			if (current != null)
				return true;
			if (s.hasNext()) {
				Value test = s.next();
				if (set.contains(test))
					return hasNext();
				else {
					set.add(test);
					current = test;
					return true;
				}
			}
			return false;
		}
		
		@Override
		public Value next() {
			if (current == null)
				throw new IllegalStateException("Calling for next but didnt call for hasNext or has not next");
			Value to = current;
			current = null;
			return to;
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(s);
		}
		
		public static StreamVar getVar(StreamVar s) {
			if (s instanceof NumberStreamVar)
				return new NumberStream(s);
			if (s instanceof StringStreamVar)
				return new StringStream(s);
			return null;
		}
		
		@Override
		public String getConsoleString() {
			return "Distinct Stream of "+s.getConsoleString();
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Distinct.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s) {
				super(s);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s) {
				super(s);
			}
		}
	}
}
