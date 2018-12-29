package streash.vars.functions.sources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.StringStreamVar;

public class Bytes extends AbstractFunction{
	
	public Bytes() {
		super(1);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof CharChain)
			return new Stream((CharChain) args[0]);

		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "bytes";
	}
	
	public static class Stream extends AbstractStreamVar implements StringStreamVar{
		private String filePath;
		private FileInputStream read;
		private int current;
		
		public Stream(CharChain filePath) {
			this.filePath = filePath.toString();
			try {
				this.read = new FileInputStream(this.filePath);
			} catch (FileNotFoundException e) {
				throw new IllegalStateException("Unvalid filepath : file not found");
			} catch (@SuppressWarnings("hiding") IOException e) {
				throw new IllegalStateException("Unable to open the file : IO Exception");
			}
			this.current = -1;
		}
	 	
		@Override
		public StreamVar duplicate() {
			return new Stream(new CharChain(filePath));
		}
		
		@Override
		public String getConsoleString() {
			return "Reader byte per byte of the file "+filePath;
		}
		
		@Override
		public boolean hasNext() {
			if (current != -1)
				return true;
			try {
				if ((current = read.read()) == -1) {
					read.close();
					return false;
				} return true;
			} catch (Exception e) {
				throw new IllegalStateException("Error while reading the file : "+e.getMessage());
			}
		}
		
		@Override
		public Value next() {
			if (current == -1)
				throw new IllegalStateException("Asking for next line without asking if has next line");
			CharChain to = new CharChain(String.format("%8s", Integer.toBinaryString((byte) current & 0xFF)).replace(' ', '0'));
			current = -1;
			return to;
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(new CharChain(filePath).getJSONObject());
			return array;
		}
		@Override
		public String getJSONName() {
			return Bytes.getJSONName();
		}
	}
}
