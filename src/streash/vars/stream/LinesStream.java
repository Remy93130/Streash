package streash.vars.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import streash.vars.CharChain;
import streash.vars.StreamVar;
import streash.vars.Value;

public class LinesStream implements StringStreamVar{
	private String filePath;
	private BufferedReader br;
	private String current;
	
	public LinesStream(CharChain filePath) {
		this.filePath = filePath.toString();
		try {
			this.br = new BufferedReader(new FileReader(this.filePath));
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Unvalid filepath : file not found");
		} catch (@SuppressWarnings("hiding") IOException e) {
			throw new IllegalStateException("Unable to open the file : IO Exception");
		}
		this.current = null;
	}
 	
	@Override
	public StreamVar duplicate() {
		return new LinesStream(new CharChain(filePath));
	}
	
	@Override
	public String getConsoleString() {
		return "Reader line per line of the file "+filePath;
	}
	
	@Override
	public boolean hasNext() {
		if (current != null)
			return true;
		try {
			if ((current = br.readLine()) == null) {
				br.close();
				return false;
			} return true;
		} catch (Exception e) {
			throw new IllegalStateException("Error while reading the file : "+e.getMessage());
		}
	}
	
	@Override
	public Value next() {
		if (current == null)
			throw new IllegalStateException("Asking for next line without asking if has next line");
		CharChain to = new CharChain(current);
		current = null;
		return to;
	}
}
