package streash.vars.stream;

import streash.vars.StreamVar;

public interface StringStreamVar extends StreamVar{
	default boolean sameGenerical(StreamVar s) {
		return s instanceof StringStreamVar;
	}
}
