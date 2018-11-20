package streash.vars.stream;

import streash.vars.StreamVar;

public interface StringStreamVar extends StreamVar{
	default boolean sameGenerical(StreamVar s) {
		return s instanceof StringStreamVar;
	}
	@Override
	default String getType() {
		return type();
	}
	public static String type() {
		return "Number"+StreamVar.type();
	}
}
