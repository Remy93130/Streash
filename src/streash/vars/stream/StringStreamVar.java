package streash.vars.stream;

import streash.vars.StreamVar;

public interface StringStreamVar extends StreamVar{
	@Override
	default String getType() {
		return "StringStream";
	}
}
