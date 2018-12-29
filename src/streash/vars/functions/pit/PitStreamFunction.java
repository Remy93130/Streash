package streash.vars.functions.pit;

import org.json.JSONObject;

import streash.vars.Function;
import streash.vars.StreamVar;

public interface PitStreamFunction extends Function{
	
	@Override
	default StreamVar getStreamFromJSON(JSONObject json, boolean npi) {
		return null;
	}
}
