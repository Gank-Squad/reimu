package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuNameResolver;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public interface ReimuType {
	
	public boolean canBeBool();

	public boolean isAssignableFrom(ReimuType other);

	public boolean isEqualType(ReimuType other);

	public void resolve(ReimuTypeResolver resolver) throws ReimuCompileException;

	public String getLookupName();

	public Data getDefaultEmptyValue();

	public default String formatToString(Object... param) {
		return ReimuNameResolver.getFormatedName(this, param);
	}
}
