package streash.console;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

import streash.vars.Value;

public class JSONConverter {
	JSONObject json;
	
	public JSONConverter() {
		this.json = new JSONObject();
	}
	
	private void addEntry(String name, Value value) {
		json.put(name, value.getJSONObject());
	}
	
	public void addMap(Map<String, Value> map) {
		for (String key : map.keySet()) {
			addEntry(key, map.get(key));
		}
	}
	
	public Map<String, Value> getMap() {
		Map<String, Value> map = new HashMap<String, Value>();
		for (String key : json.keySet()) {
			JSONObject obj = json.getJSONObject(key);
			
			map.put(key, Value.getValueFromJSON(obj));
		}
		return map;
	}
	
	public void writeJSON(String filePath) {
		try (FileWriter file = new FileWriter(filePath)) {
			file.write(json.toString());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to write in the file");
		}
	}
	
	public void writeJSON() {
		writeJSON("streashSession.json");
	}
	
	@SuppressWarnings("resource")
	public void readJSON(String filePath) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
		 
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			 
			while ((line = bufferedReader.readLine()) != null)
				stringBuffer.append(line).append("\n");
			
			json = new JSONObject(stringBuffer.toString());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to read the file");
		}
	}
	
	public void readJSON() {
		readJSON("streashSession.json");
	}
}
