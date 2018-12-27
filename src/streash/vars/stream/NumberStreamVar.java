package streash.vars.stream;

import streash.vars.StreamVar;

public interface NumberStreamVar extends StreamVar{
	default boolean sameGenerical(StreamVar s) {
		return s instanceof NumberStreamVar;
	}
}
