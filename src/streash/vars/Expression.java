package streash.vars;

import org.json.JSONObject;

public interface Expression {
	JSONObject getJSONObject();
	public static Expression getValueFromJSON(JSONObject o) {
		return getValueFromJSON(o, false);
	}
	public static Expression getValueFromJSON(JSONObject o, boolean npi) {
		String type = o.getString("type");
		if (Number.hasJSONTag(type))
			return Number.getValueFromJSON(o.getJSONObject("Value"));
		if (StreamVar.hasJSONTag(type))
			return StreamVar.getValueFromJSON(o.getJSONObject("Value"), npi);
		if (CharChain.hasJSONTag(type))
			return CharChain.getValueFromJSON(o.getJSONObject("Value"));
		if (LambdaVar.hasJSONTag(type))
			return LambdaVar.getValueFromJSON(o.getJSONObject("Value"));
		if (Function.hasJSONTag(type))
			return Function.getValueFromJSON(o.getJSONObject("Value"));
		return null;
	}
}
