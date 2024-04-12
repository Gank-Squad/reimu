package com.git.ganksquad.data.types;

public interface TestReimuType {
	
	public static enum ReimuTypeEnumValue {
		NONE,
		UNKNOWN,
		NUMERIC,
		STRING,
		BOOLEAN,
		FUNCTION,
		INDEXABLE,
		ITERABLE;
	}

	public boolean isCompatibleWith(ReimuTypeEnumValue other);
}
