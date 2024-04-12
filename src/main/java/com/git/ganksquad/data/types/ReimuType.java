package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuNameResolver;

public interface ReimuType {
	
	public boolean isAssignableFrom(ReimuType other);

	public boolean isEqualType(ReimuType other);

	public default String formatToString(Object... param) {
		return ReimuNameResolver.getFormatedName(this, param);
	}
}
