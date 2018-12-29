package streash.vars.functions.use;

import org.json.JSONArray;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Concat extends AbstractFunction{
	
	public Concat() {
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
		return "concat";
	}
	
	public static class Stream extends AbstractStreamVar implements StringStreamVar{
		private StreamVar[] ss;
		private int index = 0;
		
		private Stream(StreamVar s1, StreamVar s2) {
			this.ss = new StreamVar[2];
			ss[0] = s1.duplicate();
			ss[1] = s1.duplicate();
		}
		
		public static StreamVar getVar(StreamVar s1, StreamVar s2) {
			if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
				return new NumberStream(s1, s2);
			if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
				return new StringStream(s1, s2);
			return null;
		}
		@Override
		public StreamVar duplicate() {
			return new Stream(ss[0].duplicate(), ss[1].duplicate());
		}
		
		@Override
		public boolean hasNext() {
			if(ss[index].hasNext())
				return true;
			if (index == 1)
				return false;
			index++;
			return ss[index].hasNext();
		}
		
		@Override
		public Value next() {
			if (!hasNext())
				throw new IllegalStateException("No more element in the Stream");
			return ss[index].next();
		}
		
		@Override
		public String getConsoleString() {
			return ss[0].getConsoleString()+" concatened with "+ss[1].getConsoleString();
		}
		
		@Override
		public long len() {
			return ss[0].duplicate().len() + ss[1].duplicate().len();
		}
		
		@Override
		public long print() {
			return ss[0].duplicate().print() + ss[1].duplicate().print();
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(ss[0].getJSONObject());
			array.put(ss[1].getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Concat.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s1, StreamVar s2) {
				super(s1, s2);
			}

			@Override
			public String getType() {
				return NumberStreamVar.super.getType();
			}
		}
		public static class StringStream extends Stream implements NumberStreamVar {
			public StringStream(StreamVar s1, StreamVar s2) {
				super(s1, s2);
			}

			@Override
			public String getType() {
				return NumberStreamVar.super.getType();
			}
		}
	}
}
