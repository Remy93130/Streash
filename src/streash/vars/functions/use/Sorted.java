package streash.vars.functions.use;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Sorted extends AbstractFunction{
	
	public Sorted() {
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
		return "sorted";
	}
	
	public static class Stream extends AbstractStreamVar{
		private StreamVar s;
		private Iterator<Value> it;
		
		private Stream(StreamVar s) {
			this.s = s.duplicate();
			ArrayList<Value> list = new ArrayList<Value>();
			while(this.s.hasNext())
				list.add(this.s.next());
			list.sort(Value::compareTo);
			it = list.iterator();
		}
		
		public static StreamVar getVar(StreamVar s) {
			if (s instanceof NumberStreamVar)
				return new NumberStream(s);
			if (s instanceof StringStreamVar)
				return new StringStream(s);
			return null;
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(s.duplicate());
		}
		
		@Override
		public String getConsoleString() {
			return "Sorted stream of "+s.getConsoleString();
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
		public long len() {
			return s.len();
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Sorted.getJSONName();
		}
		
		static class NumberStream extends Stream implements NumberStreamVar{
			public NumberStream(StreamVar s) {
				super(s);
			}
		}
		static class StringStream extends Stream implements NumberStreamVar{
			public StringStream(StreamVar s) {
				super(s);
			}
		}
	}
}
