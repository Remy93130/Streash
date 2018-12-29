package streash.vars.stream;

import org.json.JSONArray;
import org.json.JSONObject;

import streash.vars.StreamVar;

public abstract class AbstractStreamVar implements StreamVar{
	abstract public String getJSONName();
	abstract public JSONArray getArgs();
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("type", "Stream");
		JSONObject stream = new JSONObject();
		stream.put("Name", getJSONName());
		stream.put("Args", getArgs());
		o.put("Value", stream);
		return o;
	}
}
