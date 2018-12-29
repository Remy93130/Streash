package streash.vars.stream;

import streash.vars.StreamVar;

public interface NumberStreamVar extends StreamVar{
	@Override
	default String getType() {
		return "NumberStream";
	}
}
