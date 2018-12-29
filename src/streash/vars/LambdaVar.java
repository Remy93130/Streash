package streash.vars;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class LambdaVar implements Value{
	private List<Expression> list;
	private int point;
	private boolean npi;
	
	private LambdaVar(List<Expression> list, boolean npi) {
		this.list = list;
		this.point = 0;
		this.npi = npi;
	}
	
	public Value produce(Value v) {
		point = 0;
		if (list.get(point) == null)
			return v;
		if (list.get(point) instanceof Function) {
			point++;
			return productOfFunction((Function) list.get(point-1), v);
		}
		point = 0;
		return (Value) list.get(point);
	}
	
	private Value productOfFunction(Function f, Value v) {
		f.empty();
		for (int i = 0; i < f.argNumber(); i++) {
			if (list.get(point) == null) {
				f.takeArgument(v, npi);
				point++;
				continue;
			}
			if (list.get(point) instanceof Function) {
				point++;
				f.takeArgument(productOfFunction((Function) list.get(point-1), v), npi);
				continue;
			}
			f.takeArgument((Value) list.get(point), npi);
			point++;
		}
		return f.evaluate();
	}
	
	@Override
	public String getType() {
		return "Lambda";
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
	@Override
	public String getConsoleString() {
		return this.toString();
	}
	
	public static LambdaVar parse(List<Expression> values, boolean npi) {
		LambdaVar lambda = new LambdaVar(values, npi);
		if (values.size() < 1)
			throw new IllegalArgumentException("Syntax Error for lambda");
		int count = 1;
		int i = 0;
		
		while(count > 0) {
			if (values.size() <= i)
				throw new IllegalStateException("Too few arguments for lambda expression");
			count--;
			if (values.get(i) instanceof Function) {
				count += ((Function) values.get(i)).argNumber();
			}
			i++;
		}
		
		if (i != values.size())
			throw new IllegalStateException("Too much arguments for lambda expression");
		
		return lambda;
	}
	
	private List<JSONObject> getJSONList() {
		List<JSONObject> l = new ArrayList<JSONObject>();
		for (Expression e : list) {
			if (e == null)
				l.add(null);
			else
				l.add(e.getJSONObject());
		}
			
		return l;
	}
	
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("type", "Lambda");
		JSONObject lambda = new JSONObject();
		lambda.put("npi", npi);
		lambda.put("list", getJSONList());
		o.put("Value", lambda);
		return o;
	}
	
	public static boolean hasJSONTag(String tag) {
		return tag.equals("Lambda");
	}
	
	public static LambdaVar getValueFromJSON(JSONObject o) {
		JSONArray list = o.getJSONArray("list");
		List<Expression> expressions = new ArrayList<Expression>();
		boolean npi = o.getBoolean("npi");
		for (Object e : list) {
			if (e.equals(null))
				expressions.add(null);
			else
				expressions.add(Expression.getValueFromJSON((JSONObject) e, npi));
		}
		return LambdaVar.parse(expressions, npi);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for (Expression e : list) {
			if (e == null)
				sb.append("it ");
			if (e instanceof Function)
				sb.append("<"+((Function) e).getName()+"> ");
			if (e instanceof Primitive)
				sb.append(((Primitive) e).getConsoleString()+" ");
			if (e instanceof StreamVar)
				sb.append(((StreamVar)e).getConsoleString());
			if (e instanceof LambdaVar)
				sb.append(((LambdaVar) e).getConsoleString());
		}
		sb.append("}");
		return sb.toString();
	}
}
