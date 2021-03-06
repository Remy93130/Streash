package streash.vars;

import org.json.JSONObject;

import streash.vars.functions.pit.Average;
import streash.vars.functions.pit.Count;
import streash.vars.functions.pit.Get;
import streash.vars.functions.pit.Join;
import streash.vars.functions.pit.Len;
import streash.vars.functions.pit.Max;
import streash.vars.functions.pit.Min;
import streash.vars.functions.pit.Print;
import streash.vars.functions.pit.Product;
import streash.vars.functions.pit.Sum;
import streash.vars.functions.primitive.Add;
import streash.vars.functions.primitive.Div;
import streash.vars.functions.primitive.Mul;
import streash.vars.functions.primitive.Sub;
import streash.vars.functions.sources.Bytes;
import streash.vars.functions.sources.Fibo;
import streash.vars.functions.sources.InfiniteIntegers;
import streash.vars.functions.sources.Lines;
import streash.vars.functions.sources.Prime;
import streash.vars.functions.sources.RandomStreamFunc;
import streash.vars.functions.use.Concat;
import streash.vars.functions.use.Distinct;
import streash.vars.functions.use.Filter;
import streash.vars.functions.use.Intersection;
import streash.vars.functions.use.Map;
import streash.vars.functions.use.Mapwhere;
import streash.vars.functions.use.Repeat;
import streash.vars.functions.use.Shuffle;
import streash.vars.functions.use.Slice;
import streash.vars.functions.use.Split;

public interface Function extends Expression{
	public static Function getFunctionByName(String name) {
		// primitives
		if (name.equals("<add>")){ return new Add(); }
		if (name.equals("<sub>")){ return new Sub(); }
		if (name.equals("<div>")){ return new Div(); }
		if (name.equals("<mul>")){ return new Mul(); }
		// stream sources
		if (name.equals("<integers>")){ return new InfiniteIntegers(false); }
		if (name.equals("<revintegers>")){ return new InfiniteIntegers(true); }
		if (name.equals("<random>")){ return new RandomStreamFunc(); }
		if (name.equals("<fibo>")){ return new Fibo(); }
		if (name.equals("<lines>")){ return new Lines(); }
		if (name.equals("<bytes>")){ return new Bytes(); }
		if (name.equals("<prime>")){ return new Prime(); }
		// stream actions
		if (name.equals("<slice>")){ return new Slice(); }
		if (name.equals("<repeat>")){ return new Repeat(); }
		if (name.equals("<concat>")){ return new Concat(); }
		if (name.equals("<inter>")){ return new Intersection(); }
		if (name.equals("<shuffle>")){ return new Shuffle(); }
		if (name.equals("<map>")){ return new Map(); }
		if (name.equals("<filter>")){ return new Filter(); }
		if (name.equals("<split>")){ return new Split(); }
		if (name.equals("<distinct>")){ return new Distinct(); }
		if (name.equals("<mapif>")){ return new Mapwhere(); }
		// stream products
		if (name.equals("<print>")){ return new Print(); }
		if (name.equals("<len>")){ return new Len(); }
		if (name.equals("<sum>")){ return new Sum(); }
		if (name.equals("<product>")){ return new Product(); }
		if (name.equals("<average>")){ return new Average(); }
		if (name.equals("<min>")){ return new Min(); }
		if (name.equals("<max>")){ return new Max(); }
		if (name.equals("<count>")){ return new Count(); }
		if (name.equals("<get>")){ return new Get(); }
		if (name.equals("<join>")){ return new Join(); }
		throw new IllegalArgumentException("There is no function called "+name);
	}
	public void takeArgument(Value v, boolean npi);
	public int argNumber();
	public Value evaluate();
	public void empty();
	public String getName();
	
	@Override
	default JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("type", "Function");
		o.put("Value", new JSONObject().put("Name", this.getName()));
		return o;
	}
	public StreamVar getStreamFromJSON(JSONObject json, boolean npi);
	public static boolean hasJSONTag(String tag) {
		return tag.equals("Function");
	}
	public static Function getValueFromJSON(JSONObject o) {
		return getFunctionByName("<"+o.getString("Name")+">");
	}
}
