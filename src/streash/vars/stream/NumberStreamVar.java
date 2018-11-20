package streash.vars.stream;

import streash.vars.StreamVar;

public interface NumberStreamVar extends StreamVar{
	default boolean sameGenerical(StreamVar s) {
		return s instanceof NumberStreamVar;
	}
	@Override
	default String getType() {
		return type();
	}
	public static String type() {
		return "Number"+StreamVar.type();
	}
}
