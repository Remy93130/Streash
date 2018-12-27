package streash.vars.stream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import streash.vars.CharChain;
import streash.vars.StreamVar;
import streash.vars.Value;

public class BytesStream implements StringStreamVar{
	private String filePath;
	private FileInputStream read;
	private int current;
	
	public BytesStream(CharChain filePath) {
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
		return new BytesStream(new CharChain(filePath));
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
}
