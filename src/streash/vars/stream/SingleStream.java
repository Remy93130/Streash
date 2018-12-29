package streash.vars.stream;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.StreamVar;
import streash.vars.Value;

public class SingleStream extends AbstractStreamVar{
	private Value value;
	private boolean given;
	
	private SingleStream(Value v) {
		this.value = v;
		this.given = false;
	}
	
	public static StreamVar getVar(Value v) {
		if (v instanceof Number)
			return new SingleNumberStream(v);
		if (v instanceof CharChain)
			return new SingleStringStream(v);
		return null;
	}
	
	@Override
	public StreamVar duplicate() {
		return new SingleStream(value);
	}
	
	@Override
	public boolean hasNext() {
		return !given;
	}
	
	@Override
	public Value next() {
		if (!hasNext())
			throw new IllegalStateException("No more element in the Stream");
		given = true;
		return value;
	}
	
	@Override
	public String getConsoleString() {
		return value.getConsoleString();
	}
	
	@Override
	public long len() {
		return 1;
	}
	
	@Override
	public JSONArray getArgs() {
		JSONArray array = new JSONArray();
		array.put(value.getJSONObject());
		return array;
	}
	
	@Override
	public String getJSONName() {
		return Single.getJSONName();
	}
	
	public static class SingleNumberStream extends SingleStream implements NumberStreamVar {
		public SingleNumberStream(Value v) {
			super(v);
		}
	}
	public static class SingleStringStream extends SingleStream implements NumberStreamVar {
		public SingleStringStream(Value v) {
			super(v);
		}
	}
}
