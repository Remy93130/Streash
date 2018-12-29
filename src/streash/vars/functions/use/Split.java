package streash.vars.functions.use;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.SingleStream;
import streash.vars.stream.StringStreamVar;

public class Split extends AbstractFunction{
	
	public Split() {
		super(2);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StringStreamVar && args[1] instanceof CharChain) {
			return new Stream((StreamVar) args[0], (CharChain) args[1]);
		} 
		if (args[0] instanceof CharChain && args[1] instanceof CharChain) {
			return new Stream(SingleStream.getVar((CharChain) args[0]), (CharChain) args[1]);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "split";
	}
	
	public static class Stream extends AbstractStreamVar implements StringStreamVar{
		private StreamVar s;
		private String separator;
		private String[] split;
		int index;
		
		
		public Stream(StreamVar s, CharChain separator) {
			this.s = s.duplicate();
			this.separator = separator.toString();
			this.index = 0;
			this.split = null;
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(s, new CharChain(separator));
		}
		
		@Override
		public boolean hasNext() {
			if (split != null && split.length > index)
				return true;
			else {
				if (s.hasNext()) {
					split = split((CharChain) s.next());
					index = 0;
					return hasNext();
				}
				else
					return false;
			}
		}
		
		@Override
		public Value next() {
			index++;
			return new CharChain(split[index-1]);
		}
		
		@Override
		public String getConsoleString() {
			return "Splitting of "+s.getConsoleString()+" with \""+separator+"\"";
		}
		
		private String[] split(CharChain c) {
			return c.toString().split(separator);
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			array.put(new CharChain(separator).getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Split.getJSONName();
		}
	}
}
